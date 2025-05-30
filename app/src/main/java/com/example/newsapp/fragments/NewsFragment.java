package com.example.newsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsapp.R;
import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.models.News;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements NewsAdapter.OnNewsClickListener {

    private ArrayList<News> topStories = new ArrayList<>();
    private ArrayList<News> newsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // initiate sample data
        topStories.add(new News("Top Story 1", "Desc 1", "https://media.istockphoto.com/photos/news-picture-id588595538?k=6&m=588595538&s=170667a&w=0&h=kgBJNnIa-5kDt4rwtkTTP9UYWT_QHeWdqZX0Nob9Rzw="));
        topStories.add(new News("Top Story 2", "Desc 2", "https://media.istockphoto.com/photos/news-picture-id588595538?k=6&m=588595538&s=170667a&w=0&h=kgBJNnIa-5kDt4rwtkTTP9UYWT_QHeWdqZX0Nob9Rzw="));
        topStories.add(new News("Top Story 3", "Desc 3", "https://media.istockphoto.com/photos/news-picture-id588595538?k=6&m=588595538&s=170667a&w=0&h=kgBJNnIa-5kDt4rwtkTTP9UYWT_QHeWdqZX0Nob9Rzw="));

        newsList.add(new News("9NEWS", "Description 1", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));
        newsList.add(new News("7NEWS", "Description 2", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));
        newsList.add(new News("ABC NEWS", "Description 3", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));
        newsList.add(new News("THE AGE", "Description 4", "https://writology.com/wp-content/uploads/2021/07/newsletter.jpg"));

        // Top Stories RecyclerView
        RecyclerView topStoriesRecycler = view.findViewById(R.id.recycler_top_stories);
        topStoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topStoriesRecycler.setAdapter(new NewsAdapter(topStories, this));

        // News RecyclerView
        RecyclerView newsRecycler = view.findViewById(R.id.recycler_news);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
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
