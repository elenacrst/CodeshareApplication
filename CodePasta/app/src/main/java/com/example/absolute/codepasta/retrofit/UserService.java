package com.example.absolute.codepasta.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("users/{user}")
    Call<UserResponse> getUserResponse(@Path("user")
                                                 String user);

    @GET("search/repositories?q=android&sort=updated&order=desc\n" +
            "&per_page=20&page=1")
    Call<RepositoryResponse> getRepositories();

    @POST("gists")
    Call<MessageResponse> savePost(@Body PastaData pastaData);

    @GET("users/{username}/repos")
    Call<List<RepositoryData>> getMyRepositories(@Path("username")String username);
}
