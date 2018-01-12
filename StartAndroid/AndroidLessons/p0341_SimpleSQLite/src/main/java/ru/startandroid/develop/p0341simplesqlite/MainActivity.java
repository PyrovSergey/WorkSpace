package ru.startandroid.develop.p0341simplesqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

    // строка "лог" для отслеживания происодящего
    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, etEmail, etID;

    // создаем переменную для подключения и работы с базой данных
    DBHelper dbHelper;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etID = (EditText) findViewById(R.id.etID);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }


    @Override // метод отслеживания нажатия
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода и присваиваем их переменным
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            // если нажата кнопка "ADD"
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца / значение
                cv.put("name", name);
                cv.put("email", email);
                // вставляем запись и получаем ее ID (метод insert возвращает ID вставленной строки)
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            // если нажата кнопка "READ"
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor (саму таблицу)
                // тот самый метод query, который позволяет "считать" базу
                // на вход ему подается имя таблицы, список запрашиваемых полей, условия выборки, группировка и сортировка.
                Cursor cursor = db.query("mytable", null, null, null, null, null, null);
                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (cursor.moveToFirst()) {
                    // определяем номера столбцов по имени в выборке
                    int idColIndex = cursor.getColumnIndex("id");
                    int nameColIndex = cursor.getColumnIndex("name");
                    int emailColIndex = cursor.getColumnIndex("email");
                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + cursor.getInt(idColIndex) +
                                        ", name = " + cursor.getString(nameColIndex) +
                                        ", email = " + cursor.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (cursor.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                cursor.close();
                break;
            // если нажата кнопка "CLEAR"
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи (вход delete передаем имя таблицы и null в качестве условий для удаления)
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
            // если нажата кнопка "UPDATE"
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) { // если поле id пустое - ничего не делаем
                    break;
                }
                Log.d(LOG_TAG, "--- Update mytable: ---");
                // подготовим значения для обновления (они считываются при любом нажатии)
                cv.put("name", name);
                cv.put("email", email);
                // обновляем по id (метод update возвращает кол-во обновленных строк)
                int updCount = db.update("mytable", cv, "id = " + id,
                        null);
                Log.d(LOG_TAG, "updated rows count = " + updCount);
                break;
            // если нажата кнопка "DELETE"
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) { // если поле id пустое - ничего не делаем
                    break;
                }
                Log.d(LOG_TAG, "--- Delete from mytable: ---");
                // удаляем по id (метод delete возвращает кол-во удаленных строк)
                int delCount = db.delete("mytable", "id = " + id, null);
                Log.d(LOG_TAG, "deleted rows count = " + delCount);
                break;
        }
        // закрываем подключение к БД
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
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем  таблицу mytable с полями id, name и email
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "email text" + ");");
            // сам запрос - "create table mytable (id integer primary key autoincrement,name text,email text);"
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}