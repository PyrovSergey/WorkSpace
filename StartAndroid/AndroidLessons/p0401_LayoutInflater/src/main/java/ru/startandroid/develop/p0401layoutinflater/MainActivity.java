package ru.startandroid.develop.p0401layoutinflater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.text, null, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
        linearLayout.addView(view);

        Log.d(LOG_TAG, "Class of view: " + view.getClass().toString());
        Log.d(LOG_TAG, "LayoutParams of view is null: " + (layoutParams == null));
        Log.d(LOG_TAG, "Text of view: " + ((TextView) view).getText());
    }
}
