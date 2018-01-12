package ru.startandroid.develop.p0361sqlitequery;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements View.OnClickListener {
    // строка "лог" для отслеживания происодящего
    final String LOG_TAG = "myLogs";

    // массив по странам
    String name[] = {"Китай", "США", "Бразилия", "Россия", "Япония",
            "Германия", "Египет", "Италия", "Франция", "Канада"};
    // массив по населению (тыс.человек)
    int people[] = {1400, 311, 195, 142, 128, 82, 80, 60, 66, 35};
    // массив по регионам
    String region[] = {"Азия", "Америка", "Америка", "Европа", "Азия",
            "Европа", "Африка", "Европа", "Европа", "Америка"};

    Button btnAll, btnFunc, btnPeople, btnSort, btnGroup, btnHaving;
    EditText etFunc, etPeople, etRegionPeople;
    RadioGroup rgSort;

    DBHelper dbHelper;
    SQLiteDatabase db;

    /**
     * Called when the activity is first created.
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAll = (Button) findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnFunc = (Button) findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);

        btnPeople = (Button) findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(this);

        btnSort = (Button) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = (Button) findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);

        etFunc = (EditText) findViewById(R.id.etFunc);
        etPeople = (EditText) findViewById(R.id.etPeople);
        etRegionPeople = (EditText) findViewById(R.id.etRegionPeople);

        rgSort = (RadioGroup) findViewById(R.id.rgSort);

        dbHelper = new DBHelper(this);
        // подключаемся к базе
        db = dbHelper.getWritableDatabase();

        // проверка существования записей
        Cursor cursor = db.query("mytable", null, null, null, null, null, null);
        // если БД пустая
        if (cursor.getCount() == 0) {
            // .. то создаем объект ContentValues для хранения данных (таблиц)
            ContentValues cv = new ContentValues();
            // и заполним таблицу
            for (int i = 0; i < 10; i++) {
                cv.put("name", name[i]);
                cv.put("people", people[i]);
                cv.put("region", region[i]);
                Log.d(LOG_TAG, "id = " + db.insert("mytable", null, cv));
            }
        }
        cursor.close();
        dbHelper.close();
        // эмулируем нажатие кнопки btnAll
        onClick(btnAll);

    }

    @Override // метод отслеживания нажатия
    public void onClick(View v) {

        // подключаемся к БД
        db = dbHelper.getWritableDatabase();

        // читаем данные с экрана и присваиваем переменным
        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        // переменные для query
        String[] columns = null;         // столбцы
        String selection = null;         // выбор
        String[] selectionArgs = null;   //
        String groupBy = null;           // группа по
        String having = null;            // имеющий
        String orderBy = null;           // Сортировать по

        // объект "курсор" для работы с таблицей
        Cursor cursor = null;

        // определяем нажатую кнопку
        switch (v.getId()) {
            // если нажата кнопка "ВСЕ ЗАПИСИ"
            case R.id.btnAll:
                Log.d(LOG_TAG, "--- Все записи ---");
                // просто читаем в cursor нашу БД
                cursor = db.query("mytable", null, null, null, null, null, null);
                break;
            // если нажата кнопка "ФУНКЦИЯ"
            case R.id.btnFunc:
                Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
                // записываем в стринговый массив наименование нашей фунции (ее считываем с экрана)
                // и записываем в массив columns
                columns = new String[]{sFunc};
                // и в cursor уже считываем нашу БД с учетом columns(содержит функцию)
                cursor = db.query("mytable", columns, null, null, null, null, null);
                break;
            // если нажата кнопка "Население"
            case R.id.btnPeople:
                Log.d(LOG_TAG, "--- Население больше " + sPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[]{sPeople};
                cursor = db.query("mytable", null, selection, selectionArgs, null, null,
                        null);
                break;
            // если нажата кнопка "Население по региону"
            case R.id.btnGroup:
                Log.d(LOG_TAG, "--- Население по региону ---");
                columns = new String[]{"region", "sum(people) as people"};
                groupBy = "region";
                cursor = db.query("mytable", columns, null, null, groupBy, null, null);
                break;
            // если нажата кнопка "Население по региону >"
            case R.id.btnHaving:
                Log.d(LOG_TAG, "--- Регионы с населением больше " + sRegionPeople
                        + " ---");
                columns = new String[]{"region", "sum(people) as people"};
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                cursor = db.query("mytable", columns, null, null, groupBy, having, null);
                break;
            // если нажата кнопка "Сортировка"
            case R.id.btnSort:
                // сортировка по
                switch (rgSort.getCheckedRadioButtonId()) {
                    // наименование
                    case R.id.rName:
                        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;
                    // население
                    case R.id.rPeople:
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;
                    // регион
                    case R.id.rRegion:
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;
                }
                // в зависимости от выбранной RadioButton идет сортировка, переданная в orderBy
                cursor = db.query("mytable", null, null, null, null, null, orderBy);
                break;
        }

        // тут передаем результат в лог
        if (cursor != null) { // если объект cursor не пуст
            if (cursor.moveToFirst()) { // то переносим "курсор чтения" на первую строчку
                String str; // создаем строковую переменную
                do {
                    str = ""; // "обнуляем" строковую переменую
                    for (String cn : cursor.getColumnNames()) { // перебираем через for:each имена столбцов
                        // пишем в переменную str
                        str = str.concat(cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);  // здесь идет печать в лог

                } while (cursor.moveToNext()); // пока "курсор" переходит на новую строку
            }
            cursor.close(); // закрываем cursor
        } else
            // если объект cursor пуст - пишем это в лог
            Log.d(LOG_TAG, "Cursor is null");

        dbHelper.close();
    }

    // создаем класс DBHelper для работы с базой данных. для этого наследуем его от SQLiteOpenHelper..
    class DBHelper extends SQLiteOpenHelper {
        // ..переопределяем конструктор супер класса (оставляем только context)
        public DBHelper(Context context) {
            /* конструктор суперкласса, в который передаем:
            context - контекст
            mydb - название базы данных
            null – объект для работы с курсорами, нам пока не нужен, поэтому null
            1 – версия базы данных */
            super(context, "myDB", null, 1);
        }

        // а в методе onCreate (этот метод вызывается, если БД не существует и ее надо создавать)
        // создаем таблицу запросом (метод execSQL объекта SQLiteDatabase)
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями id, name, people и region
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement," + "name text,"
                    + "people integer," + "region text" + ");");
            // сам запрос - "create table mytable (id integer primary key autoincrement,name text,people integer,region text);"
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}