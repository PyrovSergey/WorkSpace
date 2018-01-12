package com.example.pyrov.testappsqlite1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pyrov.testappsqlite1.Data.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String LOG_TAG = "myLogs";

    EditText edName;
    EditText edPhone;
    EditText edEmail;
    EditText edId;

    Button buttonAdd;
    Button buttonRead;
    Button buttonClear;
    Button buttonUpdate;
    Button buttonDelete;

    DBHelper dbHelper;
    String nameTable = "myTable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edName = (EditText) findViewById(R.id.edit_name);
        edPhone = (EditText) findViewById(R.id.edit_phone);
        edEmail = (EditText) findViewById(R.id.edit_email);
        edId = (EditText) findViewById(R.id.edit_id);

        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonRead = (Button) findViewById(R.id.button_read);
        buttonClear = (Button) findViewById(R.id.button_clear);
        buttonUpdate = (Button) findViewById(R.id.button_update);
        buttonDelete = (Button) findViewById(R.id.button_delete);

        buttonAdd.setOnClickListener(this);
        buttonRead.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    public void clearEditText() {
        edName.setText("");
        edPhone.setText("");
        edEmail.setText("");
        edId.setText("");
    }

    @Override
    public void onClick(View view) {

        String name = edName.getText().toString();
        String phone = edPhone.getText().toString();
        String email = edEmail.getText().toString();
        String id = edId.getText().toString();

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        switch (view.getId()) {
            case R.id.button_delete:
                if (id.isEmpty()) {
                    if (phone.isEmpty() & email.isEmpty() & name.isEmpty()) {
                        return;
                    }
                    if (phone.isEmpty() & email.isEmpty()) {
                        Toast.makeText(this, "--- Удаление из таблицы имени---", Toast.LENGTH_SHORT).show();

                        int deleteCount = sqLiteDatabase.delete(nameTable, "name=?", new String[]{name});

                        Toast.makeText(this, "Строка с именем " + name + " удалена", Toast.LENGTH_SHORT).show();
                        clearEditText();
                    }

                } else {
                    Toast.makeText(this, "--- Удаление из таблицы---", Toast.LENGTH_SHORT).show();

                    int deleteCount = sqLiteDatabase.delete(nameTable, "id = " + id, null);

                    Toast.makeText(this, "количество удаленных строк = " + deleteCount, Toast.LENGTH_SHORT).show();

                    clearEditText();
                }
                break;
            case R.id.button_update:
                if (id.isEmpty()) {
                    return;
                }
                Toast.makeText(this, "--- Обновление таблицы: ---", Toast.LENGTH_SHORT).show();

                if (!name.isEmpty()) {
                    contentValues.put("name", name);
                }
                if (!phone.isEmpty()) {
                    contentValues.put("phone", phone);
                }
                if (!email.isEmpty()) {
                    contentValues.put("email", email);
                }

                int updateCount = sqLiteDatabase.update(nameTable, contentValues, "id = ?", new String[]{id});
                Toast.makeText(this, "updated rows count = " + updateCount, Toast.LENGTH_SHORT).show();

                clearEditText();
                break;
            case R.id.button_add:
                if (name.isEmpty() | phone.isEmpty() | email.isEmpty()) {
                    return;
                }

                contentValues.put("name", name);
                contentValues.put("phone", phone);
                contentValues.put("email", email);

                long rowID = sqLiteDatabase.insert(nameTable, null, contentValues);
                Log.d(LOG_TAG, "row inserted ID = " + rowID);
                Toast.makeText(this, "строка добавлена ID = " + rowID, Toast.LENGTH_SHORT).show();
                clearEditText();
                break;

            case R.id.button_read:

                Cursor cursor = sqLiteDatabase.query(nameTable, null, null, null, null, null, null);

                if (cursor.moveToNext()) {

                    int idColIndex = cursor.getColumnIndex("id");
                    int nameColIndex = cursor.getColumnIndex("name");
                    int phoneColIndex = cursor.getColumnIndex("phone");
                    int emailColIndex = cursor.getColumnIndex("email");

                    do {

                        Toast.makeText(this, "ID = " + cursor.getInt(idColIndex)
                                + ", name = " + cursor.getString(nameColIndex)
                                + ", phone number = " + cursor.getString(phoneColIndex)
                                + ", email = " + cursor.getString(emailColIndex), Toast.LENGTH_SHORT).show();

                    } while (cursor.moveToNext());
                } else {
                    Toast.makeText(this, "База пуста", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                clearEditText();
                break;

            case R.id.button_clear:
                int clearCount = sqLiteDatabase.delete(nameTable, null, null);
                Toast.makeText(this, "Таблица " + nameTable + " очищенна\nУдалено строк = " + clearCount, Toast.LENGTH_SHORT).show();
                clearEditText();
                break;
        }
        dbHelper.close();
    }
}
