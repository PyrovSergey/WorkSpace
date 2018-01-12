package ru.startandroid.develop.logandmess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button buttonOk;
    Button buttonCancel;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // найдем View-элементы
        Log.d(TAG, "найдем View-элементы");
        textView = (TextView) findViewById(R.id.tvOut);
        buttonOk = (Button) findViewById(R.id.btnOk);
        buttonCancel = (Button) findViewById(R.id.btnCancel);

        // присваиваем обработчик кнопкам
        Log.d(TAG, "присваиваем обработчик кнопкам");
        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // по id определяем кнопку, вызвавшую этот обработчик
        Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик");
        if (view.getId() == buttonOk.getId()) {
            // кнопка ОК
            Log.d(TAG, "кнопка ОК");
            textView.setText("Нажата кнопка Ok");
            Toast.makeText(this,"Нажата кнопка Ok", Toast.LENGTH_LONG).show();
        }
        if (view.getId() == buttonCancel.getId()) {
            // кнопка Cancel
            Log.d(TAG, "кнопка Cancel");
            textView.setText("Нажата кнопка Cancel");
            Toast.makeText(this,"Нажата кнопка Cancel", Toast.LENGTH_LONG).show();
        }
    }
}
