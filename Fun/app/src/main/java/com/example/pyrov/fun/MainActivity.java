package com.example.pyrov.fun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch aSwitchWork;
    Switch aSwitchSocialLife;
    Switch aSwitchSleep;

    boolean hasWork;
    boolean hasSocialLife;
    boolean hasSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitchWork = (Switch) findViewById(R.id.work);
        aSwitchSocialLife = (Switch) findViewById(R.id.social_life);
        aSwitchSleep = (Switch) findViewById(R.id.sleep);

        aSwitchWork.setOnCheckedChangeListener(this);
        aSwitchSocialLife.setOnCheckedChangeListener(this);
        aSwitchSleep.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.work:
                if (b) {
                    hasWork = true;
                    if (hasSocialLife) {
                        hasSocialLife = false;
                        aSwitchSocialLife.setChecked(false);
                    }
                    else if  (hasSocialLife & hasSleep) {
                        hasSleep = false;
                        aSwitchSleep.setChecked(false);
                    }

                } else {
                    hasWork = false;
                }
                break;
            case R.id.social_life:
                if (b) {
                    hasSocialLife = true;
                    if (hasWork && hasSleep) {
                        hasSleep = false;
                        aSwitchSleep.setChecked(false);
                    }
                } else {
                    hasSocialLife = false;
                }
                break;
            case R.id.sleep:
                if (b) {
                    hasSleep = true;
                    if (hasWork & hasSocialLife) {
                        hasSocialLife = false;
                        aSwitchSocialLife.setChecked(false);
                    }

                } else {
                    hasSleep = false;
                }
                break;
        }
    }
}
