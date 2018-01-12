package ru.startandroid.develop.dynamiclayout2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Выносим вверх все нужные переменные
    LinearLayout llMain;
    RadioGroup radioGroup;
    EditText editText;
    Button buttonCreate;
    Button buttonClear;

    // интовая переменная для сокращения кода == wrap_content
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // находим по ID все необходимые ссылки и присваеваем их нашим переменным
        llMain = (LinearLayout) findViewById(R.id.llMain);
        radioGroup = (RadioGroup) findViewById(R.id.rgGravity);
        editText = (EditText) findViewById(R.id.etName);
        buttonCreate = (Button) findViewById(R.id.btnCreate);
        buttonClear = (Button) findViewById(R.id.btnClear);
        // присваеваем нашим двум кнопкам "слушателя"
        // т.к. наш класс реализует интерфейс OnClickListener, то передаем "this"
        buttonCreate.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        // если нажата кнопка "Create"..
        if (view.getId() == R.id.btnCreate) {
            //.. выполняем следующее
            // Созданим LayoutParams c шириной и высотой по содержимому
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);
            // создаем переменнаю для хранения значения выравнивания (по умолчанию пусть будет LEFT)
            int btnGravity = Gravity.LEFT;
            // определяем, какой RadioButton "чекнут" и соответственно заполняем btnGravity
            if (radioGroup.getCheckedRadioButtonId() == R.id.rbLeft) {
                btnGravity = Gravity.LEFT;
            }
            if (radioGroup.getCheckedRadioButtonId() == R.id.rbCenter) {
                btnGravity = Gravity.CENTER_HORIZONTAL;
            }
            if (radioGroup.getCheckedRadioButtonId() == R.id.rbRight) {
                btnGravity = Gravity.RIGHT;
            }
            // переносим полученное значение выравнивания в LayoutParams
            lParams.gravity = btnGravity;
            // создаем Button, пишем текст и добавляем в LinearLayout
            Button button = new Button(this);
            button.setText(editText.getText().toString());
            llMain.addView(button, lParams);
        }
        // если нажата кнопка "Clear"
        if (view.getId() == R.id.btnClear) {
            // удаляем все созданные кнопки
            llMain.removeAllViews();
            // выводим на экран, что мы все удалили
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
        }
    }
}
