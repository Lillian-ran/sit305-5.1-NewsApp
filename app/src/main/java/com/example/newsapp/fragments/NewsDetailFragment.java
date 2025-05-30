package com.example.newsapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.adapters.RelatedNewsAdapter;
import com.example.newsapp.models.News;

import java.util.ArrayList;

public class NewsDetailFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESC = "desc";
    private static final String ARG_IMG = "img";

    public static NewsDetailFragment newInstance(News news) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, news.getTitle());
        args.putString(ARG_DESC, news.getDescription());
        args.putString(ARG_IMG, news.getImageUrl());

        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString(ARG_TITLE);
            String desc = args.getString(ARG_DESC);
            String imageUrl = args.getString(ARG_IMG);

            ((TextView) view.findViewById(R.id.news_title)).setText(title);
            ((TextView) view.findViewById(R.id.news_desc)).setText(desc);
            Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)       // if failed
                    .into((ImageView) view.findViewById(R.id.news_image));
        }

        // sample related news
        ArrayList<News> related = new ArrayList<>();
        related.add(new News("Related 1", "Related desc 1", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));
        related.add(new News("Related 2", "Related desc 2", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));

        RecyclerView relatedRecycler = view.findViewById(R.id.recycler_related);
        relatedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        relatedRecycler.setAdapter(new RelatedNewsAdapter(related));

        return view;
    }
}

