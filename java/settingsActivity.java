package com.example.amichai.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class settingsActivity extends AppCompatActivity {

    private Switch soundSwitch;

    public static boolean soundOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        soundSwitch = (Switch) findViewById(R.id.soundSwitch);

        soundSwitch.setChecked(soundOn);

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                soundOn = b;
            }
        });
    }
    private void returnToMain() {
        Intent backToMain = new Intent(settingsActivity.this, MainActivity.class);

        setResult(RESULT_OK, backToMain);
        finish();


    }

    @Override
    public void onBackPressed() {
        Intent backToMain = new Intent(settingsActivity.this, MainActivity.class);
        setResult(RESULT_OK, backToMain);
        finish();
    }
}