package ict376.murdoch.edu.au.mobileappass2attempt2;

import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Playing fragment that plays the actual songs
 *
 */
public class PlayingFragment extends Fragment implements SensorEventListener {

    private MediaPlayer mediaplayer;
    private int mediaFileLengthInMilliseconds;
    private Button playPause;
    private Button previous;
    private Button next;
    private SeekBar seek;
    private ImageView imageView;
    private int current;
    private Sensor accelerometer;
    private SensorManager sensorManager;
    private float lastX, lastY, lastZ;
    private float deltaX = 0, deltaY = 0, deltaZ = 0;
    boolean isFirstTime = true;
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

    /**
     * creates the stuff for the fragment like the media player aswell as getting the json data
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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
        initSensors(container);
        return v;

    }

    /**
     * this isn't currently used i don't believe
     * @param resName
     * @param resType
     * @param ctx
     * @return
     */
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

    /**
     * this pauses the player when the backbutton is pressed.
     */
    @Override
    public void onPause() {
    super.onPause();
        mediaplayer.pause();
    }

    /**
     * added by chris to shuffle song if shaken
     */
    private void shuffleSong() {
        Random randomNum = new Random();
        //generate random current value
        int current = (1 + randomNum.nextInt(tracks.size())) - 1;//-1 to compensate for array indexing
        initView(current);
    }

    //credit to this link:
    //https://examples.javacodegeeks.com/android/core/hardware/sensor/android-accelerometer-example/
    private void initSensors(ViewGroup container)
    {
        sensorManager = (SensorManager) container.getContext().getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

            Log.d("Sensor", "Sensor set.");

        } else {

            // fai! we dont have an accelerometer!

            Log.d("Sensor", "Sensor not set.");
            Toast.makeText(getActivity().getApplicationContext(), "Shuffle feature not available.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * not used
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //left empty deliberately
    }

    //called whenever new sensor data is recieved, the if condtion
    //ensures something only happens if the device is actually moved.
    @Override
    public void onSensorChanged(SensorEvent event)
    {
//      //ensures that the event isn't triggered without the device moving
        if(isFirstTime)
        {
            lastX = event.values[0];
            lastY = event.values[1];
            lastZ = event.values[2];
        }
        isFirstTime = false;
        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        //if delta values are less than 2, not significant enough motion
        if(deltaX > 2 || deltaY > 2 || deltaZ > 2)
        {
            shuffleSong();
            Log.d("Shuffle", "Shuffled song");
        }

        lastX = event.values[0];
        lastY = event.values[1];
        lastZ = event.values[2];

    }

    /**
     * bad name but sticking with it, this initializes the music player object and plays the music.
     * @param index
     */
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

    /**
     * plays and pauses the music
     */
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

    /**
     * plumbijng code for listeners
     */
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

    /**
     * next song in the tracklist
     */
    private void nextSong() {
        current=(current+1)%(tracks.size()-1);
        initView(current);
    }

    /**
     * previous song in the tracklist
     */
    private void previousSong() {
        if(current==0)
            current=tracks.size()-2;
        else
            current=current-1;
        initView(current);
    }

    /**
     * used to get the json but it isn't necessary to make it async but it is anyway because we
     * were using network stores songs before but decided to just use local songs
     */
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
