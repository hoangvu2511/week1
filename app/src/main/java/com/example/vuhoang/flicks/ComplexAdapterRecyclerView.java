package com.example.vuhoang.flicks;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vuhoang.flicks.Api.InterfaceApi;
import com.example.vuhoang.flicks.GetMovie.Movie;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ComplexAdapterRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Movie> listMovies;
    private Context ctx;
    private final int BACK_DROP = -1 , POSTER = 1;
    String urlImg = InterfaceApi.BASE_IMAGES_URL + "w200" ;

    public void setData(List<Movie> movies){
        this.listMovies = movies;
        notifyDataSetChanged();
    }

    public ComplexAdapterRecyclerView(Context context){
        this.ctx = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case POSTER:
                View v1 = inflater.inflate(R.layout.item,parent,false);
                holder = new ViewHolderPoster(v1);
                break;
            case BACK_DROP:
                View v2 = inflater.inflate(R.layout.item_img,parent,false);
                holder = new ViewHolderBackDrop(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.item,parent,false);
                holder = new ViewHolderPoster(v3);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case POSTER:
                ViewHolderPoster viewHolderPoster = (ViewHolderPoster) holder;
                configureViewHolderPoster(viewHolderPoster,position);
                break;
            case BACK_DROP:
                ViewHolderBackDrop viewHolderBackDrop = (ViewHolderBackDrop) holder;
                configureViewHolderBackDrop(viewHolderBackDrop,position);
                break;
        }
    }

    private void configureViewHolderBackDrop(ViewHolderBackDrop viewHolderBackDrop, int position) {
        Movie movie = listMovies.get(position);
        int witdh = Resources.getSystem().getDisplayMetrics().widthPixels;
        if (movie != null){
            Glide.with(ctx).load(urlImg+movie.getBackdropPath())
                    .apply(new RequestOptions().override(witdh-40,600))
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30,0)))
                    .into(viewHolderBackDrop.backDrop);
        }
    }

    private void configureViewHolderPoster(ViewHolderPoster viewHolderPoster, int position) {
        Movie movie = listMovies.get(position);
        if (movie != null){
            Glide.with(ctx).load(urlImg+movie.getPosterPath())
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30,0)))
                    .apply(new RequestOptions().override(200,200))
                    .into(viewHolderPoster.poster);
            viewHolderPoster.title.setText(movie.getTitle());
            viewHolderPoster.overView.setText(movie.getOverview());
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if(listMovies.get(position).getVoteAverage() > 5 )
                return BACK_DROP;
            return POSTER;
        }
        return BACK_DROP;

    }

    private class ViewHolderPoster extends RecyclerView.ViewHolder {
        public ImageView poster;
        public TextView title;
        public TextView overView;

        public ViewHolderPoster(View v1) {
            super(v1);
            poster = v1.findViewById(R.id.imgV);
            title = v1.findViewById(R.id.ti_tle);
            overView = v1.findViewById(R.id.overView);

        }
    }

    private class ViewHolderBackDrop extends RecyclerView.ViewHolder {
        public ImageView backDrop;
        public ViewHolderBackDrop(View v2) {
            super(v2);
            backDrop = v2.findViewById(R.id.imgBackDrop);

        }
    }
}
