package com.example.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.models.News;

import java.util.List;

public class TopStoryAdapter extends RecyclerView.Adapter<TopStoryAdapter.ViewHolder> {

    private List<News> topStories;
    private OnTopStoryClickListener listener;

    public interface OnTopStoryClickListener {
        void onTopStoryClick(News news);
    }

    public TopStoryAdapter(List<News> topStories, OnTopStoryClickListener listener) {
        this.topStories = topStories;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.top_story_image);
        }
    }

    @Override
    public TopStoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News story = topStories.get(position);

        Glide.with(holder.image.getContext())
                .load(story.getImageUrl())
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTopStoryClick(story);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topStories.size();
    }
}
