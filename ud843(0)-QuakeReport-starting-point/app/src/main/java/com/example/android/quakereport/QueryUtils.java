package com.example.android.quakereport;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

// класс для получения данных по Api
public final class QueryUtils {

    /**
     * Создайте частный конструктор, потому что никто не должен создавать объект {@link QueryUtils}.
     * Этот класс предназначен только для хранения статических переменных и методов, к которым можно получить доступ
     * непосредственно из имени класса QueryUtils (и экземпляр объекта QueryUtils не нужен).
     */
    private QueryUtils() {
    }

    // Возвращаем список объектов {@link Earthquake}, которые были созданы из разбора JSON-ответа.
    public static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {

        // Если переданная строка пустая или равна null - возвращаем null и дальше ничего не делаем
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Создайем пустой List, чтобы мы могли начать добавлять землетрясения к
        List<Earthquake> earthquakes = new ArrayList<>();

        try {
            // Создаем объект JSON и передаем в него строку запроса (USGS_REQUEST_URL)
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            // Создаем массим JSON и передаем ему извленный массив из объекта baseJsonResponse по ключу "features"
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            // Для создания объекта Earthquake нам нужно извлеч из массива все переменные типа
            // double magnitude, String place, long timeInMilliseconds, Uri uri

            // Извлекаем все эти данные из массива earthquakeArray в цикле
            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject data = currentEarthquake.getJSONObject("properties");
                double magnitude = data.getDouble("mag");
                String place = data.getString("place");
                long timeInMilliseconds = data.getLong("time");
                Uri uri = Uri.parse(data.getString("url"));
                // затем создаем объект Earthquake с полученными данными и добавляем в список earthquakes
                Earthquake earthquake = new Earthquake(magnitude, place, timeInMilliseconds, uri);
                earthquakes.add(earthquake);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }

    // Сделайте HTTP-запрос к указанному URL-адресу и верните строку (JSON) как ответ.
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Чтение строки из входящего потока, которая содержит весь ответ JSON с сервера.
    private static String readFromStream(InputStream inputStream) throws IOException {
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

    // Обощающий публичный метод, который делает запрос на сервер, получает ответ - строку (JSON)
    // и возвращает список объектов Earthquake
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponce = null;
        try {
            jsonResponce = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponce);
        Log.e("QueryUtils", "сработал метод fetchEarthquakeData()");

        return earthquakes;
    }

    // Возвращает новый URL-объект из заданного строкового URL-адреса.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
}
