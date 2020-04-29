package com.rosihandie.moviecatalogue_sub_5.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable {

    private int id;
    private String type;
    private String rating;
    private String title;
    private String description;
    private String release;
    private String photo;
    private String backdrop;

    public TvShow() {

    }

    //Setter


    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    //Getter


    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRelease() {
        return release;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public TvShow(JSONObject object) {
        try {

            int id = object.getInt("id");
            String vote_average = object.getString("vote_average");
            String title = object.getString("name");
            String overview = object.getString("overview");
            String release_date = object.getString("first_air_date");
            String poster_path = object.getString("poster_path");
            String backdrop_path = object.getString("backdrop_path");

            this.id = id;
            this.rating = vote_average;
            this.title = title;
            this.description = overview;
            this.release = release_date;
            this.photo = poster_path;
            this.backdrop = backdrop_path;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.type);
        dest.writeString(this.rating);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.release);
        dest.writeString(this.photo);
        dest.writeString(this.backdrop);
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.rating = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.release = in.readString();
        this.photo = in.readString();
        this.backdrop = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
