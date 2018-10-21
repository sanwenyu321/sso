package com.airwallex.ssoclient.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.http.Consts.UTF_8;

public class HttpUtil {
    private static HttpClient client = HttpClients.createDefault();

    public static boolean post(String url, Map params) {
        HttpPost post = new HttpPost(url);

        if (params != null && !params.isEmpty()) {
            List<NameValuePair > list = new ArrayList<>();
            params.forEach(
                    (Object key, Object value) -> {
                        list.add(new BasicNameValuePair((String)key, (String) value));
                    }
            );

            post.setEntity(new UrlEncodedFormEntity(list, UTF_8));
        }
        try {
            client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
