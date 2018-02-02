package com.example.amichai.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String HIGH_SCORE_FILE_NAME = "highScores";

    private Button beginnerButton;
    private Button intermediateButton;
    private Button proButton;
    private Switch customSwitch;

    private Spinner boardSizeSpinner;
    private Spinner numberOfMinesSpinner;

    private Button startGameButton;
    private Button settingsButton;
    private Button gameThemeButton;

    private int boardSize;
    private int numberOfMines;

    private int spinnerBoardSizeSelected;
    private int spinnerNumberOfMinesSelected;

    public static GameTheme gameTheme;

    ArrayList<String> numOfMines;

    private String gameMode;

    private int gameTime;

    private MediaPlayer mediaPlayer;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameTheme = new GameTheme();

        getHighScores();

        mediaPlayer = MediaPlayer.create(this, R.raw.main_menu_music);

        if (settingsActivity.soundOn){
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
        settingsActivity.soundOn = true;
        setLevelOrder();

        GameTheme.currentGameLevel = GameTheme.classic;


        beginnerButton = (Button) findViewById(R.id.easyButton);
        beginnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameMode = "easy";
                boardSize = GameTheme.currentGameLevel.getBoardSizeEasyMode();
                numberOfMines = GameTheme.currentGameLevel.getNumberOfBombsEasyMode();
                startGame();
            }
        });
        intermediateButton = (Button) findViewById(R.id.intermediateButton);
        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameMode = "intermediate";
                boardSize = GameTheme.currentGameLevel.getBoardSizeIntermediateMode();
                numberOfMines = GameTheme.currentGameLevel.getNumberOfBombsIntermediateMode();
                startGame();
            }
        });



        proButton = (Button) findViewById(R.id.proButton);
        proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameMode = "pro";
                boardSize = GameTheme.currentGameLevel.getBoardSizeProMode();
                numberOfMines = GameTheme.currentGameLevel.getNumberOfBombsHardMode();
                startGame();
            }
        });

        numOfMines = new ArrayList<String>();

        boardSizeSpinner = (Spinner) findViewById(R.id.boardSizeSpinner);
        numberOfMinesSpinner = (Spinner) findViewById(R.id.numberOfMinesSpinner);

        customSwitch= (Switch) findViewById(R.id.customSwitch);
        customSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    spinnerBoardSizeSelected = 3;
                    spinnerNumberOfMinesSelected = 1;
                    startGameButton.setVisibility(View.VISIBLE);
                    boardSizeSpinner.setVisibility(View.VISIBLE);
                    numberOfMinesSpinner.setVisibility(View.VISIBLE);
                } else {
                    startGameButton.setVisibility(View.INVISIBLE);
                    boardSizeSpinner.setVisibility(View.INVISIBLE);
                    numberOfMinesSpinner.setVisibility(View.INVISIBLE);
                }
            }
        });

        boardSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerBoardSizeSelected = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                setNumberOfMinesInSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinnerBoardSizeSelected = 3;
                setNumberOfMinesInSpinner();
            }
        });




        numberOfMinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerNumberOfMinesSelected = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        startGameButton = (Button) findViewById(R.id.startGameButton);
        startGameButton.setVisibility(View.INVISIBLE);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameMode = "custom";
                boardSize = spinnerBoardSizeSelected;
                numberOfMines = spinnerNumberOfMinesSelected;
                startGame();
            }
        });

        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setBackgroundResource(R.drawable.settings_icon);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, settingsActivity.class);
                startActivityForResult(i,1);
            }
        });

        gameThemeButton = (Button) findViewById(R.id.gameThemeButton);
        GameTheme.theme.setThemeButton(gameThemeButton);

        gameThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, gameThemeActivity.class);
                startActivityForResult(i,3);
            }
        });

        addBestTimeToButtons();
        setButtonsBackground();
    }

    private void getHighScores() {
        SharedPreferences highScores = getSharedPreferences(HIGH_SCORE_FILE_NAME, Context.MODE_PRIVATE);

        for (int i = 0; i < GameTheme.NUMBER_OF_LEVELS; i++) {
            GameTheme.gameLevels.get(i).setBestTimeEasyMode(highScores.getInt(GameTheme.gameLevels.get(i).getThemeName() + " easy", Integer.MAX_VALUE));
            GameTheme.gameLevels.get(i).setBestIntermediateMode(highScores.getInt(GameTheme.gameLevels.get(i).getThemeName() + " intermediate", Integer.MAX_VALUE));
            GameTheme.gameLevels.get(i).setBestTimeProMode(highScores.getInt(GameTheme.gameLevels.get(i).getThemeName() + " pro", Integer.MAX_VALUE));
        }
        for (int i=1; i<gameTheme.NUMBER_OF_LEVELS; i++){
            GameTheme.currentGameLevel = GameTheme.gameLevels.get(i);
            if (gameTheme.allModesWon(GameTheme.currentGameLevel)){
                GameTheme.gameLevels.get(i+1).setLockedStatus(false);
            } else {
                return;
            }
        }
    }



    private void setLevelOrder() {
        for (int i=0; i<GameTheme.NUMBER_OF_LEVELS-1; i++){
            GameTheme.gameLevels.get(i).setNextLevel(GameTheme.gameLevels.get(i+1));
        }
    }

    public void addBestTimeToButtons() {
        if (GameTheme.currentGameLevel.getBestTimeEasyMode() != Integer.MAX_VALUE){
            beginnerButton.setText("EASY\nBest time: " + Integer.toString(GameTheme.currentGameLevel.getBestTimeEasyMode()));
        } else {
            beginnerButton.setText("EASY");
        }

        if (GameTheme.currentGameLevel.getBestIntermediateMode() != Integer.MAX_VALUE){
            intermediateButton.setText("INTERMEDIATE\nBest time: " + Integer.toString(GameTheme.currentGameLevel.getBestIntermediateMode()));
        } else {
            intermediateButton.setText("INTERMEDIATE");
        }

        if (GameTheme.currentGameLevel.getBestTimeProMode() != Integer.MAX_VALUE){
            proButton.setText("PRO\nBest time: " + Integer.toString(GameTheme.currentGameLevel.getBestTimeProMode()));
        } else {
            proButton.setText("PRO");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                if (data.getBooleanExtra("gameWon", false)) {
                    gameTime = data.getIntExtra("time", 0);
                    setNewWinningTimeAccordingToMode();
                    if(gameTheme.allModesWon(GameTheme.currentGameLevel)){
                        openNextLevelIfLocked();
                    }
                    saveHighScore();
                }
                if (data.getBooleanExtra("refreshGame",false) == true){
                    startGame();
                }
            }
            if (requestCode == 3){
                GameTheme.theme.setThemeButton(gameThemeButton);
            }
        }
        setButtonsBackground();
        addBestTimeToButtons();
    }

    private void saveHighScore() {
        SharedPreferences highScores = getSharedPreferences(HIGH_SCORE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = highScores.edit();

        for (int i=0; i<GameTheme.NUMBER_OF_LEVELS; i++) {
            editor.putInt(GameTheme.gameLevels.get(i).getThemeName() + " easy", GameTheme.gameLevels.get(i).getBestTimeEasyMode());
            editor.putInt(GameTheme.gameLevels.get(i).getThemeName() + " intermediate", GameTheme.gameLevels.get(i).getBestIntermediateMode());
            editor.putInt(GameTheme.gameLevels.get(i).getThemeName() + " pro", GameTheme.gameLevels.get(i).getBestTimeProMode());
        }
        editor.apply();

    }



    private void openNextLevelIfLocked() {
        if (GameTheme.currentGameLevel.getNextLevel().getLockedStatus() == true){
            GameTheme.currentGameLevel.getNextLevel().setLockedStatus(false);
            Toast.makeText(getApplicationContext(),GameTheme.currentGameLevel.getNextLevel().getThemeName()+" unlocked!",Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonsBackground() {
        if (GameTheme.currentGameLevel.getBestTimeEasyMode() < Integer.MAX_VALUE) {
            beginnerButton.setBackgroundResource(R.drawable.won);
        } else {
            beginnerButton.setBackgroundResource(R.drawable.unwon);
        }
        if (GameTheme.currentGameLevel.getBestIntermediateMode() < Integer.MAX_VALUE){
            intermediateButton.setBackgroundResource(R.drawable.won);
        } else {
            intermediateButton.setBackgroundResource(R.drawable.unwon);
        }

        if (GameTheme.currentGameLevel.getBestTimeProMode() < Integer.MAX_VALUE){
            proButton.setBackgroundResource(R.drawable.won);
        } else {
            proButton.setBackgroundResource(R.drawable.unwon);
        }
    }

    private void setNewWinningTimeAccordingToMode() {
        if (gameMode == "easy") {
            if (GameTheme.currentGameLevel.getBestTimeEasyMode() > gameTime) {
                GameTheme.currentGameLevel.setBestTimeEasyMode(gameTime);
            }
        } else if (gameMode == "intermediate"){
            if (GameTheme.currentGameLevel.getBestIntermediateMode() > gameTime){
                GameTheme.currentGameLevel.setBestIntermediateMode(gameTime);
            }
        } else if (gameMode == "pro"){
            if (GameTheme.currentGameLevel.getBestTimeProMode() > gameTime){
                GameTheme.currentGameLevel.setBestTimeProMode(gameTime);
            }
        }

    }

    private void setNumberOfMinesInSpinner() {
        numOfMines.clear();

        for (int i=0; i< spinnerBoardSizeSelected*spinnerBoardSizeSelected-1; i++)
            numOfMines.add(Integer.toString(i+1));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,numOfMines);

        numberOfMinesSpinner.setAdapter(adapter);
    }

    private void startGame() {
        Intent game = new Intent(MainActivity.this, SecondActivity.class);
        game.putExtra("boardSize",boardSize);
        game.putExtra("numberOfMines",numberOfMines);
        startActivityForResult(game,2);
    }


}
