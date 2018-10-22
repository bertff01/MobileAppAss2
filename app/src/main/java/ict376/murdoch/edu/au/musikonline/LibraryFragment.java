package ict376.murdoch.edu.au.musikonline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment{
    List<String> tracks;
    String url=  "http://206.189.149.36:8080/tracks.json";
    ListView list;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.libraryfragment, container, false);
        tracks=new ArrayList<String>();
        new JSONParse().execute();

        list=(ListView) v.findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generate2d method stub

SingletonTracks.getInstance().setTrack(position);
                    PlayingFragment fragment = new PlayingFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragement_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();


            }
        });

        return v;

    }

    public void initView() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getView().getContext(),android.R.layout.simple_list_item_1,tracks);
        list.setAdapter(arrayAdapter);
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
            try {
                json = GetTracks.readJsonFromUrl(url);


            } catch (Exception e) {


            }
            return json;}
        @Override
        protected void onPostExecute(JSONArray json) {
            try {
                for(int i=0;i<json.length();i++) {

                    String name=json.getJSONObject(i).getString("track_name");

                    if(json.getJSONObject(i).getInt("track_artist_id")==5) {
                        name+=" : Paul Gray";
                    } else  {
                        name+=" : Frederick Bertram";
                    }

                    tracks.add(name);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            initView();

        }
    }
}
