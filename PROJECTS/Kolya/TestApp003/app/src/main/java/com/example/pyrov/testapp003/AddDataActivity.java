package com.example.pyrov.testapp003;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.pyrov.testapp003.MainActivity.LOG_TAG;

public class AddDataActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDate;
    private EditText editEntryField;
    private Spinner spinnerDB;
    private Button buttonAdd;
    private String dBName;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        Log.d(LOG_TAG, "Start main AddDataActivity");

        editTextDate = (EditText) findViewById(R.id.date);
        editEntryField = (EditText) findViewById(R.id.entry_field);
        spinnerDB = (Spinner) findViewById(R.id.spinner);
        buttonAdd = (Button) findViewById(R.id.button_add_data);

        editTextDate.setText(getDate());
        buttonAdd.setOnClickListener(this);

        setupSpinner();

    }

    @Override
    public void onClick(View view) {

        String date = editTextDate.getText().toString();
        String data = editEntryField.getText().toString();

        ContentValues contentValues = new ContentValues();

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        if (editEntryField.getText().toString().isEmpty()) {
            return;
        }
        switch (view.getId()) {

            case R.id.button_add_data:
                Log.d(LOG_TAG, "--- Insert in " + dBName + ": ---");

                // подготовим данные для вставки в виде пар: наименование столбца - значение
                contentValues.put("date", date);
                contentValues.put("data", data);

                // вставляем запись и получаем ее ID
                long rowID = sqLiteDatabase.insert(dBName, null, contentValues);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
        }
        dbHelper.close();
    }

    // Настройте выпадающий счетчик, который позволяет пользователю выбирать пол домашнего животного.
    private void setupSpinner() {
        // Создайте адаптер для счетчика. Параметры списка из массива String, в котором он будет использоваться, будет использоваться макет по умолчанию
        ArrayAdapter dbAdapter = ArrayAdapter.createFromResource(this, R.array.DB_names, android.R.layout.simple_spinner_item);

        // Указать стиль выпадающего макета - простое представление списка с 1 элементом на строку
        dbAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Устанавливаем адаптер спиннеру
        spinnerDB.setAdapter(dbAdapter);

        // Установите целое число mSelected в значения констант
        spinnerDB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.DBNotes))) {
                        dBName = getString(R.string.DBNotes);
                    } else if (selection.equals(getString(R.string.DBSport))) {
                        dBName = getString(R.string.DBSport);
                    } else if (selection.equals(getString(R.string.DBSport))) {
                        dBName = getString(R.string.DBCommonBase);
                    }
                }
            }

            // Поскольку AdapterView является абстрактным классом, onNothingSelected должен быть определен
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dBName = getString(R.string.DBCommonBase);
            }
        });
    }

    // метод возвращает текущую дату и время
    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return simpleDateFormat.format(new Date());
    }

    // метод возвращает имя выбранной БД в меню Спинер
    public static String getNameDB() {
        return "common_database";
    }
}
