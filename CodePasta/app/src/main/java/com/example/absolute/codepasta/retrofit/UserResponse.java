package com.example.absolute.codepasta.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse implements Parcelable{
    @SerializedName("login")
    @Expose
    private String login;//username
    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;
    @SerializedName("html_url")
    @Expose
    private String html_url;//link for profile
    //in a webview
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("hireable")
    @Expose
    private boolean hireable;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("public_repos")
    @Expose
    private int public_repos;
    @SerializedName("followers")
    @Expose
    private int followers;
    @SerializedName("following")
    @Expose
    private int following;
    @SerializedName("created_at")
    @Expose
    private String created_at;//register date
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    //date when profile has been updated
    @SerializedName("location")
    @Expose
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserResponse(Parcel in){
        login = in.readString();
        avatar_url = in.readString();
        html_url = in.readString();
        name= in.readString();
        company = in.readString();
        hireable = Boolean.valueOf(in.readString());
        bio = in.readString();
        public_repos = in.readInt();
        followers = in.readInt();
        following = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        location = in.readString();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isHireable() {
        return hireable;
    }

    public void setHireable(boolean hireable) {
        this.hireable = hireable;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(login);
        parcel.writeString(avatar_url);
        parcel.writeString(html_url);
        parcel.writeString(name);
        parcel.writeString(company);
        parcel.writeString(hireable+"");
        parcel.writeString(bio);
        parcel.writeInt(public_repos);
        parcel.writeInt(followers);
        parcel.writeInt(following);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeString(location);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserResponse createFromParcel(Parcel in) {
            return new UserResponse(in);
        }

        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }

    };
    public UserResponse(){
    }

    public UserResponse(UserResponse userResponse){
        this.avatar_url = userResponse.avatar_url;
        this.bio = userResponse.bio;
        this.company = userResponse.company;
        this.created_at = userResponse.created_at;
        this.followers = userResponse.followers;
        this.following = userResponse.following;
        this.hireable = userResponse.hireable;
        this.html_url = userResponse.html_url;
        this.location = userResponse.location;
        this.login = userResponse.login;
        this.name = userResponse.name;
        this.public_repos = userResponse.public_repos;
        this.updated_at= userResponse.updated_at;
    }

}
