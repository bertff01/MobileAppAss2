package ict376.murdoch.edu.au.musikonline;

public class Song {

    private String song_id;

    public Song()
    {
        song_id = "";
    }

    public Song(String id)
    {
        song_id = id;

    }

    public void setSong_id(String id)
    {
        song_id = id;
    }

    public String getSong_id()
    {
        return song_id;
    }
}
