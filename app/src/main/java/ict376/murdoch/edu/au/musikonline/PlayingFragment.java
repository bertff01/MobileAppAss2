package ict376.murdoch.edu.au.musikonline;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayingFragment extends Fragment {

    private MediaPlayer mediaplayer;
    private int mediaFileLengthInMilliseconds;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.playingfragment, container, false);
        initView();
        return v;

    }
    private void initView() {
        try {
            mediaplayer = new MediaPlayer();
            mediaplayer.setDataSource("http://206.189.149.36:8080/54oppression.mp3");
            mediaplayer.prepare();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        mediaFileLengthInMilliseconds=mediaplayer.getDuration();
        if(!mediaplayer.isPlaying()){
            mediaplayer.start();
           // buttonPlayPause.setImageResource(R.drawable.button_pause);
        }else {
            mediaplayer.pause();
           // buttonPlayPause.setImageResource(R.drawable.button_play);
        }

    }
}
