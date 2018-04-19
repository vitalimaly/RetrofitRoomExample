package com.vitaliimalone.retrofitroomexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitaliimalone.retrofitroomexample.database.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public static final String TAG = PostAdapter.class.getSimpleName();

    List<Post> posts;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Post post, int position);
    }

    public PostAdapter(List<Post> posts, OnItemClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(posts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_item_tv)
        TextView idItemTv;
        @BindView(R.id.title_item_tv)
        TextView titleItemTv;
        @BindView(R.id.body_item_tv)
        TextView bodyItemTv;

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Post post, final OnItemClickListener listener) {
            Log.d(TAG, "bind: dinding data");

            idItemTv.setText("#" + post.getId());
            titleItemTv.setText(post.getTitle());
            bodyItemTv.setText(post.getBody());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(post, getAdapterPosition());
                }
            });
        }
    }
}
