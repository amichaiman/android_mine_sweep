package com.example.amichai.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    Board board;
    Button[][] buttons;
    int boardSize;
    int numberOfMines;
    TableLayout boardTableLayout;
    TextView numberOfMinesLeftTextView;
    TextView pictureOfFlagTextView;

    Button smileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle bundle = getIntent().getExtras();


        boardSize = bundle.getInt("boardSize");
        numberOfMines = bundle.getInt("numberOfMines");

        numberOfMinesLeftTextView = (TextView) findViewById(R.id.numberOfMinesLeftTextView);
        pictureOfFlagTextView = (TextView) findViewById(R.id.pictureOfFlagTextView);

        pictureOfFlagTextView.setBackgroundResource(R.drawable.flag);
        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines));

        board = new Board(boardSize, numberOfMines);
        buttons = new Button[boardSize][boardSize];

        smileButton = (Button) findViewById(R.id.smileButton);
        smileButton.setBackgroundResource(R.drawable.game);


        smileButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (board.gameIsOver()) {
                        refreshGame();
                    }
                    smileButton.setBackgroundResource(R.drawable.pressed);
                    return true;
                }
                smileButton.setBackgroundResource(R.drawable.game);
                return false;
            }
        });

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
                        board.buttonClicked(view.getId() / boardSize, view.getId() % boardSize);
                        numberOfMinesLeftTextView.setText(Integer.toString(numberOfMines - board.getSpotsFlagged()));

                        if (board.gameIsOver()) {
                            board.unActivateButtons();
                            if (board.gameWon()) {
                                smileButton.setBackgroundResource(R.drawable.win);
                            } else {
                                smileButton.setBackgroundResource(R.drawable.lose);
                                board.revealMines();
                            }
                        }
                    }


                });
                button.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            smileButton.setBackgroundResource(R.drawable.pressed);
                            if (board.buttonIsVisible(view.getId()/boardSize,view.getId()%boardSize)) {
                                return false;
                            }
                            return false;
                        }
                        smileButton.setBackgroundResource(R.drawable.game);
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

}
