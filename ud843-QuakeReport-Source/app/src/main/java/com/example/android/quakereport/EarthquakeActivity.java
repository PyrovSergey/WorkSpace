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
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=3&limit=9000";

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    // Адаптер для списка землетрясений
    private EarthquakeAdapter mAdapter;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Найти ссылку на {@link ListView} в макете
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Цепляем ссылку на "пустой" View
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Создаем новый адаптер, который принимает пустой список землетрясений в качестве входных данных
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Установите адаптер в {@link ListView}
        // поэтому список может быть заполнен в пользовательском интерфейсе
        earthquakeListView.setAdapter(mAdapter);

        // Устанавливаем элемент Listen List List в ListView, который отправляет намерение в веб-браузер
        // открыть веб-сайт с дополнительной информацией о выбранном землетрясении.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Найдите текущее землетрясение, на которое было нажато
                Earthquake currentEarthquake = mAdapter.getItem(i);

                // Преобразование String URL в объект URI (для перехода в конструктор Intent)
                Uri earthquakeUri = currentEarthquake.getUri();

                // Создаем новое намерение для просмотра URI землетрясения
                Intent websiteintent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteintent);
            }
        });

        // Создаем объект Loader (EarthquakeLoader)
        LoaderManager loaderManager = getLoaderManager();
        // инициализируем его и запускаем
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        Log.e("QueryUtils", "сработал метод initLoader()");

        // Получить ссылку на ConnectivityManager для проверки состояния сетевого подключения
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Получить информацию о текущей активной сети передачи данных по умолчанию
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Если есть сетевое подключение, выберите данные
        if (networkInfo != null && networkInfo.isConnected()) {
            // Получите ссылку на LoaderManager, чтобы взаимодействовать с загрузчиками.
            loaderManager = getLoaderManager();

            // Инициализация загрузчика. Перейдите в константу ID ID, указанную выше, и передайте значение null для
            // связка. Перейдите в эту операцию для параметра LoaderCallbacks (который действителен
            // потому что эта активность реализует интерфейс LoaderCallbacks).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // В противном случае, ошибка отображения
            // Сначала скройте индикатор загрузки, чтобы было видно сообщение об ошибке
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Обновить пустое состояние без сообщения об ошибке подключения
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    // Если объекта Loader не существует, вызывается данный метод и создает EarthquakeLoader
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.e("QueryUtils", "сработал метод onCreateLoader()");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);

    }

    // Когда вызывается, когда отработает loadInBackground в классе EarthquakeLoader
    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Очистите адаптер предыдущих данных землетрясения
        mAdapter.clear();
        // Если есть допустимый список {@link Earthquake} s, добавьте их в адаптер
        // набор данных. Это приведет к обновлению ListView.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
        Log.e("QueryUtils", "сработал метод onLoadFinished()");
    }

    // Вызывается, когда необходимо сбросить адаптер от предыдущих данных
    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Очистите адаптер предыдущих данных землетрясения
        mAdapter.clear();
        Log.e("QueryUtils", "сработал метод onLoaderReset()");
    }
}
