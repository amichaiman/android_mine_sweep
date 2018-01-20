package com.example.amichai.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private Board board;
    private Button[][] buttons;
    private int boardSize;
    private int numberOfMines;
    private TableLayout boardTableLayout;
    private TextView numberOfMinesLeftTextView;
    private TextView pictureOfFlagTextView;
    private TextView pictureOfTimeTextView;
    private TextView numberOfSecondsTextView;
    private Thread t;
    private int time;

    private MediaPlayer startGameMediaPlayer;
    private MediaPlayer winGameMediaPlayer;

    private Button smileButton;
    private String themeName;
    private Theme gameTheme;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle bundle = getIntent().getExtras();

        boardSize = bundle.getInt("boardSize");
        numberOfMines = bundle.getInt("numberOfMines");
        themeName = bundle.getString("theme");
        gameTheme = new Theme(themeName);
        numberOfSecondsTextView = (TextView) findViewById(R.id.numberOfSecondsTextView);
        numberOfMinesLeftTextView = (TextView) findViewById(R.id.numberOfMinesLeftTextView);
        pictureOfFlagTextView = (TextView) findViewById(R.id.pictureOfFlagTextView);
        pictureOfTimeTextView = (TextView) findViewById(R.id.pictureOfTimeTextView);

        setSoundByTheme();
        startGameMediaPlayer.start();
        pictureOfTimeTextView.setBackgroundResource(R.drawable.time);
        pictureOfFlagTextView.setBackgroundResource(R.drawable.flag);
        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines));
        numberOfSecondsTextView.setText(Integer.toString(time));


        time = 0;
        t = new Thread(){
            @Override
            public void run(){
                try{
                    while (!board.gameIsOver()){
                        numberOfSecondsTextView.setText(Integer.toString(time));
                        Thread.sleep(1000);
                        time += 1;
                    }
                } catch (Exception e){

                }
            }
        };

        board = new Board(boardSize, numberOfMines);
        buttons = new Button[boardSize][boardSize];

        smileButton = (Button) findViewById(R.id.smileButton);
        gameTheme.smileyGameImage(smileButton);

        smileButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (board.gameIsOver()) {
                        refreshGame();
                    }
                    gameTheme.smileyPressedImage(smileButton);
                    return true;
                }
                gameTheme.smileyGameImage(smileButton);
                return false;
            }
        });

        createBoard();
        t.start();
        board.setButtons(buttons);

    }

    public void createBoard() {
        boardTableLayout = (TableLayout) findViewById(R.id.boardTableLayout);

        for (int row = 0; row < boardSize; row++) {
            TableRow curTableRow = new TableRow(this);
            curTableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            boardTableLayout.addView(curTableRow);
            for (int col = 0; col < boardSize; col++) {
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        board.reCloseSpots(view.getId()/boardSize,view.getId()%boardSize);
                        board.buttonClicked(view.getId() / boardSize, view.getId() % boardSize);
                        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines - board.getSpotsFlagged()));

                        if (board.gameIsOver()) {
                            board.unActivateButtons();

                            if (board.gameWon()) {
                                winGameMediaPlayer.start();
                                gameTheme.smileyWon(smileButton);
                            } else {
                                gameTheme.smileyLost(smileButton);
                                board.revealMines();
                            }
                        }
                    }


                });
                button.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            gameTheme.smileyPressedImage(smileButton);
                            if (board.buttonIsVisible(view.getId()/boardSize,view.getId()%boardSize)) {
                                return false;
                            }
                            return false;
                        }
                        gameTheme.smileyGameImage(smileButton);
                        return false;
                    }
                });
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        board.reCloseSpots(view.getId()/boardSize,view.getId()%boardSize);
                        board.buttonLongClicked(view.getId() / boardSize, view.getId() % boardSize);
                        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines - board.getSpotsFlagged()));
                        return true;
                    }
                });

                button.setId(row * boardSize + col);
                button.setBackgroundResource(R.drawable.square);
                curTableRow.addView(button);
                buttons[row][col] = button;

            }
        }
    }
    private void refreshGame(){
        Intent restartActivity = getIntent();
        finish();
        startActivity(restartActivity);
    }

    public void setThemeName(String themeName){
        this.themeName = themeName;
    }
    public String getThemeName(){
        return themeName;
    }

    private void setSoundByTheme() {
        switch (gameTheme.getThemeName()) {
            case "vitas":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_vitas);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_vitas);
                break;
            case "trump":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_trump);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_trump);
                break;
            default:
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_vitas);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_vitas);
        }
    }
}
