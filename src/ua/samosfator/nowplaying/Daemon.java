package ua.samosfator.nowplaying;

import javafx.application.Platform;
import org.json.simple.parser.ParseException;
import ua.samosfator.nowplaying.gui.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogManager;

public class Daemon {

    private static Timer updateStatus = new Timer();

    public static void start() {
        Lastfm lastfm = new Lastfm(Config.LASTFM_USER);
        LogManager.getLogManager().reset();

        updateStatus.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Song npSong = new Song(lastfm.getNPSong());

                    Platform.runLater(() -> {
                        if (Controller.songList.size() > 0
                                && Controller.songList.get(0).getTitle().equals(npSong.getTitle())) {
                            Controller.songList.set(0, npSong);
                        } else if (!npSong.getTitle().equals("")) Controller.songList.add(0, npSong);
                    });

                    VKAudio.setStatus(npSong.getTitle());
                } catch (IOException | URISyntaxException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, 0, Config.REFRESH_RATE);
    }

    public static void stop() {
        updateStatus.cancel();
        updateStatus.purge();
        updateStatus = new Timer();
    }
}
