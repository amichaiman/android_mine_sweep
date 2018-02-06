package com.example.amichai.myapplication;

import android.widget.Button;

import java.util.Random;

public class Board {
    private Spot[][] board;
    private int boardSize;
    private int numberOfMines;
    private Button[][] buttons;
    private int spotsRevealed;
    private int spotsFlagged;
    private boolean gameOver;
    private boolean gameLost;
    private boolean flagMode;
    private static int DEFAULT_BOARD_SIZE = 10;


    public static final int MIN_NUMBER_OF_MINES = 1;


    private static final int NUMBER_OF_NEIGHBORS = 8;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        setBoard();

    }

    public Board(int boardSize, int numberOfMines) {
        this.boardSize = boardSize;
        this.numberOfMines = numberOfMines;

        setBoard();
        plantMines();
        setNumberOfNeighborBombs();
        flagMode = false;
        gameOver = false;
        gameLost = false;
    }

    public Board() {
        boardSize = DEFAULT_BOARD_SIZE;
        setBoard();
    }

    public void setFlagModeStatus(boolean status){
        flagMode = status;
    }

    public boolean getFlagModeStatus(){
        return flagMode;
    }

    public boolean gameIsLost(){
        return gameLost;
    }

    private void setBoard() {
        spotsRevealed = 0;
        spotsFlagged = 0;

        gameOver = false;
        board = new Spot[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Spot();
            }
        }

    }


    public void setButtons(Button[][] buttons) {
        this.buttons = buttons;
        lockButtons();
    }

    public void setNumberOfMines(int numberOfMines) {
        if (numberOfMines < MIN_NUMBER_OF_MINES) {
            this.numberOfMines = MIN_NUMBER_OF_MINES;
        } else if (numberOfMines > boardSize * boardSize) {
            this.numberOfMines = boardSize * boardSize;
        } else {
            this.numberOfMines = numberOfMines;
        }
        plantMines();
        setNumberOfNeighborBombs();
    }

    private void plantMines() {
        Random r = new Random();
        int i, j;

        for (int k = 0; k < numberOfMines; k++) {
            do {
                i = Math.abs(r.nextInt()) % boardSize;
                j = Math.abs(r.nextInt()) % boardSize;
            } while (board[i][j].getBombStatus() == true);
            board[i][j].setBombStatus(true);
        }
    }

    private void setNumberOfNeighborBombs() {
        int[] offsetI = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] offsetJ = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].getBombStatus() == false) {
                    for (int k = 0; k < NUMBER_OF_NEIGHBORS; k++) {
                        if (isInBoard(i + offsetI[k], j + offsetJ[k])) {
                            if (board[i + offsetI[k]][j + offsetJ[k]].getBombStatus() == true) {
                                board[i][j].setNumberOfNeighborBombs(board[i][j].getNumberOfNeighborBombs() + 1);
                            }
                        }
                    }
                }
            }
        }


    }

    private boolean isInBoard(int i, int j) {
        return i < 0 || j < 0 || i > boardSize - 1 || j > boardSize - 1 ? false : true;
    }

    public void buttonClicked(int i, int j) {
        if (flagMode){
            flagModeButtonClicked(i,j);
            return;
        }

        while (spotsRevealed == 0 && board[i][j].getBombStatus() == true) {
            replantMines();
        }

        if (board[i][j].getVisibilityStatus() == true) {
            openSpotClicked(i, j);
            return;
        }

        if (board[i][j].getFlaggedStatus() == true || board[i][j].getQuestionMarkedStatus() == true) {
            flagClicked(i, j);
            return;
        }

        if (board[i][j].getBombStatus() == true) {
            board[i][j].setVisibility(true);
            buttons[i][j].setBackgroundResource(R.drawable.bomkclicked);
            gameOver = true;
            gameLost = true;
            return;
        }

        revealRec(i, j);
    }


    public int getSpotsRevealed() {
        return spotsRevealed;
    }


    private void revealRec(int i, int j) {

        spotsRevealed++;
        showSpotImage(i, j);
        board[i][j].setVisibility(true);

        if (spotsRevealed == boardSize * boardSize - numberOfMines) {
            gameOver = true;
        }


        if (board[i][j].getNumberOfNeighborBombs() != 0) {
            return;
        }

        if (board[i][j].getBombStatus() == true) {
            buttons[i][j].setBackgroundResource(R.drawable.bomkclicked);
            gameOver = true;
            gameLost = true;
            return;
        }

        if (board[i][j].getFlaggedStatus() == true){
            spotsFlagged--;
        }
        int[] offsetI = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] offsetJ = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int k = 0; k < NUMBER_OF_NEIGHBORS; k++) {
            if (isInBoard(i + offsetI[k], j + offsetJ[k])) {
                if (board[i + offsetI[k]][j + offsetJ[k]].getVisibilityStatus() == false) {
                    revealRec(i + offsetI[k], j + offsetJ[k]);
                }
            }
        }
    }

    void showSpotImage(int i, int j) {
        if (board[i][j].getNumberOfNeighborBombs() == 0) {
            buttons[i][j].setBackgroundResource(R.drawable.zero);
        } else if (board[i][j].getNumberOfNeighborBombs() == 1) {
            buttons[i][j].setBackgroundResource(R.drawable.one);
        } else if (board[i][j].getNumberOfNeighborBombs() == 2) {
            buttons[i][j].setBackgroundResource(R.drawable.two);
        } else if (board[i][j].getNumberOfNeighborBombs() == 3) {
            buttons[i][j].setBackgroundResource(R.drawable.three);
        } else if (board[i][j].getNumberOfNeighborBombs() == 4) {
            buttons[i][j].setBackgroundResource(R.drawable.four);
        } else if (board[i][j].getNumberOfNeighborBombs() == 5) {
            buttons[i][j].setBackgroundResource(R.drawable.five);
        } else if (board[i][j].getNumberOfNeighborBombs() == 6) {
            buttons[i][j].setBackgroundResource(R.drawable.six);
        } else if (board[i][j].getNumberOfNeighborBombs() == 7) {
            buttons[i][j].setBackgroundResource(R.drawable.seven);
        } else {
            buttons[i][j].setBackgroundResource(R.drawable.eight);
        }
    }

    private void lockButtons() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                buttons[row][col].setMinWidth(buttons[row][col].getWidth());
                buttons[row][col].setMaxWidth(buttons[row][col].getWidth());
                buttons[row][col].setMinHeight(buttons[row][col].getHeight());
                buttons[row][col].setMaxHeight(buttons[row][col].getHeight());
            }
        }

    }

    public void flagModeButtonClicked(int i, int j){
        if (board[i][j].getVisibilityStatus()){
            return;
        }
        if (board[i][j].getFlaggedStatus()){
            board[i][j].setFlagged(false);
            spotsFlagged--;
            buttons[i][j].setBackgroundResource(R.drawable.square);
        } else if (numberOfMines != spotsFlagged){
            board[i][j].setFlagged(true);
            spotsFlagged++;
            buttons[i][j].setBackgroundResource(R.drawable.flag);
        }
    }
    public void flagClicked(int i, int j) {
        if (board[i][j].getFlaggedStatus() == false && board[i][j].getQuestionMarkedStatus() == false) {
            if (numberOfMines-spotsFlagged != 0) {
                board[i][j].setFlagged(true);
                buttons[i][j].setBackgroundResource(R.drawable.flag);
                spotsFlagged++;
            }
            return;
        }

        if (board[i][j].getFlaggedStatus() == true) {
            board[i][j].setFlagged(false);
            board[i][j].setQuestionMarked(true);
            buttons[i][j].setBackgroundResource(R.drawable.questionmark);
            spotsFlagged--;
            return;
        }

        board[i][j].setFlagged(false);
        board[i][j].setQuestionMarked(false);
        buttons[i][j].setBackgroundResource(R.drawable.square);


    }

    public boolean gameIsOver() {
        return gameOver;
    }

    public boolean gameWon() {
        return (boardSize * boardSize - numberOfMines == spotsRevealed);
    }

    private void replantMines() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j].setBombStatus(false);
                board[i][j].setNumberOfNeighborBombs(0);
            }
        }
        plantMines();
        setNumberOfNeighborBombs();
    }

    public void buttonLongClicked(int i, int j) {
        if (board[i][j].getVisibilityStatus() == true) {
            return;
        }
        if (flagMode){
            flagModeButtonClicked(i,j);
            return;
        }
        flagClicked(i, j);
    }

    private void openSpotClicked(int i, int j) {
        int[] offsetI = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] offsetJ = {-1, 0, 1, -1, 1, -1, 0, 1};

        int numberOfNeighborsFlagged = 0;

        for (int k = 0; k < NUMBER_OF_NEIGHBORS; k++) {
            if (isInBoard(i + offsetI[k], j + offsetJ[k])) {
                if (board[i + offsetI[k]][j + offsetJ[k]].getFlaggedStatus() == true) {
                    numberOfNeighborsFlagged++;
                }
            }
        }

        if (board[i][j].getNumberOfNeighborBombs() == numberOfNeighborsFlagged) {
            for (int k = 0; k < NUMBER_OF_NEIGHBORS; k++) {
                if (isInBoard(i + offsetI[k], j + offsetJ[k])) {
                    if (board[i + offsetI[k]][j + offsetJ[k]].getVisibilityStatus() == false) {
                        if (board[i + offsetI[k]][j + offsetJ[k]].getFlaggedStatus() == false) {
                            revealRec(i + offsetI[k], j + offsetJ[k]);
                        }
                    }
                }
            }
        }
    }

    public void revealMines() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].getBombStatus() == true) {
                    if (board[i][j].getVisibilityStatus() == false)
                        buttons[i][j].setBackgroundResource(R.drawable.mine);
                }
            }
        }
    }

    public void unActivateButtons()
    {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    public void setSpotsFlagged(int num) {
        spotsFlagged = num;
    }

    public int getSpotsFlagged() {
        return spotsFlagged;
    }

    public boolean buttonIsVisible(int i, int j){
        int[] offsetI = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] offsetJ = {-1, 0, 1, -1, 1, -1, 0, 1};


        if (board[i][j].getVisibilityStatus() == true){
            for (int k = 0; k < NUMBER_OF_NEIGHBORS; k++) {
                if (isInBoard(i + offsetI[k], j + offsetJ[k])) {
                    if (board[i + offsetI[k]][j + offsetJ[k]].getFlaggedStatus() == false) {
                        if (board[i + offsetI[k]][j + offsetJ[k]].getVisibilityStatus() == false) {
                            if (board[i + offsetI[k]][j + offsetJ[k]].getQuestionMarkedStatus() == false) {
                                buttons[i + offsetI[k]][j + offsetJ[k]].setBackgroundResource(R.drawable.zero);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void reCloseSpots(int i, int j){
        int[] offsetI = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] offsetJ = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int k = 0; k < NUMBER_OF_NEIGHBORS; k++) {
            if (isInBoard(i + offsetI[k], j + offsetJ[k])) {
                if (board[i + offsetI[k]][j + offsetJ[k]].getFlaggedStatus() == false) {
                    if (board[i + offsetI[k]][j + offsetJ[k]].getVisibilityStatus() == false) {
                        if (board[i + offsetI[k]][j + offsetJ[k]].getQuestionMarkedStatus() == false) {
                            buttons[i + offsetI[k]][j + offsetJ[k]].setBackgroundResource(R.drawable.square);
                        }
                    }
                }
            }
        }
    }
}