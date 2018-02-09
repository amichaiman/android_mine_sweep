package com.example.amichai.myapplication;

import java.util.ArrayList;

public class GameTheme {
    public static final int NUMBER_OF_LEVELS = 7;

    public static Theme theme;

    public static final String DEFAULT_THEME= "classic";

    public static ArrayList<GameLevel> gameLevels;

    public static GameLevel classic;
    public static GameLevel trump;
    public static GameLevel vitas;
    public static GameLevel quagmire;
    public static GameLevel borat;
    public static GameLevel obama;
    public static GameLevel dalaiLama;
    public static GameLevel currentGameLevel;

    public GameTheme(){
        theme = new Theme();

        classic = new GameLevel("classic");
        trump = new GameLevel("trump");
        vitas = new GameLevel("vitas");
        quagmire = new GameLevel("quagmire");
        borat = new GameLevel("borat");
        obama = new GameLevel("obama");
        dalaiLama = new GameLevel("dalai lama");
        gameLevels = new ArrayList<GameLevel>();

        gameLevels.add(0,classic);
        gameLevels.add(1,trump);
        gameLevels.add(2,quagmire);
        gameLevels.add(3,vitas);
        gameLevels.add(4,borat);
        gameLevels.add(5,obama);
        gameLevels.add(6,dalaiLama);

        currentGameLevel = gameLevels.get(0);
        trump.setLockedStatus(false);
        setLevelBoardSizes();
        setLevelNumberOfBombs();
    }

    private void setLevelBoardSizes() {
        for (int i=1; i<NUMBER_OF_LEVELS; i++){
            gameLevels.get(i).setBoardSizeEasyMode(GameLevel.EASY_BOARD_SIZE+i);
            gameLevels.get(i).setBoardSizeIntermediateMode(GameLevel.INTERMEDIATE_BOARD_SIZE+i);
            gameLevels.get(i).setBoardSizeProMode(GameLevel.PRO_BOARD_SIZE+i);
        }
    }


    private void setLevelNumberOfBombs(){
        for (int i=1; i<NUMBER_OF_LEVELS; i++){
            gameLevels.get(i).setNumberOfBombsEasyMode(GameLevel.EASY_NUMBER_OF_MINES+(i*i)/2);
            gameLevels.get(i).setNumberOfBombsIntermediateMode(GameLevel.INTERMEDIATE_NUMBER_OF_MINES+i*i);
            gameLevels.get(i).setNumberOfBombsHardMode(GameLevel.PRO_NUMBER_OF_MINES+(i*i)+i*2 );
        }
    }
    public boolean allModesWon(GameLevel g) {
        if (g.getBestTimeProMode() == Integer.MAX_VALUE || g.getBestIntermediateMode() == Integer.MAX_VALUE || g.getBestTimeEasyMode() == Integer.MAX_VALUE){
            return false;
        }

        return true;
    }
}
