package com.example.absolute.codepasta.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Absolute on 12/6/2017.
 */

public class RepositoryData implements Parcelable{
    private String name;
    private String html_url, description, created_at, updated_at,
    language;
    private boolean has_issues, has_downloads;
    private int watchers;
    private double score;

    public RepositoryData() {
    }

    public RepositoryData(String name, String html_url,
                                   String description, String created_at,
                                   String updated_at, String language,
                                   boolean has_issues, boolean has_downloads,
                                   int watchers, double score) {
        this.name = name;
        this.html_url = html_url;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.language = language;
        this.has_issues = has_issues;
        this.has_downloads = has_downloads;
        this.watchers = watchers;
        this.score = score;
    }

    protected RepositoryData(Parcel in) {
        name = in.readString();
        html_url = in.readString();
        description = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        language = in.readString();
        has_issues = in.readByte() != 0;
        has_downloads = in.readByte() != 0;
        watchers = in.readInt();
        score = in.readDouble();
    }

    public static final Creator<RepositoryData> CREATOR = new Creator<RepositoryData>() {
        @Override
        public RepositoryData createFromParcel(Parcel in) {
            return new RepositoryData(in);
        }

        @Override
        public RepositoryData[] newArray(int size) {
            return new RepositoryData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isHas_issues() {
        return has_issues;
    }

    public void setHas_issues(boolean has_issues) {
        this.has_issues = has_issues;
    }

    public boolean isHas_downloads() {
        return has_downloads;
    }

    public void setHas_downloads(boolean has_downloads) {
        this.has_downloads = has_downloads;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(html_url);
        parcel.writeString(description);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeString(language);
        parcel.writeByte((byte) (has_issues ? 1 : 0));
        parcel.writeByte((byte) (has_downloads ? 1 : 0));
        parcel.writeInt(watchers);
        parcel.writeDouble(score);
    }
}
