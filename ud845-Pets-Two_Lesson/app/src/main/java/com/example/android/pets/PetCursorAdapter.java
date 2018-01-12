package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;

// Класс для чтения с БД, создания ListView и его заполнения
public class PetCursorAdapter extends CursorAdapter {
    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    // Тут мы создаем новый ПУСТОЙ элемент ListView и возвращаем его
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Находим по ID из list_item.xml наши TextView и цепляем к переменным
        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvSummary = (TextView) view.findViewById(R.id.summary);
        // Вытаскиваем нужные значения из Cursor
        String petName = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_NAME));
        String petBreed = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_BREED));

        // Если породы домашнего животного - пустая строка или нуль, то используйте текст
        // по умолчанию, который говорит «Неизвестная порода», поэтому TextView не пуст.
        if (TextUtils.isEmpty(petBreed)) {
            petBreed = context.getString(R.string.unknown_breed);
        }

        // Присваиваем полученную инфу из Cursor в наши TextView
        tvName.setText(petName);
        tvSummary.setText(petBreed);
    }
}
