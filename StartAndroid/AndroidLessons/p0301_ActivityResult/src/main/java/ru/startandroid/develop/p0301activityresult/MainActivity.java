package ru.startandroid.develop.p0301activityresult;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvText;
    Button btnColor;
    Button btnAlign;

    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tvText = (TextView) findViewById(R.id.tvText);
        btnColor = (Button) findViewById(R.id.btnColor);
        btnAlign = (Button) findViewById(R.id.btnAlign);

        btnColor.setOnClickListener(this);
        btnAlign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.btnColor) {
            intent = new Intent(this, ColorActivity.class);
            startActivityForResult(intent, REQUEST_CODE_COLOR);
        }
        if (view.getId() == R.id.btnAlign) {
            intent = new Intent(this, AlignActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ALIGN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_COLOR) {
                int color = data.getIntExtra("color", Color.WHITE);
                tvText.setTextColor(color);
            } else if (requestCode == REQUEST_CODE_ALIGN) {
                int align = data.getIntExtra("alignment", Gravity.LEFT);
                tvText.setGravity(align);
            } else {
                Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
