package com.example.amichai.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class settingsActivity extends AppCompatActivity {
    Button smileyButton;
    Button trumpButton;
    Button vitasButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        smileyButton = (Button) findViewById(R.id.smileyButton);
        trumpButton = (Button) findViewById(R.id.trumpButton);
        vitasButton = (Button) findViewById(R.id.vitasButton);

        smileyButton.setBackgroundResource(R.drawable.smiley);
        trumpButton.setBackgroundResource(R.drawable.trump);
        vitasButton.setBackgroundResource(R.drawable.vitas);

        smileyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMain("classic");
            }
        });

        trumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMain("trump");
            }
        });

        vitasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToMain("vitas");
            }
        });

    }
    void returnToMain(String theme){
        Intent backToMain = new Intent(settingsActivity.this, MainActivity.class);

        backToMain.putExtra("theme",theme);
        setResult(RESULT_OK,backToMain);
        finish();

    }
}
