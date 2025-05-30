package com.example.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.models.News;

import java.util.List;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.ViewHolder> {
    private List<News> relatedList;

    public RelatedNewsAdapter(List<News> relatedList) {
        this.relatedList = relatedList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.related_news_title);
            image = view.findViewById(R.id.related_news_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_related_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = relatedList.get(position);
        holder.title.setText(news.getTitle());

        Glide.with(holder.image.getContext())
                .load(news.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return relatedList.size();
    }
}
