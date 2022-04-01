package com.system.common.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class OkHttpClientsUtil {

    public static String sendPost(String url, Map<String, String> paras) {
        okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
        Iterator entries = paras.entrySet().iterator();

        while(entries.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)entries.next();
            builder.add((String)entry.getKey(), (String)entry.getValue());
        }

        RequestBody body = builder.build();
        Request request = (new Request.Builder()).url(url).post(body).build();
        Call call = (new OkHttpClient()).newCall(request);

        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException var8) {
            var8.printStackTrace();
            return null;
        }
    }
}
