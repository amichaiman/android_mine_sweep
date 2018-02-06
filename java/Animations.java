package com.example.amichai.myapplication;

import android.widget.ImageView;

public class Animations {
    private int arrowFrame;
    private int timeFrame;
    private int settingsFrame;

    private static final int NUMBER_OF_FRAMES_ARROW_ANIMATION = 10;
    private static final int NUMBER_OF_FRAMES_TIME_ANIMATION = 12;

    public Animations(){
        arrowFrame = 1;
        timeFrame = 1;
        settingsFrame = 1;
    }


    public void time(ImageView timerAnimationImageView){
        if (timeFrame == NUMBER_OF_FRAMES_TIME_ANIMATION+1) {
            timeFrame = 1;
        }
        switch (timeFrame++) {
            case 1:
                timerAnimationImageView.setBackgroundResource(R.drawable.t1); break;
            case 2:
                timerAnimationImageView.setBackgroundResource(R.drawable.t2); break;
            case 3:
                timerAnimationImageView.setBackgroundResource(R.drawable.t3); break;
            case 4:
                timerAnimationImageView.setBackgroundResource(R.drawable.t4); break;
            case 5:
                timerAnimationImageView.setBackgroundResource(R.drawable.t5); break;
            case 6:
                timerAnimationImageView.setBackgroundResource(R.drawable.t6); break;
            case 7:
                timerAnimationImageView.setBackgroundResource(R.drawable.t7); break;
            case 8:
                timerAnimationImageView.setBackgroundResource(R.drawable.t8); break;
            case 9:
                timerAnimationImageView.setBackgroundResource(R.drawable.t9); break;
            case 10:
                timerAnimationImageView.setBackgroundResource(R.drawable.t10); break;
            case 11:
                timerAnimationImageView.setBackgroundResource(R.drawable.t11); break;
            case 12:
                timerAnimationImageView.setBackgroundResource(R.drawable.t12); break;

        }
    }

    public void arrow(ImageView arrowAnimationImageView){
        if (arrowFrame == NUMBER_OF_FRAMES_ARROW_ANIMATION+1){
            arrowFrame = 1;
        }
        switch (arrowFrame++) {
            case 1:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a1); break;
            case 2:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a2); break;
            case 3:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a3); break;
            case 4:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a4); break;
            case 5:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a5); break;
            case 6:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a6); break;
            case 7:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a7); break;
            case 8:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a8); break;
            case 9:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a9); break;
            case 10:
                arrowAnimationImageView.setBackgroundResource(R.drawable.a10); break;

            }
    }
}
