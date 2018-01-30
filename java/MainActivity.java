package com.example.amichai.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button beginnerButton;
    private Button intermediateButton;
    private Button proButton;
    private Switch customSwitch;

    private Spinner boardSizeSpinner;
    private Spinner numberOfMinesSpinner;

    private Button startGameButton;
    private Button settingsButton;

    private int boardSize;
    private int numberOfMines;

    private Theme theme;
    private boolean soundOn;
    ArrayList<String> numOfMines;

    public static final String DEFAULT_THEME= "classic";


    public static GameLevel classic = new GameLevel("classic");
    public static GameLevel trump = new GameLevel("trump");
    public static GameLevel vitas = new GameLevel("vitas");
    public static GameLevel quagmire= new GameLevel("quagmire");
    public static GameLevel borat = new GameLevel("borat");
    public static GameLevel obama = new GameLevel("obama");

    public GameLevel currentGameLevel;

    private String gameMode;

    private TextView timeEasyModeTextView;
    private TextView timeIntermediateModeTextView;
    private TextView timeProModeTextView;


    private Button themeButton;

    private int gameTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themeButton = (Button) findViewById(R.id.themeButton);

        trump.setLockedStatus(false);


        timeEasyModeTextView = (TextView) findViewById(R.id.timeEasyTextView);
        timeIntermediateModeTextView = (TextView) findViewById(R.id.timeIntermediateTextView);
        timeProModeTextView = (TextView) findViewById(R.id.timeProTextView);

        soundOn = true;

        theme = new Theme(DEFAULT_THEME);
        currentGameLevel = classic;

        setGameLevel();
        setBestTimeTextViews();
        setLevelOrder();


        theme.setThemeButton(themeButton);

        themeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    theme.themeButtonPressed(themeButton);
                    return false;
                }
                theme.setThemeButton(themeButton);
                return true;
            }
        });
        beginnerButton = (Button) findViewById(R.id.easyButton);
        beginnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameMode = "easy";
                boardSize = currentGameLevel.getBoardSizeEasyMode();
                numberOfMines = currentGameLevel.getNumberOfBombsEasyMode();
                startGame();
            }
        });
        intermediateButton = (Button) findViewById(R.id.intermediateButton);
        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameMode = "intermediate";
                boardSize = currentGameLevel.getBoardSizeIntermediateMode();
                numberOfMines = currentGameLevel.getNumberOfBombsIntermediateMode();
                startGame();
            }
        });



        proButton = (Button) findViewById(R.id.proButton);
        proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameMode = "pro";
                boardSize = currentGameLevel.getBoardSizeProMode();
                numberOfMines = currentGameLevel.getNumberOfBombsHardMode();
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
                    boardSize = 3;
                    numberOfMines = 1;
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
                boardSize = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                setNumberOfMinesInSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                boardSize = 3;
                setNumberOfMinesInSpinner();
            }
        });




        numberOfMinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numberOfMines = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
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
                startGame();
            }
        });

        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setBackgroundResource(R.drawable.settings_icon);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, settingsActivity.class);
                i.putExtra("sound",soundOn);
                i.putExtra("theme",theme.getThemeName());
                startActivityForResult(i,1);
            }
        });
        setButtonsBackground();
    }


    private void setLevelOrder() {
        trump.setNextLevel(quagmire);
        quagmire.setNextLevel(vitas);
        vitas.setNextLevel(borat);
        borat.setNextLevel(obama);
    }

    public void setBestTimeTextViews() {
        timeEasyModeTextView.setText(Integer.toString(currentGameLevel.getBestTimeEasyMode()));
        timeIntermediateModeTextView.setText(Integer.toString(currentGameLevel.getBestIntermediateMode()));
        timeProModeTextView.setText(Integer.toString(currentGameLevel.getBestTimeProMode()));

        if (currentGameLevel.getBestTimeEasyMode() != Integer.MAX_VALUE){
            timeEasyModeTextView.setVisibility(View.VISIBLE);
        } else {
            timeEasyModeTextView.setVisibility(View.INVISIBLE);
        }

        if (currentGameLevel.getBestIntermediateMode() != Integer.MAX_VALUE){
            timeIntermediateModeTextView.setVisibility(View.VISIBLE);
        } else {
            timeIntermediateModeTextView.setVisibility(View.INVISIBLE);
        }

        if (currentGameLevel.getBestTimeProMode() != Integer.MAX_VALUE){
            timeProModeTextView.setVisibility(View.VISIBLE);
        } else {
            timeProModeTextView.setVisibility(View.INVISIBLE);
        }
    }

    void setGameLevel(){
        switch(theme.getThemeName()){
            case "classic":
                currentGameLevel = classic; break;
            case "trump":
                currentGameLevel = trump; break;
            case "vitas":
                currentGameLevel = vitas; break;
            case "quagmire":
                currentGameLevel = quagmire; break;
            case "borat":
                currentGameLevel = borat; break;
            case "obama":
                currentGameLevel = obama; break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                theme.setThemeName(data.getStringExtra("theme"));
                soundOn = data.getBooleanExtra("sound", true);
                Toast.makeText(getApplicationContext(), "Level:" + theme.getThemeName(), Toast.LENGTH_SHORT).show();
                setGameLevel();
                theme.setThemeButton(themeButton);
            }
            if (requestCode == 2) {
                if (data.getBooleanExtra("refreshGame",false) == true){
                    startGame();
                }
                if (data.getBooleanExtra("gameWon", false)) {
                    if(currentGameLevel.hasNext() && allModesWon()){
                        openNextLevelIfLocked();
                    }
                    gameTime = data.getIntExtra("time", 0);
                    setNewWinningTimeAccordingToMode();
                }
            }
        }
        setButtonsBackground();
        setBestTimeTextViews();
    }

    private boolean allModesWon() {
        if (currentGameLevel.getBestTimeProMode() < Integer.MAX_VALUE && currentGameLevel.getBestIntermediateMode() < Integer.MAX_VALUE && currentGameLevel.getBestTimeEasyMode() < Integer.MAX_VALUE){
            return true;
        }

        return false;
    }

    private void openNextLevelIfLocked() {
        if (currentGameLevel.getNextLevel().getLockedStatus() == true){
            currentGameLevel.getNextLevel().setLockedStatus(false);
            Toast.makeText(getApplicationContext(),currentGameLevel.getNextLevel().getThemeName()+" unlocked!",Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonsBackground() {
        if (currentGameLevel.getBestTimeEasyMode() < Integer.MAX_VALUE) {
            beginnerButton.setBackgroundResource(R.drawable.won);
        } else {
            beginnerButton.setBackgroundResource(R.drawable.unwon);
        }
        if (currentGameLevel.getBestIntermediateMode() < Integer.MAX_VALUE){
            intermediateButton.setBackgroundResource(R.drawable.won);
        } else {
            intermediateButton.setBackgroundResource(R.drawable.unwon);
        }

        if (currentGameLevel.getBestTimeProMode() < Integer.MAX_VALUE){
            proButton.setBackgroundResource(R.drawable.won);
        } else {
            proButton.setBackgroundResource(R.drawable.unwon);
        }
    }

    private void setNewWinningTimeAccordingToMode() {
        if (gameMode == "easy") {
            if (currentGameLevel.getBestTimeEasyMode() > gameTime) {
                currentGameLevel.setBestTimeEasyMode(gameTime);
            }
        } else if (gameMode == "intermediate"){
            if (currentGameLevel.getBestIntermediateMode() > gameTime){
                currentGameLevel.setBestIntermediateMode(gameTime);
            }
        } else if (gameMode == "pro"){
            if (currentGameLevel.getBestTimeProMode() > gameTime){
                currentGameLevel.setBestTimeProMode(gameTime);
            }
        }
    }


    private void setNumberOfMinesInSpinner() {
        numOfMines.clear();

        for (int i=0; i< boardSize*boardSize-1; i++)
            numOfMines.add(Integer.toString(i+1));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,numOfMines);

        numberOfMinesSpinner.setAdapter(adapter);
    }

    private void startGame() {
        Intent game = new Intent(MainActivity.this, SecondActivity.class);
        game.putExtra("boardSize",boardSize);
        game.putExtra("numberOfMines",numberOfMines);
        game.putExtra("theme",theme.getThemeName());
        game.putExtra("sound",soundOn);
        startActivityForResult(game,2);
    }


}
