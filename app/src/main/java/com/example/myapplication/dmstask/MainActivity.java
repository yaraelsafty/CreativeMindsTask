package com.example.myapplication.dmstask;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.dmstask.adpter.DataAdapter;
import com.example.myapplication.dmstask.data.model.Response;
import com.example.myapplication.dmstask.data.network.ApiInterface;
import com.example.myapplication.dmstask.data.network.MainApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;



public class MainActivity extends AppCompatActivity {
    private RecyclerView rc_repos;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Response>list=new ArrayList<>();
    DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rc_repos = (RecyclerView) findViewById(R.id.rc_repos);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.sw_refresh);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rc_repos.setLayoutManager(layoutManager);


        //load data
        getData();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //clear cache and load fresh data
                MainApi.deleteCache(MainActivity.this);
                getData();
                Toast.makeText(MainActivity.this,"Data refreshed",Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getData() {
        ApiInterface apiService = MainApi.getClient().create(ApiInterface.class);
        Call<ArrayList<Response>> call = apiService.getJSON();
      call.enqueue(new Callback<ArrayList<Response>>() {
          @Override
          public void onResponse(Call<ArrayList<Response>> call, retrofit2.Response<ArrayList<Response>> response) {
              swipeRefreshLayout.setRefreshing(false);
              Log.i("response","------success");
              list=response.body();
              Log.i("listSize ", String.valueOf(list.size()));
              adapter=new DataAdapter(MainActivity.this,list);
              rc_repos.setAdapter(adapter);
          }

          @Override
          public void onFailure(Call<ArrayList<Response>> call, Throwable t) {
              Log.e("Failure",""+t);

          }
      });

    }
}
