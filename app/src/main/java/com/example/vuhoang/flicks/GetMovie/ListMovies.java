package com.example.vuhoang.flicks.GetMovie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListMovies implements Parcelable {
    @SerializedName("results")
    @Expose
    private List<Movie> results = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalMovies;
    @SerializedName("dates")
    @Expose
    private Dates dates;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    
    public List<Movie> getMovies() {
        return results;
    }

    public void setMovies(List<Movie> results) {
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(Integer totalMovies) {
        this.totalMovies = totalMovies;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.results);
        dest.writeValue(this.page);
        dest.writeValue(this.totalMovies);
        dest.writeParcelable(this.dates, flags);
        dest.writeValue(this.totalPages);
    }

    public ListMovies() {
    }

    protected ListMovies(Parcel in) {
        this.results = new ArrayList<Movie>();
        in.readList(this.results, Movie.class.getClassLoader());
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalMovies = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dates = in.readParcelable(Dates.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ListMovies> CREATOR = new Parcelable.Creator<ListMovies>() {
        @Override
        public ListMovies createFromParcel(Parcel source) {
            return new ListMovies(source);
        }

        @Override
        public ListMovies[] newArray(int size) {
            return new ListMovies[size];
        }
    };
}
