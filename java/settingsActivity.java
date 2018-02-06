package com.example.amichai.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class settingsActivity extends AppCompatActivity {

    private Switch soundSwitch;
    private Button resetButton;
    public static boolean soundOn;

    private TextView totalGamesPlayed;
    private TextView totalGamesWon;

    private TextView numberOfProGamesPlayed;
    private TextView numberOfIntermediateGamesPlayed;
    private TextView numberOfBeginnerGamesPlayed;

    private TextView numberOfProGamesWon;
    private TextView numberOfIntermediateGamesWon;
    private TextView numberOfBeginnerGamesWon;

    private TextView totalWinningPercentage;
    private TextView beginnerWinningPercentage;
    private TextView intermediateWinningPercentage;
    private TextView proWinningPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        resetButton = (Button) findViewById(R.id.resetButton);

        totalGamesPlayed = (TextView) findViewById(R.id.totalGamesPlayed);
        totalGamesWon = (TextView) findViewById(R.id.totalGamesWon);;

        numberOfProGamesPlayed = (TextView) findViewById(R.id.proGamesPlayed);
        numberOfIntermediateGamesPlayed = (TextView) findViewById(R.id.intermediateGamesPlayed);
        numberOfBeginnerGamesPlayed = (TextView) findViewById(R.id.beginnerGamesPlayed);

        numberOfProGamesWon = (TextView) findViewById(R.id.proGamesWon);
        numberOfIntermediateGamesWon = (TextView) findViewById(R.id.intermediateGamesWon);
        numberOfBeginnerGamesWon = (TextView) findViewById(R.id.beginnerGamesWon);

        totalWinningPercentage = (TextView) findViewById(R.id.totalWinningPercentage);
        beginnerWinningPercentage = (TextView) findViewById(R.id.beginnerWinningPercentage);
        intermediateWinningPercentage = (TextView) findViewById(R.id.intermediateWinningPercentage);
        proWinningPercentage = (TextView) findViewById(R.id.proWinningPercentage);

        totalGamesPlayed.setText("Total games played: " + Integer.toString(MainActivity.gameStats.getNumberOfGamesPlayed()));
        totalGamesWon.setText("Total games won: " + Integer.toString(MainActivity.gameStats.getNumberOfProGamesWon()));

        numberOfBeginnerGamesPlayed.setText("Beginner games played: " + Integer.toString(MainActivity.gameStats.getNumberOfBeginnerGamesPlayed()));
        numberOfIntermediateGamesPlayed.setText("Intermediate games played: "+ Integer.toString(MainActivity.gameStats.getNumberOfIntermediateGamesPlayed()));
        numberOfProGamesPlayed.setText("Pro games played: " + Integer.toString(MainActivity.gameStats.getNumberOfProGamesPlayed()));


        numberOfProGamesWon.setText("Pro games won: " + Integer.toString(MainActivity.gameStats.getNumberOfProGamesWon()));
        numberOfIntermediateGamesWon.setText("Intermediate games won: " + Integer.toString(MainActivity.gameStats.getNumberOfIntermediateGamesWon()));
        numberOfBeginnerGamesWon.setText("Beginner games won: " + Integer.toString(MainActivity.gameStats.getNumberOfBeginnerGamesWon()));

        totalWinningPercentage.setText("Total winning %: " + Float.toString(MainActivity.gameStats.getTotalWinningPercentage())+"%");
        beginnerWinningPercentage.setText("Beginner winning %: " + Float.toString(MainActivity.gameStats.getWinningPercentageBeginnerMode())+"%");
        intermediateWinningPercentage.setText("Intermediate winning %: " + Float.toString(MainActivity.gameStats.getWinningPercentageIntermediateMode())+"%");
        proWinningPercentage.setText("Pro winning %: " + Float.toString(MainActivity.gameStats.getWinningPercentageProMode())+"%");

        soundSwitch.setChecked(soundOn);


        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                soundOn = b;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(settingsActivity.this).create();

                alertDialog.setTitle("WARNING");
                alertDialog.setMessage("Are you sure? All high scores and levels will be reset");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                       getApplicationContext().getSharedPreferences(MainActivity.HIGH_SCORE_FILE_NAME,0).edit().clear().commit();
                        Toast.makeText(getApplicationContext(), "Levels and scores reset", Toast.LENGTH_LONG).show();
                        dataReset();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void dataReset() {
        Intent backToMain = new Intent(settingsActivity.this, MainActivity.class);
        setResult(RESULT_OK,backToMain);
        backToMain.putExtra("game reset",true);
        finish();
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