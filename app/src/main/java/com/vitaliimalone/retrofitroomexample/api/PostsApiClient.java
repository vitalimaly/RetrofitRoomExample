package com.vitaliimalone.retrofitroomexample.api;

import com.vitaliimalone.retrofitroomexample.database.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostsApiClient {

    @GET("posts")
    Call<List<Post>> getPosts();
}
