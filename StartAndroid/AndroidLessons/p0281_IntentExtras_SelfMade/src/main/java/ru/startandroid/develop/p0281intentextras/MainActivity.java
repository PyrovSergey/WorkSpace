package ru.startandroid.develop.p0281intentextras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName;
    EditText editTextLastName;
    EditText editTextPhone;

    Button buttonSubmint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editName);
        editTextLastName = (EditText) findViewById(R.id.editLastName);
        editTextPhone = (EditText) findViewById(R.id.editPhone);
        buttonSubmint = (Button) findViewById(R.id.Submint);
        buttonSubmint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("name", editTextName.getText().toString() );
        intent.putExtra("lastName", editTextLastName.getText().toString());
        intent.putExtra("phone",editTextPhone.getText().toString() );
        startActivity(intent);
    }
}
