package com.vitaliimalone.retrofitroomexample.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insertPost(Post post);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Post> posts);

    @Query("SELECT * FROM post")
    List<Post> getAllPosts();

    @Query("DELETE FROM post")
    void deleteAll();

}
