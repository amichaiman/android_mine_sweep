package com.example.amichai.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView timerAnimationImageView;
    private TextView numberOfSecondsTextView;
    private boolean refreshGame;
    private Button flagButton;

    private int time;

    private MediaPlayer startGameMediaPlayer;
    private MediaPlayer winGameMediaPlayer;

    private Button smileButton;

    private Animations animations;

    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle bundle = getIntent().getExtras();
        refreshGame = false;
        boardSize = bundle.getInt("boardSize");
        numberOfMines = bundle.getInt("numberOfMines");

        animations = new Animations();

        timerAnimationImageView = (ImageView) findViewById(R.id.timerAnimationImageView);
        numberOfSecondsTextView = (TextView) findViewById(R.id.numberOfSecondsTextView);
        numberOfMinesLeftTextView = (TextView) findViewById(R.id.numberOfMinesLeftTextView);
        flagButton = (Button) findViewById(R.id.flagButton);

        setSoundByTheme();


        if (settingsActivity.soundOn && !GameTheme.currentGameLevel.getThemeName().contentEquals("classic")) {
            startGameMediaPlayer.start();
        }

        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines));
        numberOfSecondsTextView.setText(Integer.toString(time));


        time = 0;
        t = new Thread(){
            @Override
            public void run() {
                try{
                    while (!isInterrupted() && !board.gameIsOver()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                numberOfSecondsTextView.setText(Integer.toString(time));
                                animations.time(timerAnimationImageView);
                                time+=1;
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e){

                }
            }
        };


        board = new Board(boardSize, numberOfMines);
        buttons = new Button[boardSize][boardSize];

        smileButton = (Button) findViewById(R.id.smileButton);
        GameTheme.theme.smileyGameImage(smileButton);

        smileButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    refreshGame = true;
                    onBackPressed();
                    return true;
                }
                GameTheme.theme.smileyGameImage(smileButton);
                return false;
            }
        });

        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (board.getFlagModeStatus()){
                    board.setFlagModeStatus(false);
                    flagButton.setBackgroundResource(R.drawable.flag);
                } else {
                    board.setFlagModeStatus(true);
                    flagButton.setBackgroundResource(R.drawable.flag_pressed);
                }
            }
        });

        t.start();
        createBoard();
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
                        GameTheme.theme.smileyGameImage(smileButton);
                        board.buttonClicked(view.getId() / boardSize, view.getId() % boardSize);
                        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines - board.getSpotsFlagged()));

                        if (board.gameIsOver()) {
                            board.unActivateButtons();

                            if (board.gameWon()) {
                                if (settingsActivity.soundOn && !GameTheme.currentGameLevel.getThemeName().contentEquals("classic")){
                                    winGameMediaPlayer.start();
                                }

                                GameTheme.theme.smileyWon(smileButton);
                            } else {
                                GameTheme.theme.smileyLost(smileButton);
                                board.revealMines();
                            }
                        }
                    }


                });
                button.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            GameTheme.theme.smileyPressedImage(smileButton);
                            if (board.buttonIsVisible(view.getId()/boardSize,view.getId()%boardSize)) {
                                return false;
                            }
                            return false;
                        }
                        return false;
                    }
                });
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        board.reCloseSpots(view.getId()/boardSize,view.getId()%boardSize);
                        GameTheme.theme.smileyGameImage(smileButton);
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


    private void setSoundByTheme() {
        switch (GameTheme.currentGameLevel.getThemeName()) {
            case "vitas":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_vitas);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_vitas);
                break;
            case "trump":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_trump);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_trump);
                break;
            case "quagmire":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.game_start_quagmire);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_quagmire); break;
            case "borat":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.start_game_borat);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_borat); break;
            case "obama":
                startGameMediaPlayer = MediaPlayer.create(this, R.raw.start_game_obama);
                winGameMediaPlayer = MediaPlayer.create(this, R.raw.win_game_obama); break;
        }
    }

    @Override
    public void onBackPressed() {
        backToMain();
    }

    private void backToMain() {
        Intent backToMain = new Intent(SecondActivity.this, MainActivity.class);

        if (settingsActivity.soundOn && !GameTheme.currentGameLevel.getThemeName().contentEquals("classic")) {
            winGameMediaPlayer.stop();
        }

        backToMain.putExtra("gameLost", board.gameIsLost());
        if (board.gameWon()){
            backToMain.putExtra("gameWon", true);
            backToMain.putExtra("time", time-1);
            backToMain.putExtra("refreshGame",refreshGame);
            setResult(RESULT_OK, backToMain);
        }
        backToMain.putExtra("refreshGame",refreshGame);
        setResult(RESULT_OK, backToMain);
        finish();
        super.onBackPressed();
    }
}
