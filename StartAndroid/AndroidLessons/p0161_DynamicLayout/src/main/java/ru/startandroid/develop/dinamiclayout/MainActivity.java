package ru.startandroid.develop.dinamiclayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // создаем LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        // устанавливаем вертикальную ориентацию
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams (параметры макета)
        ViewGroup.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // устанавливаем linLayout как корневой элемент экрана
        setContentView(linearLayout, layoutParams);

        // создаем TextView
        // создаем параметры для наших вьюшек из ViewGroup.LayoutParams (доступны только ширина и высота)
        ViewGroup.LayoutParams viewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // создаем сам TextView
        TextView textView1 = new TextView(this);
        // присваиваем вьюшке любой текст
        textView1.setText("Это TextView");
        // задаем вьюшке параметры, которые мы указали в layoutParams
        textView1.setLayoutParams(viewParams);
        // и добавляем нау текствьюшку в сам основной макет (linearLayout)
        linearLayout.addView(textView1);

        // создаем Button (кнопку №1)
        Button button1 = new Button(this);
        // присваеваем наше кнопке текст (ее название)
        button1.setText("Кнопка 1");
        // добавляем кнопку в наш основной макет (linearLayout) И ПАРАМЕТРЫ layoutParams!!!
        linearLayout.addView(button1, viewParams);

        // создаем еще параметр для вьюшек из LinearLayout.LayoutParams (доступны еще отступы и выравнивание)
        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // задаем отступ от левого края
        leftMarginParams.leftMargin = 50;

        // создаем кнопку №2
        Button button2 = new Button(this);
        // присваеваем ей текст (имя)
        button2.setText("Кнопка 2");
        // и добавляем кнопку №2 в наш основной макет (linearLayout) НО С ПАРАМЕТРАМИ leftMarginParams
        linearLayout.addView(button2, leftMarginParams);

        // создаем еще параметр для вьюшек из LinearLayout.LayoutParams для демонстрации выравнивания
        LinearLayout.LayoutParams alignmentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // собственно задаем само выравнивание (по правому краю)
        alignmentParams.gravity = Gravity.RIGHT;
        // создаем кнопку №3 для демонстрации
        Button button3 = new Button(this);
        // присваеваем ей имя
        button3.setText("Кнопка 3");
        // добавляем кнопку №3 в основной макет с использованием выравнивания
        linearLayout.addView(button3, alignmentParams);

    }
}
