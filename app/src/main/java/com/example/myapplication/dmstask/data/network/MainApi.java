package com.example.myapplication.dmstask.data.network;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yara on 12-Jan-18.
 */

public class MainApi {
    Context context;
    public static final String BASE_URL = "https://api.github.com";
    private static Retrofit retrofit = null;

    int cacheSize = 10 * 1024 * 1024; // 10 MB
    File httpCacheDirectory = new File(context.getCacheDir(), "responses");
    Cache cache = new Cache(httpCacheDirectory, cacheSize);


    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(cache)
            .build();

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
