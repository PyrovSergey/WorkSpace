package com.example.pyrov.p0331_sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText, editKey;
    Button buttonSave, buttonLoad;

    SharedPreferences sharedPreferences;

    //String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editKey = (EditText) findViewById(R.id.etKey);
        editText = (EditText) findViewById(R.id.etText);
        buttonSave = (Button) findViewById(R.id.btnSave);
        buttonLoad = (Button) findViewById(R.id.btnLoad);

        buttonSave.setOnClickListener(this);
        buttonLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                saveText();
                break;
            case R.id.btnLoad:
                loadText();
                break;
            default:
                break;
        }
    }

    // метод для сохранения текста
    public void saveText() {
        // с помощью метода getPreferences получаем объект sPref класса SharedPreferences, который позволяет работать с данными (читать и писать)
        sharedPreferences = getPreferences(MODE_PRIVATE);
        // получаем объект Editor, чтобы редактировать данные
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // сохраняем строку, указав наименование переменной (SAVED_TEXT), и сам текст
        editor.putString(editKey.getText().toString(), editText.getText().toString());
        // выполняем commit() для сохранения
        editor.commit();
        // выводим сообщение, что текст сохранен
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    // метод по чтению текста
    public void loadText() {
        // с помощью метода getPreferences получаем объект sPref класса SharedPreferences, который позволяет работать с данными (читать и писать)
        sharedPreferences = getPreferences(MODE_PRIVATE);
        // получаем строку из sharedPreferences, указав константу (SAVED_TEXT) - это имя, и значение по умолчанию (пустая строка)
        String savedtext = sharedPreferences.getString(editKey.getText().toString(), "");
        // присваиваем полю ввода полученный текст
        editText.setText(savedtext);
        // выводим сообщение об этом
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }
}
