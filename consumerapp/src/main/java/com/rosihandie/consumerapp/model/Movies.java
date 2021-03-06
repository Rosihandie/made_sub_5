package com.rosihandie.consumerapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.rosihandie.consumerapp.database.DbContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.rosihandie.consumerapp.database.DbContract.getColumnInt;
import static com.rosihandie.consumerapp.database.DbContract.getColumnString;

public class Movies implements Parcelable {

    private int id;
    private String type;
    private String rating;
    private String title;
    private String description;
    private String release;
    private String photo;
    private String backdrop;

    public Movies() {

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


    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getBackdrop() {
        return backdrop;
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


    public Movies(JSONObject object) {
        try {

            int id = object.getInt("id");
            String title = object.getString("title");
            String poster_path = object.getString("poster_path");


            this.id = id;
            this.title = title;
            this.photo = poster_path;

    } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public int describeContents() {
        return 0;
    }

    public Movies(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.type = getColumnString(cursor, DbContract.MovieColumns.TYPE);
        this.title = getColumnString(cursor, DbContract.MovieColumns.TITLE);
        this.photo = getColumnString(cursor, DbContract.MovieColumns.URL_IMAGE);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.rating);
        dest.writeString(this.release);
        dest.writeString(this.photo);
        dest.writeString(this.backdrop);
    }

    protected Movies(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.rating = in.readString();
        this.release = in.readString();
        this.photo = in.readString();
        this.backdrop = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

}
