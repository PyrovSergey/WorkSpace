package ru.startandroid.develop.listener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button buttonOk;
    Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // находим по id все View элементы
        textView = (TextView) findViewById(R.id.tvOut);
        buttonOk = (Button) findViewById(R.id.btnOk);
        buttonCancel = (Button) findViewById(R.id.btnCancel);

        // создаем обработчика событий (слушателя)
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to do
                if (view.getId() == R.id.btnOk) {
                    textView.setText("Нажата кнопка Ok");
                }
                if (view.getId() == R.id.btnCancel) {
                    textView.setText("Нажата кнопка Cancel");
                }
            }
        };

        // присваиваем кнопкам Ok и Cancel обработчика событий
        buttonOk.setOnClickListener(onClickListener);
        buttonCancel.setOnClickListener(onClickListener);

    }
}
