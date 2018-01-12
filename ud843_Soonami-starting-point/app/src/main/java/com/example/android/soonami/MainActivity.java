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
package com.example.android.soonami;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

/**
 * Отображает информацию об одном землетрясении.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Тег для сообщений журнала
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * URL для запроса набора данных USGS для информации о землетрясениях
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-12-01&minmagnitude=7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Удалите {@link AsyncTask} для выполнения сетевого запроса
        TsunamiAsyncTask task = new TsunamiAsyncTask();
        task.execute();
    }

    /**
     * Обновите экран, чтобы отобразить информацию из данного {@link Event}.
     */
    private void updateUi(Event earthquake) {
        // Отобразить название землетрясения в пользовательском интерфейсе
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        // Отображение даты землетрясения в пользовательском интерфейсе
        TextView dateTextView = (TextView) findViewById(R.id.date);
        dateTextView.setText(getDateString(earthquake.time));

        // Отображать, было ли предупреждение о цунами в пользовательском интерфейсе
        TextView tsunamiTextView = (TextView) findViewById(R.id.tsunami_alert);
        tsunamiTextView.setText(getTsunamiAlertString(earthquake.tsunamiAlert));
    }

    /**
     * Возвращает форматированную дату и временную строку, когда произошло землетрясение.
     */
    private String getDateString(long timeInMilliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z");
        return formatter.format(timeInMilliseconds);
    }

    /**
     * Верните строку отображения для того, было ли предупреждение о цунами для землетрясения или нет.
     */
    private String getTsunamiAlertString(int tsunamiAlert) {
        switch (tsunamiAlert) {
            case 0:
                return getString(R.string.alert_no);
            case 1:
                return getString(R.string.alert_yes);
            default:
                return getString(R.string.alert_not_available);
        }
    }

    /**
     * {@link AsyncTask} для выполнения сетевого запроса в фоновом потоке, а затем
     *  обновить пользовательский интерфейс с первым землетрясением в ответе.
     */
    private class TsunamiAsyncTask extends AsyncTask<URL, Void, Event> {

        @Override
        protected Event doInBackground(URL... urls) {
            // Создать объект URL
            URL url = createUrl(USGS_REQUEST_URL);

            // Выполните HTTP-запрос на URL-адрес и получите ответ JSON
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Обработать исключение IOException
            }

            // Извлеките соответствующие поля из ответа JSON и создайте объект {@link Event}
            Event earthquake = extractFeatureFromJson(jsonResponse);

            // Верните объект {@ link Event} в результате {@link Tsunami AsyncTask}
            return earthquake;
        }

        /**
         * Обновите экран с данным землетрясением (которое было результатом
         * {@link TsunamiAsyncTask}).
         */
        @Override
        protected void onPostExecute(Event earthquake) {
            if (earthquake == null) {
                return;
            }

            updateUi(earthquake);
        }

        /**
         * Возвращает новый URL-объект из заданного строкового URL-адреса.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Сделайте HTTP-запрос к указанному URL-адресу и верните строку как ответ.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            if (url == null) {
                return jsonResponse;
            }
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                // если запрос успешен - выполняем далее чтение с потока
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }
            } catch (IOException e) {
                // TODO: Обработать исключение
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // функция должна обрабатывать java.io.IOException здесь
                    inputStream.close();
                }
            }

            return jsonResponse;
        }

        /**
         * Преобразуйте {@link InputStream} в строку, содержащую
         * весь ответ JSON с сервера.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /*
         * Верните объект {@ Link Event}, разобрав информацию
         * о первом землетрясении из входного землетрясенияJSON.
         */
        private Event extractFeatureFromJson(String earthquakeJSON) {
            //  если ответ JSON является пустой строкой, то возвращаем null и ничего не делаем
            if (TextUtils.isEmpty(earthquakeJSON)) {
                return null;
            }
            try {
                JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
                JSONArray featureArray = baseJsonResponse.getJSONArray("features");

                // Если в массиве признаков есть результаты
                if (featureArray.length() > 0) {
                    // Извлечь первую особенность (которая является землетрясением)
                    JSONObject firstFeature = featureArray.getJSONObject(0);
                    JSONObject properties = firstFeature.getJSONObject("properties");

                    // Извлечь значения названия, времени и цунами
                    String title = properties.getString("title");
                    long time = properties.getLong("time");
                    int tsunamiAlert = properties.getInt("tsunami");

                    // Создайте новый объект {@ Link Event}
                    return new Event(title, time, tsunamiAlert);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return null;
        }
    }
}
