package com.example.absolute.codepasta.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Absolute on 12/13/2017.
 */

public class PastaData {
    @SerializedName("files")
    @Expose
    Files files;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("public")
    @Expose
    boolean isPublic;

    public PastaData(Files files, String description, boolean isPublic) {
        this.files = files;
        this.description = description;
        this.isPublic = isPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
