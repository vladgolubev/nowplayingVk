package ua.samosfator.nowplaying;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class VKAudio {

    private static String track;

    public static String setStatus(String trackName) throws IOException, URISyntaxException, ParseException {
        track = trackName;
        String aid = getAudioID(VkApi.getJSON(URL.getSearchURL(track)));

        if (aid != null) {
            VkApi.executeMethod(URL.getAudionStatusURL(aid));
        } else {
            VkApi.executeMethod(URL.getStatusURL(track));
        }

        return "";
    }

    private static String getAudioID(String json) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        JSONArray jsonArray = (JSONArray) jsonObject.get("response");

        for (int i = 1; i < jsonArray.size(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            if (isFound(jo)) {
                return jo.get("owner_id") + "_" + jo.get("aid");
            }
        }

        return null;
    }

    private static boolean isFound(JSONObject jo) {
        String foundName = jo.get("artist") + " - " + jo.get("title");
        return foundName.equalsIgnoreCase(track);
    }
}
