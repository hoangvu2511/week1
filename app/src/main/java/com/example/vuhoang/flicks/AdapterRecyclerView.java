package com.example.vuhoang.flicks;

import android.content.Context;
import android.content.res.Configuration;
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
        String urlImg = InterfaceApi.BASE_IMAGES_URL + "w200" ;
        int size = 1000;
        if(movie != null ){
            if(ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                if (movie.getVoteAverage() >5){
                    urlImg += movie.getBackdropPath();
                    Glide.with(ctx).load(urlImg)
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(90,0)))
                            .apply(new RequestOptions().override(size))
                            .into(viewHolder.imageView);
                    viewHolder.title.setVisibility(View.GONE);
                    viewHolder.overView.setVisibility(View.GONE);
                }
                else{
                    viewHolder.title.setVisibility(View.VISIBLE);
                    viewHolder.overView.setVisibility(View.VISIBLE);
                    urlImg +=  movie.getPosterPath();
                    Glide.with(ctx).load(urlImg)
                            .apply(new RequestOptions().override(size))
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(90,0)))
                            .into(viewHolder.imageView);
                    viewHolder.title.setText(movie.getOriginalTitle());
                    viewHolder.overView.setText(movie.getOverview());
                }
            }
            else{
                urlImg += movie.getBackdropPath();
                Glide.with(ctx).load(urlImg)
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(90,0)))
                        .apply(new RequestOptions().override(size))
                        .into(viewHolder.imageView);
                viewHolder.title.setText(movie.getOriginalTitle());
                viewHolder.overView.setText(movie.getOverview());
            }
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView title;
        public TextView overView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgV);
            title = itemView.findViewById(R.id.ti_tle);
            overView = itemView.findViewById(R.id.overView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface IClickListrner{
        void onClick();
    }

}
