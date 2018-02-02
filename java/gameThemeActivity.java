package com.example.amichai.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class gameThemeActivity extends AppCompatActivity {

    private Button smileyButton;
    private Button trumpButton;
    private Button vitasButton;
    private Button quagmireButton;
    private Button boratButton;
    private Button obamaButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_theme);


        smileyButton = (Button) findViewById(R.id.smileyButton);
        trumpButton = (Button) findViewById(R.id.trumpButton);
        vitasButton = (Button) findViewById(R.id.vitasButton);
        quagmireButton = (Button) findViewById(R.id.quagmireButton);
        boratButton = (Button) findViewById(R.id.boratButton);
        obamaButton = (Button) findViewById(R.id.obamaButton);



        smileyButton.setBackgroundResource(R.drawable.smiley);

        if (GameTheme.trump.getLockedStatus() == true) {
            trumpButton.setBackgroundResource(R.drawable.trump_locked);
        } else if (MainActivity.gameTheme.allModesWon(GameTheme.trump)) {
            trumpButton.setBackgroundResource(R.drawable.trump_sunglasses);
        } else {
            trumpButton.setBackgroundResource(R.drawable.trump);
        }

        if (GameTheme.vitas.getLockedStatus() == true) {
            vitasButton.setBackgroundResource(R.drawable.vitas_locked);
        }  else if (MainActivity.gameTheme.allModesWon(GameTheme.vitas)) {
            vitasButton.setBackgroundResource(R.drawable.vitas_sunglasses);
        } else {
            vitasButton.setBackgroundResource(R.drawable.vitas);
        }

        if (GameTheme.quagmire.getLockedStatus() == true) {
            quagmireButton.setBackgroundResource(R.drawable.quagmire_locked);;
        }  else if (MainActivity.gameTheme.allModesWon(GameTheme.quagmire)) {
            quagmireButton.setBackgroundResource(R.drawable.quagmire_sunglasses);
        }else {
            quagmireButton.setBackgroundResource(R.drawable.quagmire);
        }

        if (GameTheme.borat.getLockedStatus() == true) {
            boratButton.setBackgroundResource(R.drawable.borat_locked);
        } else if (MainActivity.gameTheme.allModesWon(GameTheme.borat)) {
            boratButton.setBackgroundResource(R.drawable.borat_sunglasses);
        } else {
            boratButton.setBackgroundResource(R.drawable.borat);
        }
        if (GameTheme.obama.getLockedStatus() == true) {
            obamaButton.setBackgroundResource(R.drawable.obama_locked);
        }  else if (MainActivity.gameTheme.allModesWon(GameTheme.obama)) {
            obamaButton.setBackgroundResource(R.drawable.obama_sunglasses);
        }else {
            obamaButton.setBackgroundResource(R.drawable.obama);
        }



        smileyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameTheme.currentGameLevel = GameTheme.classic;
                GameTheme.theme.setThemeName("classic");
                returnToMain();
            }
        });

        trumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameTheme.trump.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    GameTheme.currentGameLevel = GameTheme.trump;
                    GameTheme.theme.setThemeName("trump");
                    returnToMain();
                }
            }
        });

        vitasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameTheme.vitas.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    GameTheme.currentGameLevel = GameTheme.vitas;
                    GameTheme.theme.setThemeName("vitas");
                    returnToMain();
                }
            }
        });

        quagmireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameTheme.quagmire.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    GameTheme.currentGameLevel = GameTheme.quagmire;
                    GameTheme.theme.setThemeName("quagmire");
                    returnToMain();
                }
            }
        });

        boratButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameTheme.borat.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    GameTheme.currentGameLevel = GameTheme.borat;
                    GameTheme.theme.setThemeName("borat");
                    returnToMain();
                }
            }
        });

        obamaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameTheme.obama.getLockedStatus() == true){
                    levelLockedMessage();
                } else {
                    GameTheme.currentGameLevel = GameTheme.obama;
                    GameTheme.theme.setThemeName("obama");
                    returnToMain();
                }
            }
        });
    }

    private void levelLockedMessage() {
        Toast.makeText(getApplicationContext(), "Level locked", Toast.LENGTH_SHORT).show();
    }

    private void returnToMain() {
        Intent backToMain = new Intent(gameThemeActivity.this, MainActivity.class);
        setResult(RESULT_OK, backToMain);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent backToMain = new Intent(gameThemeActivity.this, MainActivity.class);
        setResult(RESULT_OK, backToMain);
        finish();
    }

}
