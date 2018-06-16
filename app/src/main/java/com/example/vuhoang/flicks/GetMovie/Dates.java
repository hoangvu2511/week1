package com.example.vuhoang.flicks.GetMovie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates implements Parcelable {
    @SerializedName("maximum")
    @Expose
    private String maximum;
    @SerializedName("minimum")
    @Expose
    private String minimum;

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maximum);
        dest.writeString(this.minimum);
    }

    public Dates() {
    }

    protected Dates(Parcel in) {
        this.maximum = in.readString();
        this.minimum = in.readString();
    }

    public static final Parcelable.Creator<Dates> CREATOR = new Parcelable.Creator<Dates>() {
        @Override
        public Dates createFromParcel(Parcel source) {
            return new Dates(source);
        }

        @Override
        public Dates[] newArray(int size) {
            return new Dates[size];
        }
    };
}
