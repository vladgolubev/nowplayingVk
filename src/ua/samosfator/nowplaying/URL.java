package ua.samosfator.nowplaying;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class URL {

    public static String getSearchURL(String trackName) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https").setHost("api.vk.com").setPath("/method/audio.search")
                .addParameter("access_token", Config.VK_ACCESS_TOKEN)
                .addParameter("auto_complete", "1")
                .addParameter("search_own", "1")
                .addParameter("q", trackName)
                .addParameter("count", "3")
                .addParameter("sort", "2");
        return uriBuilder.build().toString();
    }

    public static String getAudionStatusURL(String aid) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https").setHost("api.vk.com").setPath("/method/audio.setBroadcast")
                .addParameter("access_token", Config.VK_ACCESS_TOKEN)
                .addParameter("audio", aid)
                .addParameter("target_ids", Config.VK_USER_ID);
        return uriBuilder.build().toString();
    }

    public static String getStatusURL(String trackName) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder().setScheme("https").setHost("api.vk.com").setPath("/method/status.set")
                .addParameter("access_token", Config.VK_ACCESS_TOKEN)
                .addParameter("text", trackName.length() > 3 ? "#np " + trackName : "");
        return uriBuilder.build().toString();
    }
}
