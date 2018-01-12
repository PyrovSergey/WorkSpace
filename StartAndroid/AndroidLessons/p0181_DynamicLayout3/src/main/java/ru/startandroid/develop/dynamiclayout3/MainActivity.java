package ru.startandroid.develop.dynamiclayout3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    // выносим необходимые переменные
    SeekBar sbWeight;
    Button button1;
    Button button2;
    // это переменные для отслеживания состояния вьюшек
    LinearLayout.LayoutParams lParams1;
    LinearLayout.LayoutParams lParams2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // находим по ID нужные ссылки и передаем переменным..
        sbWeight = (SeekBar) findViewById(R.id.sbWeight);
        // цепяем слушателя к SeekBar
        sbWeight.setOnSeekBarChangeListener(this);

        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        // ...

        // цепляем состояние вьюшек к переменным для отследивания через приведение к нужному типу (LinearLayout.LayoutParams)
        lParams1 = (LinearLayout.LayoutParams)button1.getLayoutParams();
        lParams2 = (LinearLayout.LayoutParams)button2.getLayoutParams();


    }

    // метод срабатывает все время, пока значение меняется
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int leftValue = i;
        int rightValue = seekBar.getMax() - i;
        // настраиваем вес
        lParams1.weight = leftValue;
        lParams2.weight = rightValue;
        // а в текст кнопок пишем текущее значение веса
        button1.setText(String.valueOf(leftValue));
        button2.setText(String.valueOf(rightValue));
    }

    // метод срабатывает, когда начинаем тащить ползунок
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    // метод срабатывает, когда отпускаем ползунок
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
