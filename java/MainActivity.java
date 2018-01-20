package com.example.amichai.myapplication;

import android.content.Intent;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Switch beginnerSwitch;
    private Switch intermediateSwitch;
    private Switch proSwitch;
    private Switch randomSwitch;
    private Switch customSwitch;

    private Spinner boardSizeSpinner;
    private Spinner numberOfMinesSpinner;

    private Button startGameButton;
    private Button settingsButton;

    private String theme;
    private boolean soundOn;
    private int randomBoardSize;
    ArrayList<String> numOfMines;
    private int selectedBoardSize;
    private int selectedNumberOfMines;

    private static final int EASY_BOARD_SIZE = 6;
    private static final int INTERMEDIATE_BOARD_SIZE = 12;
    private static final int PRO_BOARD_SIZE = 18;

    private static final int EASY_NUMBER_OF_MINES = 5;
    private static final int INTERMEDIATE_NUMBER_OF_MINES = 20;
    private static final int PRO_NUMBER_OF_MINES = 60;
    public static final String DEFAULT_THEME= "classic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedBoardSize = 3;
        selectedNumberOfMines = 3;
        soundOn = true;
        theme = DEFAULT_THEME;

        beginnerSwitch = (Switch) findViewById(R.id.beginnerSwitch);
        beginnerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    turnAllSwitchesOffBesidesChecked(beginnerSwitch);
                }
            }
        });

        intermediateSwitch = (Switch) findViewById(R.id.intermediateSwitch);
        intermediateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    turnAllSwitchesOffBesidesChecked(intermediateSwitch);
                }
            }
        });


        proSwitch = (Switch) findViewById(R.id.proSwitch);
        proSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    turnAllSwitchesOffBesidesChecked(proSwitch);
                }
            }
        });


        randomSwitch = (Switch) findViewById(R.id.randomSwitch);
        randomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    turnAllSwitchesOffBesidesChecked(randomSwitch);
                }
            }
        });

        numOfMines = new ArrayList<String>();

        boardSizeSpinner = (Spinner) findViewById(R.id.boardSizeSpinner);
        numberOfMinesSpinner = (Spinner) findViewById(R.id.numberOfMinesSpinner);

        customSwitch = (Switch) findViewById(R.id.customSwitch);
        customSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    boardSizeSpinner.setVisibility(View.VISIBLE);
                    numberOfMinesSpinner.setVisibility(View.VISIBLE);
                    turnAllSwitchesOffBesidesChecked(customSwitch);
                } else {
                    boardSizeSpinner.setVisibility(View.INVISIBLE);
                    numberOfMinesSpinner.setVisibility(View.INVISIBLE);
                }
            }
        });

        boardSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBoardSize = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                setNumberOfMinesInSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedBoardSize = 3;
                setNumberOfMinesInSpinner();
            }
        });




        numberOfMinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedNumberOfMines = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        startGameButton = (Button) findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noModeSelected()== true) {
                    Toast.makeText(getApplicationContext(),"Please enter game mode",Toast.LENGTH_SHORT).show();
                } else {
                    startGame(boardSize(), numberOfMines());
                }
            }
        });

        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setBackgroundResource(R.drawable.settings_icon);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, settingsActivity.class);
                i.putExtra("sound",soundOn);
                i.putExtra("theme",theme);
                startActivityForResult(i,1);

            }
        });

    }

    private boolean noModeSelected() {
        if (beginnerSwitch.isChecked() == true){
            return false;
        }

        if (intermediateSwitch.isChecked() == true){
            return false;
        }

        if (proSwitch.isChecked() == true){
            return false;
        }

        if (randomSwitch.isChecked() == true){
            return false;
        }
        if (customSwitch.isChecked() == true){
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        theme = data.getStringExtra("theme");
        soundOn = data.getBooleanExtra("sound",true);
        Toast.makeText(getApplicationContext(),"Theme set to "+theme,Toast.LENGTH_SHORT).show();

    }

    private void turnAllSwitchesOffBesidesChecked(Switch checkSwitch) {
        beginnerSwitch.setChecked(false);
        intermediateSwitch.setChecked(false);
        proSwitch.setChecked(false);
        randomSwitch.setChecked(false);
        customSwitch.setChecked(false);
        checkSwitch.setChecked(true);
    }

    private void setNumberOfMinesInSpinner() {
        numOfMines.clear();

        for (int i=0; i< selectedBoardSize*selectedBoardSize-1; i++)
            numOfMines.add(Integer.toString(i+1));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,numOfMines);

        numberOfMinesSpinner.setAdapter(adapter);

    }

    private void startGame(int boardSize, int numberOfMines) {
        Intent game = new Intent(MainActivity.this, SecondActivity.class);
        game.putExtra("boardSize",boardSize);
        game.putExtra("numberOfMines",numberOfMines);
        game.putExtra("theme",theme);
        game.putExtra("sound",soundOn);
        startActivity(game);
    }

    int boardSize(){
        if (beginnerSwitch.isChecked()){
            return EASY_BOARD_SIZE;
        }
        if (intermediateSwitch.isChecked()){
            return INTERMEDIATE_BOARD_SIZE;
        }
        if (proSwitch.isChecked()){
            return PRO_BOARD_SIZE;
        }
        if (randomSwitch.isChecked()){
            Random r = new Random();
            randomBoardSize = Math.abs(r.nextInt())%Board.MAX_BOARD_SIZE;
            return randomBoardSize;
        }
        return selectedBoardSize;
    }
    int numberOfMines(){
        if (beginnerSwitch.isChecked()){
            return EASY_NUMBER_OF_MINES;
        }
        if (intermediateSwitch.isChecked()){
            return INTERMEDIATE_NUMBER_OF_MINES;
        }
        if (proSwitch.isChecked()){
            return PRO_NUMBER_OF_MINES;
        }
        if (randomSwitch.isChecked()){
            Random r = new Random();
            return Math.abs(r.nextInt())%(randomBoardSize*randomBoardSize-1);
        }



        return selectedNumberOfMines;
    }
}
