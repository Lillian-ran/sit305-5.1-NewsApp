package com.example.newsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsapp.R;
import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.adapters.TopStoryAdapter;
import com.example.newsapp.models.News;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements NewsAdapter.OnNewsClickListener {

    private ArrayList<News> topStories = new ArrayList<>();

    private int currentTopIndex = 0;
    private ArrayList<News> newsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // initiate sample data
        topStories.add(new News("Top News 1", "Top description 1", "https://media.istockphoto.com/photos/news-picture-id588595538?k=6&m=588595538&s=170667a&w=0&h=kgBJNnIa-5kDt4rwtkTTP9UYWT_QHeWdqZX0Nob9Rzw="));
        topStories.add(new News("Top News 2", "Top description 2", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));
        topStories.add(new News("Top News 3", "Top description 3", "https://cdn.pixabay.com/photo/2015/07/17/22/43/student-849825_960_720.jpg"));

        newsList.add(new News("9NEWS", "Description 1", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));
        newsList.add(new News("7NEWS", "Description 2", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));
        newsList.add(new News("ABC NEWS", "Description 3", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));
        newsList.add(new News("THE AGE", "Description 4", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));

        // Top Stories RecyclerView
        RecyclerView topStoriesRecycler = view.findViewById(R.id.recycler_top_stories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topStoriesRecycler.setLayoutManager(layoutManager);
        TopStoryAdapter topAdapter = new TopStoryAdapter(topStories, news -> {
            NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(news);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
        topStoriesRecycler.setAdapter(topAdapter);


        // left and right btn
        Button btnPrev = view.findViewById(R.id.btn_prev);
        Button btnNext = view.findViewById(R.id.btn_next);

        btnPrev.setOnClickListener(v -> {
            currentTopIndex = (currentTopIndex - 1 + topStories.size()) % topStories.size();
            topStoriesRecycler.smoothScrollToPosition(currentTopIndex);
        });

        btnNext.setOnClickListener(v -> {
            currentTopIndex = (currentTopIndex + 1) % topStories.size();
            topStoriesRecycler.smoothScrollToPosition(currentTopIndex);
        });


        // News RecyclerView
        RecyclerView newsRecycler = view.findViewById(R.id.recycler_news);
        newsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        newsRecycler.setAdapter(new NewsAdapter(newsList, this));

        return view;
    }

    @Override
    public void onNewsClick(News news) {
        NewsDetailFragment detailFragment = NewsDetailFragment.newInstance(news);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
