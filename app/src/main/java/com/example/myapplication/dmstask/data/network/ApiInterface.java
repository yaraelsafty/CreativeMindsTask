package com.example.myapplication.dmstask.data.network;

import com.example.myapplication.dmstask.data.model.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Yara on 12-Jan-18.
 */

public interface ApiInterface {
    @GET("users/square/repos")
    Call<ArrayList<Response>>getJSON();
}
