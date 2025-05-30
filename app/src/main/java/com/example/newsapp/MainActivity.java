package com.example.newsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.fragments.NewsFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new NewsFragment())
                .commit();
    }
}