package com.example.amichai.myapplication;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class settingsActivity extends AppCompatActivity {
    private Button smileyButton;
    private Button trumpButton;
    private Button vitasButton;
    private Button quagmireButton;
    private Button boratButton;
    private Button obamaButton;
    private Switch soundSwitch;

    private int screenWidth;
    private String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bundle bundle = getIntent().getExtras();

        getScreenWidth();

        theme = bundle.getString("theme");
        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        smileyButton = (Button) findViewById(R.id.smileyButton);
        trumpButton = (Button) findViewById(R.id.trumpButton);
        vitasButton = (Button) findViewById(R.id.vitasButton);
        quagmireButton = (Button) findViewById(R.id.quagmireButton);
        boratButton = (Button) findViewById(R.id.boratButton);
        obamaButton = (Button) findViewById(R.id.obamaButton);


        smileyButton.setBackgroundResource(R.drawable.smiley);
        trumpButton.setBackgroundResource(R.drawable.trump);
        vitasButton.setBackgroundResource(R.drawable.vitas);
        quagmireButton.setBackgroundResource(R.drawable.quagmire);
        boratButton.setBackgroundResource(R.drawable.borat);
        obamaButton.setBackgroundResource(R.drawable.obama);

        smileyButton.setHeight(screenWidth/2);
        smileyButton.setWidth(screenWidth/2);

        trumpButton.setHeight(screenWidth/2);
        trumpButton.setWidth(screenWidth/2);

        vitasButton.setHeight(screenWidth/2);
        vitasButton.setWidth(screenWidth/2);

        quagmireButton.setWidth(screenWidth/2);
        quagmireButton.setHeight(screenWidth/2);

        boratButton.setWidth(screenWidth/2);
        boratButton.setHeight(screenWidth/2);

        obamaButton.setWidth(screenWidth/2);
        obamaButton.setHeight(screenWidth/2);

        soundSwitch.setChecked(bundle.getBoolean("sound"));
        smileyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme = "classic";
                returnToMain();
            }
        });

        trumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme = "trump";
                returnToMain();
            }
        });


        vitasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme = "vitas";
                returnToMain();
            }
        });

        quagmireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme = "quagmire";
                returnToMain();
            }
        });

        boratButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme = "borat";
                returnToMain();
            }
        });
        obamaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme = "obama";
                returnToMain();
            }
        });
    }

    private void returnToMain(){
        Intent backToMain = new Intent(settingsActivity.this, MainActivity.class);

        backToMain.putExtra("theme",theme);
        backToMain.putExtra("sound",soundSwitch.isChecked());
        setResult(RESULT_OK,backToMain);
        finish();

    }

    @Override
    public void onBackPressed() {
        Intent backToMain = new Intent(settingsActivity.this, MainActivity.class);

        backToMain.putExtra("theme",theme);
        backToMain.putExtra("sound",soundSwitch.isChecked());
        setResult(RESULT_OK,backToMain);
        finish();
    }

    private void getScreenWidth(){

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = settingsActivity.this.getResources().getDisplayMetrics();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;

    }
}
