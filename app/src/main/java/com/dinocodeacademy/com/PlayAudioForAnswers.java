package com.dinocodeacademy.com;

import android.content.Context;
import android.media.MediaPlayer;

public class PlayAudioForAnswers {


    private Context mContext;
    private MediaPlayer mediaPlayer;

    public PlayAudioForAnswers(Context mContext) {
        this.mContext = mContext;
    }


    public void setAudioforAnswer(int flag) {
        // play the correct audio based on passed flag from the Activity
            switch (flag) {
                case 1:
                    int correctAudio = R.raw.correct;
                    playMusic(correctAudio);
                    break;
                case 2:
                    int wrongAudio = R.raw.wrong;
                    playMusic(wrongAudio);
                    break;
                case 3:
                    int timerAudio = R.raw.timetick;
                    playMusic(timerAudio);
                    break;
            }
        }
    private void playMusic(int audiofile) { // play the audio file

        mediaPlayer = MediaPlayer.create(mContext,audiofile);
        mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
        mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.release());

    }
}
