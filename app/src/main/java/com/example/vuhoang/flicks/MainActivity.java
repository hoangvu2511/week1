package com.example.vuhoang.flicks;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vuhoang.flicks.Api.InterfaceApi;
import com.example.vuhoang.flicks.Api.RetrofitClient;
import com.example.vuhoang.flicks.GetMovie.ListMovies;
import com.example.vuhoang.flicks.GetMovie.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ListMovies listMovies;
    private List<Movie> movies;
    private AdapterRecyclerView adapterRecyclerView;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(movies==null){
            movies = new ArrayList<>();
            setList();
            getMovies();
        }
        else
            adapterRecyclerView.setData(movies);

    }

    private void getMovies() {
        RetrofitClient.getApi().getNowPlaying(InterfaceApi.Api_key,page, Locale.getDefault().getLanguage())
                .enqueue(new Callback<ListMovies>() {
                    @Override
                    public void onResponse(Call<ListMovies> call, Response<ListMovies> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            listMovies = response.body();
                            movies = listMovies.getMovies();
                            adapterRecyclerView.setData(movies);
                        }
                        long b =100;
                    }
                    @Override
                    public void onFailure(Call<ListMovies> call, Throwable t) {

                    }
                });
    }

    private void setList() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapterRecyclerView = new AdapterRecyclerView(MainActivity.this);
        if(movies == null)
            movies = new ArrayList<>();
        adapterRecyclerView.setData(movies);

        recyclerView.setAdapter(adapterRecyclerView);

        this.recyclerView.setAdapter(adapterRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovies();
            }
        });
    }

}
