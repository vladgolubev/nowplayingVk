package ua.samosfator.nowplaying;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Song {

    private String date;
    private String title;

    public Song(String title) {
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
