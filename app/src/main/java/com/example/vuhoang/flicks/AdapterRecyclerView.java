package com.example.vuhoang.flicks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vuhoang.flicks.GetMovie.Movie;

import java.util.List;

import butterknife.BindView;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {
    private List<Movie> listMovies;
    private Context ctx;

    public void setData(List<Movie> list){
        this.listMovies = list;
        notifyDataSetChanged();
    }
    public AdapterRecyclerView(Context context){
        this.ctx = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = listMovies.get(i);
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overView.setText(movie.getOverview());
        Glide.with(ctx).load(movie.getPosterPath()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.imgV)
        public ImageView imageView;
        @BindView(R.id.ti_tle)
        public TextView title;
        @BindView(R.id.overView)
        public TextView overView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
