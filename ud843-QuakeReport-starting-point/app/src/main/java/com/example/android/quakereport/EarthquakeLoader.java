package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

// Класс Loader для рационального использования ресурсов
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e("QueryUtils", "сработал метод onStartLoading()");
    }

    @Override
    public List<Earthquake> loadInBackground() {
        // Если длина переданного массива urls меньше 1 или первая строчка равна null
        // то возвращаем null и ничего дальше не делаем
        if (mUrl == null) {
            return null;
        }
        // иначе создаем список землетрясений result и возвращаем его
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        Log.e("QueryUtils", "сработал метод loadInBackground()");
        return earthquakes;
    }
}
