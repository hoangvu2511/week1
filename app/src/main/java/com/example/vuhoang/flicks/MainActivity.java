package com.example.vuhoang.flicks;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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
    @BindView(R.id.pageNum)
    TextView pageNum;

    private RecyclerView.LayoutManager manager;

    private static ListMovies listMovies;
    private List<Movie> movies;
    private ComplexAdapterRecyclerView adapterRecyclerView;
    private int page = 1;
    private int totalPage = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(movies==null){
            movies = new ArrayList<>();
            getMovies();
            setList();
        }
        else
            adapterRecyclerView.setData(movies);

    }

    /**
     * call api and get list of now playing movies
     */
    private void getMovies() {
        RetrofitClient.getApi().getNowPlaying(InterfaceApi.Api_key,page, Locale.getDefault().getLanguage())
                .enqueue(new Callback<ListMovies>() {
                    @Override
                    public void onResponse(Call<ListMovies> call, Response<ListMovies> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            listMovies = response.body();
                            totalPage = listMovies.getTotalPages();
                            movies.addAll(listMovies.getMovies());
                            adapterRecyclerView.setData(movies);
                            setPage();
                        }
                    }
                    @Override
                    public void onFailure(Call<ListMovies> call, Throwable t) {

                    }
                });
        page++;
    }

    private void setList() {
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapterRecyclerView = new ComplexAdapterRecyclerView(MainActivity.this);
        if(movies == null)
            movies = new ArrayList<>();
        adapterRecyclerView.setData(movies);



        recyclerView.setAdapter(adapterRecyclerView);

        this.recyclerView.setAdapter(adapterRecyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getMovies();
            }
        });

    }

    private void setPage(){
        String pg ;
        if (totalPage != 0) {
            pg = "page: " + String.valueOf(page-1) + " / " + String.valueOf(totalPage) ;
            pageNum.setText(pg);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
