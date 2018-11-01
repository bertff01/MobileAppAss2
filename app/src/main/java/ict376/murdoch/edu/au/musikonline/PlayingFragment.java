package ict376.murdoch.edu.au.musikonline;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PlayingFragment extends Fragment {

    private MediaPlayer mediaplayer;
    private int mediaFileLengthInMilliseconds;
    private Button playPause;
    private Button previous;
    private Button next;
    private SeekBar seek;
    private ImageView imageView;
    private int current;
    final Random rnd = new Random();
    Handler mHandler = new Handler();
    int[] images = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6};
    List<Track> tracks;
private TextView description;
    String url;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mediaplayer = new MediaPlayer();
        View v = inflater.inflate(R.layout.playingfragment, container, false);
        url="http://206.189.149.36:8080/tracks.json";
       playPause=(Button) v.findViewById(R.id.playPauseButton);
       previous=(Button) v.findViewById(R.id.prevButton);
       seek=(SeekBar) v.findViewById(R.id.seekBar);
       next=(Button) v.findViewById(R.id.nextButton);
       tracks=new ArrayList<Track>();
description=v.findViewById(R.id.songDescField);

           //json = GetTracks.readJsonFromUrl("http://206.189.149.36:8080/tracks.json");
        Log.d("JSON","got json");

        setOnClickListeners();
        new JSONParse().execute();
        imageView = (ImageView) v.findViewById(R.id.imageView);
        // I have 3 images named img_0 to img_2, so...

        return v;

    }
    protected final static int getResourceID
            (final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }
    @Override
    public void onPause() {
    super.onPause();
        mediaplayer.pause();
    }


    private void initView(int index) {
        try {
            String musicurl;
            int resID=0;
            resID=getResources().getIdentifier(tracks.get(index).file.substring(2, tracks.get(index).file.length()-4), "raw", getActivity().getPackageName());
            if(LibraryFragment.network) {
                musicurl="http://206.189.149.36:8080/"+tracks.get(index).file;
            } else {
                musicurl="android.resource://"+ getActivity().getPackageName()+"/res/raw/" + tracks.get(index).file.substring(2);

            }
            if(mediaplayer.isPlaying())
                mediaplayer.stop();
            mediaplayer=new MediaPlayer();
            mediaplayer=MediaPlayer.create(getContext(),resID);
          //  mediaplayer.setDataSource(getContext(),Uri.parse(musicurl));
          //  mediaplayer.prepare();
           imageView.setImageResource(images[rnd.nextInt(images.length)]);
            Log.d("Music","Music Url");




            current=index;
            Log.d("playing",Boolean.toString(mediaplayer.isPlaying()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        description.setText(tracks.get(index).name + " - "+tracks.get(index).artist + " : " + tracks.get(index).description);

        Log.d("playing",Boolean.toString(mediaplayer.isPlaying()));
        mediaFileLengthInMilliseconds=mediaplayer.getDuration();
  playPause();


    }
    private void playPause() {
        if(mediaplayer!=null) {
            Log.d("playing", Boolean.toString(mediaplayer.isPlaying()));
            if (!mediaplayer.isPlaying()) {
                mediaplayer.start();

//Make sure you update Seekbar on UI thread
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if(mediaplayer != null){
                            int mCurrentPosition = mediaplayer.getCurrentPosition() / 1000*2;
                            seek.setProgress(mCurrentPosition);
                        }
                        mHandler.postDelayed(this, 1000);
                    }
                });
                // buttonPlayPause.setImageResource(R.drawable.button_pause);
                Log.d("playing", Boolean.toString(mediaplayer.isPlaying()));
            } else {
                mediaplayer.pause();
                // buttonPlayPause.setImageResource(R.drawable.button_play);
            }
        }
    }
    private void setOnClickListeners(){
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPause();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSong();
            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaplayer!= null && fromUser){
                    mediaplayer.seekTo(progress * 1000);
                }
            }
        });
    }
    private void nextSong() {
        current=(current+1)%(tracks.size()-1);
        initView(current);
    }
    private void previousSong() {
        if(current==0)
            current=tracks.size()-2;
        else
            current=current-1;
        initView(current);
    }
    private class JSONParse extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();





        }

        @Override
        protected JSONArray doInBackground(String... args) {

            JSONArray json = new JSONArray();
            // Getting JSON from URL
            if(LibraryFragment.network) {
            try {
          json = GetTracks.readJsonFromUrl(url);


            } catch (Exception e) {


        }}
        else {
                try {
                    json = GetTracks.readJsonFromString(getResources().getString(R.string.tracks));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return json;}
        @Override
        protected void onPostExecute(JSONArray json) {
            try {
              for(int i=0;i<json.length();i++) {
                  Track track= new Track();
                  track.file=json.getJSONObject(i).getString("track_filename");
                  track.name=json.getJSONObject(i).getString("track_name");
                  track.description=json.getJSONObject(i).getString("track_description");
                  if(json.getJSONObject(i).getInt("track_artist_id")==5) {
                     track.artist="Paul Gray";
                  } else  {
                      track.artist="Frederick Bertram";
                  }
                  Log.d("Music",track.file);
                  tracks.add(track);
              }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            initView(SingletonTracks.getInstance().getTrack());

        }
    }
}
