package com.example.amichai.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class settingsActivity extends AppCompatActivity {
    private Button smileyButton;
    private Button trumpButton;
    private Button vitasButton;
    private Button quagmireButton;
    private Button boratButton;
    private Button obamaButton;
    private Switch soundSwitch;

    private String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bundle bundle = getIntent().getExtras();

        theme = bundle.getString("theme");

        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        smileyButton = (Button) findViewById(R.id.smileyButton);
        trumpButton = (Button) findViewById(R.id.trumpButton);
        vitasButton = (Button) findViewById(R.id.vitasButton);
        quagmireButton = (Button) findViewById(R.id.quagmireButton);
        boratButton = (Button) findViewById(R.id.boratButton);
        obamaButton = (Button) findViewById(R.id.obamaButton);



        smileyButton.setBackgroundResource(R.drawable.smiley);
        if (MainActivity.trump.getLockedStatus() == true) {
            trumpButton.setBackgroundResource(R.drawable.trump_locked);
        } else {
            trumpButton.setBackgroundResource(R.drawable.trump);
        }

        if (MainActivity.vitas.getLockedStatus() == true) {
            vitasButton.setBackgroundResource(R.drawable.vitas_locked);
        } else {
            vitasButton.setBackgroundResource(R.drawable.vitas);
        }

        if (MainActivity.quagmire.getLockedStatus() == true) {
            quagmireButton.setBackgroundResource(R.drawable.quagmire_locked);;
        } else {
            quagmireButton.setBackgroundResource(R.drawable.quagmire);
        }
        if (MainActivity.borat.getLockedStatus() == true) {
            boratButton.setBackgroundResource(R.drawable.borat_locked);
        } else {
            boratButton.setBackgroundResource(R.drawable.borat);
        }
        if (MainActivity.obama.getLockedStatus() == true) {
            obamaButton.setBackgroundResource(R.drawable.obama_locked);
        } else {
            obamaButton.setBackgroundResource(R.drawable.obama);
        }



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
                if (MainActivity.trump.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    theme = "trump";
                    returnToMain();
                }
            }
        });

        vitasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.vitas.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    theme = "vitas";
                    returnToMain();
                }
            }
        });

        quagmireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.quagmire.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    theme = "quagmire";
                    returnToMain();
                }
            }
        });

        boratButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.borat.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    theme = "borat";
                    returnToMain();
                }
            }
        });

        obamaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.obama.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    theme = "obama";
                    returnToMain();
                }
            }
        });
    }

    private void levelLockedMessage() {
        Toast.makeText(getApplicationContext(), "Level locked", Toast.LENGTH_SHORT).show();
    }

    private void returnToMain() {
        Intent backToMain = new Intent(settingsActivity.this, MainActivity.class);

        backToMain.putExtra("theme", theme);
        backToMain.putExtra("sound", soundSwitch.isChecked());
        setResult(RESULT_OK, backToMain);
        finish();


    }

    @Override
    public void onBackPressed() {
        Intent backToMain = new Intent(settingsActivity.this, MainActivity.class);
        backToMain.putExtra("theme", theme);
        backToMain.putExtra("sound", soundSwitch.isChecked());
        setResult(RESULT_OK, backToMain);
        finish();
    }
}