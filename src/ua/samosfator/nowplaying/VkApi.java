package ua.samosfator.nowplaying;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class VkApi {

    public static void executeMethod(String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        HttpClient httpclient = HttpClientBuilder.create().build();
        httpclient.execute(httpPost);
    }

    public static String getJSON(String url) throws IOException, URISyntaxException {
        String json;

        HttpGet httpget = new HttpGet(url);
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse response;

        response = httpclient.execute(httpget);

        if (response != null) {
            HttpEntity entity = response.getEntity();
            json = IOUtils.toString(entity.getContent());
        } else throw new NullPointerException();

        return json;
    }
}
