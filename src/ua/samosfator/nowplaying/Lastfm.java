package ua.samosfator.nowplaying;

import de.umass.lastfm.Caller;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;

import java.util.ArrayList;

public class Lastfm {

    private String username;
    private Track lastTrack;

    public Lastfm(String username) {
        Caller.getInstance().setUserAgent(Config.USER_AGENT);
        this.username = username;
    }

    public String getNPSong() {
        lastTrack = getLastTrack();
        if (isNowListening()) return lastTrack.getArtist() + " - " + lastTrack.getName();
        else return "";
    }

    public boolean isNowListening() {
        return lastTrack.isNowPlaying();
    }

    private Track getLastTrack() {
        PaginatedResult<Track> recentTracks = User.getRecentTracks(username, 1, 1, Config.API_KEY);
        return ((ArrayList<Track>) recentTracks.getPageResults()).get(0);
    }
}