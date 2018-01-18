package com.example.amichai.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button eastButton;
    Button intermediateButton;
    Button proButton;
    Button crazyButton;

    Spinner boardSizeSpinner;
    Spinner numberOfMinesSpinner;
    Button startGameButton;


    ArrayList<String> numOfMines;
    int selectedBoardSize = 3;
    int selectedNumberOfMines = 3;

    private static final int EASY_BOARD_SIZE = 6;
    private static final int INTERMEDIATE_BOARD_SIZE = 12;
    private static final int PRO_BOARD_SIZE = 18;

    private static final int EASY_NUMBER_OF_MINES = 5;
    private static final int INTERMEDIATE_NUMBER_OF_MINES = 20;
    private static final int PRO_NUMBER_OF_MINES = 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eastButton = (Button) findViewById(R.id.easyButton);
        eastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(EASY_BOARD_SIZE,EASY_NUMBER_OF_MINES);
            }
        });

        intermediateButton = (Button) findViewById(R.id.intermidateButton);
        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(INTERMEDIATE_BOARD_SIZE,INTERMEDIATE_NUMBER_OF_MINES);
            }
        });

        proButton = (Button) findViewById(R.id.proButton);
        proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(PRO_BOARD_SIZE,PRO_NUMBER_OF_MINES);
            }
        });

        crazyButton = (Button) findViewById(R.id.crazyButton);
        crazyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                int boardSize = Math.abs(r.nextInt())%Board.MAX_BOARD_SIZE;
                startGame(boardSize,Math.abs(r.nextInt())%(boardSize*boardSize/2));
            }
        });

        boardSizeSpinner = (Spinner) findViewById(R.id.boardSizeSpinner);
        numberOfMinesSpinner = (Spinner) findViewById(R.id.numberOfMinesSpinner);
        numOfMines = new ArrayList<String>();


        boardSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBoardSize = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                setNumberOfMinesInSpinner();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedBoardSize = 0;
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
                startGame(selectedBoardSize,selectedNumberOfMines);
            }
        });

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

        startActivity(game);
    }


}
