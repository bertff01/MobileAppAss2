package ict376.murdoch.edu.au.mobileappass2attempt2;

public class SingletonTracks {

    private static SingletonTracks st;
    private int currentTrack;
    private SingletonTracks() {
        
    }
    public static SingletonTracks getInstance() {
        if(st==null)
            st=new SingletonTracks();
        return st;
    }
    public int getTrack() {
        return currentTrack;
    }
    public void setTrack(int track) {
        currentTrack=track;
    }
}
