package com.vitaliimalone.retrofitroomexample.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.vitaliimalone.retrofitroomexample.adapter.PostAdapter;
import com.vitaliimalone.retrofitroomexample.R;
import com.vitaliimalone.retrofitroomexample.api.PostsApiClient;
import com.vitaliimalone.retrofitroomexample.api.ServiceGenerator;
import com.vitaliimalone.retrofitroomexample.database.AppDatabase;
import com.vitaliimalone.retrofitroomexample.database.Post;
import com.vitaliimalone.retrofitroomexample.utils.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    AppDatabase db;
    List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate: started");

        db = AppDatabase.getAppDatabase(this);

        if (NetworkUtils.isOnline(this)) {
            retrofitRequest();
            posts = fetchDataFromDatabase();
        } else {
            posts = fetchDataFromDatabase();
        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        PostAdapter adapter = new PostAdapter(posts, this);
        recyclerView.setAdapter(adapter);

    }

    public void retrofitRequest() {
        Log.d(TAG, "retrofitRequest: requesting retrofit data");

        PostsApiClient client = ServiceGenerator.createService(PostsApiClient.class);
        Call<List<Post>> call = client.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: positive response");

                    List<Post> posts = response.body();
                    db.postDao().insertAll(posts);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: negative response");

                Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Post> fetchDataFromDatabase() {
        Log.d(TAG, "fetchDataFromDatabase: fetching data from db");

        return db.postDao().getAllPosts();
    }

    @Override
    public void onItemClick(Post post, int position) {
        Toast.makeText(this, "You clicked on item #" + position, Toast.LENGTH_SHORT).show();
    }
}
