package ru.startandroid.develop.p0301activityresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class AlignActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLeft;
    Button btnCenter;
    Button btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.align);

        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnCenter = (Button) findViewById(R.id.btnCenter);
        btnRight = (Button) findViewById(R.id.btnRight);

        btnLeft.setOnClickListener(this);
        btnCenter.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.btnLeft) {
            intent.putExtra("alignment", Gravity.LEFT);
        }
        if (view.getId() == R.id.btnCenter) {
            intent.putExtra("alignment", Gravity.CENTER);
        }
        if (view.getId() == R.id.btnRight) {
            intent.putExtra("alignment", Gravity.RIGHT);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
