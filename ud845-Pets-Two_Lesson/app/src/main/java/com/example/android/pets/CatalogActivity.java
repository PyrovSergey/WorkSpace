/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.pets.data.PetContract.PetEntry;

// Класс тображает список домашних животных, которые были введены и сохранены в приложении.
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static int PET_LOADER = 0;

    // Это адаптер для нашего ListView
    PetCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Настрока FAB для открытия EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        ListView petListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        mCursorAdapter = new PetCursorAdapter(this, null);
        petListView.setAdapter(mCursorAdapter);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(PetEntry.CONTENT_URI, id);

                intent.setData(currentPetUri);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(PET_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Надуйте параметры меню из файла res / menu / menu_catalog.xml.
        // Это добавляет пункты меню в панель приложений.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    private void insertPet() {
        // Создаем объект ContentValues, где имена столбцов являются ключами,
        // и атрибуты домашних животных Тото являются значениями.
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);

        // Вставьте новую строку для Toto в провайдера, используя ContentResolver.
        // Используйте {@link PetEntry # CONTENT_URI}, чтобы указать, что мы хотим вставить в таблицу базы данных домашних животных.
        // Получите новый URI контента, который позволит нам получить доступ к данным Toto в будущем.
        Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Пользователь нажал на пункт меню в меню переполнения панели приложений.
        switch (item.getItemId()) {
            // Ответьте на щелчок на меню «Вставить фиктивные данные»
            case R.id.action_insert_dummy_data:
                insertPet();
                return true;
            // Ответьте на один щелчок на пункте меню «Удалить все записи»
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Настраиваем "поекцию" того, что нам понадобится для отображение и работы
        String[] projection = {
                PetEntry._ID,                 // ID питомца
                PetEntry.COLUMN_PET_NAME,     // колонка имени
                PetEntry.COLUMN_PET_BREED};   // колонка породы

        // вот этот запрос загрузчика будет выполнен в паралельном потоке у контент провайдера и возвращен как Loader<Cursor>
        return new CursorLoader(this, PetEntry.CONTENT_URI, projection, null, null, null);
    }

    // Этот метод обновляет объект Cursor и передает все данные (в Cursor) из метода onCreateLoader()
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // тут мы заменяем у нашей переменной mCursorAdapter cursor, на полученный из onCreateLoader()
        mCursorAdapter.swapCursor(data);
    }

    // а этот метод вызывается тогда, когда данные в Cursor должны быть удалены
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // что мы собственно и делаем
        mCursorAdapter.swapCursor(null);
    }

    // Helper method to delete all pets in the database.
    private void deleteAllPets() {
        int rowsDeleted = getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }
}