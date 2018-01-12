package ru.startandroid.develop.contextmenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // создаем переменные для TextView
    TextView textViewColor;
    TextView textViewSize;

    // создаем и описываем константы для хранения ID меню
    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_GREEN = 2;
    final int MENU_COLOR_BLUE = 3;
    final int MENU_SIZE_22 = 4;
    final int MENU_SIZE_26 = 5;
    final int MENU_SIZE_30 = 6;

    // метод создает контекстного меню в зависимости от того
    // какой View был "нажат" (v)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.tvColor) {
            menu.add(0, MENU_COLOR_RED, 0, "Red");
            menu.add(0, MENU_COLOR_GREEN, 0, "Green");
            menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
        }
        if (v.getId() == R.id.tvSize) {
            menu.add(0, MENU_SIZE_22, 0, "22");
            menu.add(0, MENU_SIZE_26, 0, "26");
            menu.add(0, MENU_SIZE_30, 0, "30");
        }
    }

    // основной метод для запуска записи приложения на телефон и его запуска
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // находим по ID и присваиваем значения переменным TextView
        textViewColor = (TextView) findViewById(R.id.tvColor);
        textViewSize = (TextView) findViewById(R.id.tvSize);

        // "цепляем" контекстное меню к нужным TextView
        registerForContextMenu(textViewColor);
        registerForContextMenu(textViewSize);
    }

    // метод определяет какой пункт контекстного меню был выбран(item)
    // и осуществляет на основе этого необходимые действия
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // пункты меню для textViewColor
        if (item.getItemId() == MENU_COLOR_RED) {
            textViewColor.setTextColor(Color.RED);
            textViewColor.setText("Text color = red");
        }
        if (item.getItemId() == MENU_COLOR_BLUE) {
            textViewColor.setTextColor(Color.BLUE);
            textViewColor.setText("Text color = blue");
        }
        if (item.getItemId() == MENU_COLOR_GREEN) {
            textViewColor.setTextColor(Color.GREEN);
            textViewColor.setText("Text color = green");
        }
        // пункты меню для
        if (item.getItemId() == MENU_SIZE_22) {
            textViewSize.setTextSize(22);
            textViewSize.setText("Text size = 22");
        }
        if (item.getItemId() == MENU_SIZE_26) {
            textViewSize.setTextSize(26);
            textViewSize.setText("Text size = 26");
        }
        if (item.getItemId() == MENU_SIZE_30) {
            textViewSize.setTextSize(30);
            textViewSize.setText("Text size = 30");
        }
        return super.onContextItemSelected(item);
    }
}
