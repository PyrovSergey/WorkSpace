package com.example.pyrov.testapp003;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton buttonSetting;
    ImageButton buttonAdd;

    public static final String LOG_TAG = "MyTag" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Start main Activity");

        buttonSetting = (ImageButton) findViewById(R.id.setting);
        buttonAdd = (ImageButton) findViewById(R.id.add);

        buttonSetting.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.setting:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.add:
                intent = new Intent(this, AddDataActivity.class);
                startActivity(intent);
                break;
        }
    }
}
