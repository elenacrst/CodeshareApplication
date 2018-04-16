package com.example.absolute.codepasta.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "recent_repositories")
public class RecentRepositoriesTable {

    private String name;
    @PrimaryKey
    @NonNull
    private String html_url;
    private String description;
    private String created_at;
    private String updated_at;
    private String language;
    private boolean has_issues;
    private boolean has_downloads;
    private int watchers;
    private double score;

    public RecentRepositoriesTable(String name, @NonNull String html_url,
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(@NonNull String html_url) {
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
}
