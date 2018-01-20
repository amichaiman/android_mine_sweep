package com.example.amichai.myapplication;

import android.widget.Button;

public class Theme {
    private String themeName;

    public static final String DEFAULT_THEME = "classic";

    public Theme(String themeName){
        this.themeName = themeName;
    }

    public Theme() {
        themeName = new String(DEFAULT_THEME);
    }

    public String getThemeName(){
        return themeName;
    }
    public void smileyGameImage(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.game_vitas); break;
            case "classic":
                b.setBackgroundResource(R.drawable.game); break;
            case "trump":
                b.setBackgroundResource(R.drawable.game_trump); break;
        }
    }

    public void smileyPressedImage(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.pressed_vitas); break;
            case "classic":
                b.setBackgroundResource(R.drawable.pressed); break;
            case "trump":
                b.setBackgroundResource(R.drawable.pressed_trump); break;
        }
    }


    public void smileyWon(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.win_vitas); break;
            case "classic":
                b.setBackgroundResource(R.drawable.win); break;
            case "trump":
                b.setBackgroundResource(R.drawable.win_trump); break;
        }
    }


    public void smileyLost(Button b){
        switch (themeName) {
            case "vitas":
                b.setBackgroundResource(R.drawable.lose_vitas); break;
            case "classic":
                b.setBackgroundResource(R.drawable.lose); break;
            case "trump":
                b.setBackgroundResource(R.drawable.lose_trump); break;
        }
    }

}
