package com.example.newsapp;

//adapters/NewsAdapter
package com.example.newsapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.models.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<com.example.newsapp.adapters.NewsAdapter.ViewHolder> {
    private List<News> newsList;
    private com.example.newsapp.adapters.NewsAdapter.OnNewsClickListener listener;

    public interface OnNewsClickListener {
        void onNewsClick(News news);
    }

    public NewsAdapter(List<News> newsList, com.example.newsapp.adapters.NewsAdapter.OnNewsClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.news_title);
            image = view.findViewById(R.id.news_image);
        }
    }

    @Override
    public com.example.newsapp.adapters.NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new com.example.newsapp.adapters.NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.newsapp.adapters.NewsAdapter.ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        Glide.with(holder.image.getContext()).load(news.getImageUrl()).into(holder.image);
        holder.itemView.setOnClickListener(v -> listener.onNewsClick(news));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
//adapters/RelatedNewsAdapter
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

public class RelatedNewsAdapter extends RecyclerView.Adapter<com.example.newsapp.adapters.RelatedNewsAdapter.ViewHolder> {
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
    public com.example.newsapp.adapters.RelatedNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_related_news, parent, false);
        return new com.example.newsapp.adapters.RelatedNewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.newsapp.adapters.RelatedNewsAdapter.ViewHolder holder, int position) {
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

//fragments/NewsDetailFragment
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

    public static com.example.newsapp.fragments.NewsDetailFragment newInstance(News news) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, news.getTitle());
        args.putString(ARG_DESC, news.getDescription());
        args.putString(ARG_IMG, news.getImageUrl());

        com.example.newsapp.fragments.NewsDetailFragment fragment = new com.example.newsapp.fragments.NewsDetailFragment();
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
        relatedRecycler.setAdapter(new com.example.newsapp.adapters.RelatedNewsAdapter(related));

        return view;
    }
}
//fragments/NewsFragment
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

public class NewsFragment extends Fragment implements com.example.newsapp.adapters.NewsAdapter.OnNewsClickListener {

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
        topStoriesRecycler.setAdapter(new com.example.newsapp.adapters.NewsAdapter(topStories, this));

        // News RecyclerView
        RecyclerView newsRecycler = view.findViewById(R.id.recycler_news);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecycler.setAdapter(new com.example.newsapp.adapters.NewsAdapter(newsList, this));

        return view;
    }

    @Override
    public void onNewsClick(News news) {
        com.example.newsapp.fragments.NewsDetailFragment detailFragment = com.example.newsapp.fragments.NewsDetailFragment.newInstance(news);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}

//models/News
package com.example.newsapp.models;

public class News {
    private String title;
    private String description;
    private String imageUrl;

    public News(String title, String desc, String imageUrl) {
        this.title = title;
        this.description = desc;
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}

//MainActivity
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
                .replace(R.id.fragment_container, new com.example.newsapp.fragments.NewsFragment())
                .commit();
    }
}

package com.example.itubeapp;

//DBHelper
package com.example.itubeapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "iTube.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, fullname TEXT, username TEXT UNIQUE, password TEXT)");
        db.execSQL("CREATE TABLE playlist(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, url TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS playlist");
        onCreate(db);
    }

    public boolean registerUser(String fullname, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname", fullname);
        cv.put("username", username);
        cv.put("password", password);
        long result = db.insert("users", null, cv);
        return result != -1;
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        return c.getCount() > 0;
    }

    public void addToPlaylist(String username, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("url", url);
        db.insert("playlist", null, cv);
    }

    public List<String> getPlaylist(String username) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT url FROM playlist WHERE username=?", new String[]{username});
        while (c.moveToNext()) {
            list.add(c.getString(0));
        }
        return list;
    }
}


//HomeActivity
package com.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    EditText urlInput;
    String username;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new DBHelper(this);

        urlInput = findViewById(R.id.url_input);
        username = getIntent().getStringExtra("username");

        findViewById(R.id.btn_play).setOnClickListener(v -> {
            Intent i = new Intent(this, PlayerActivity.class);
            i.putExtra("url", urlInput.getText().toString());
            startActivity(i);
        });

        findViewById(R.id.btn_add).setOnClickListener(v -> {
            db.addToPlaylist(username, urlInput.getText().toString());
            Toast.makeText(this, "Added to playlist", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_playlist).setOnClickListener(v -> {
            Intent i = new Intent(this, PlaylistActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });
    }
}

//MainActivity
package com.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            if (db.loginUser(user, pass)) {
                Intent i = new Intent(this, HomeActivity.class);
                i.putExtra("username", user);
                startActivity(i);
            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_signup).setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
    }
}

//PlayerActivity
package com.example.itubeapp;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        webView = findViewById(R.id.webView);
        String url = getIntent().getStringExtra("url");
        String videoId = Uri.parse(url).getQueryParameter("v");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe>", "text/html", "utf-8");
    }
}

//PlaylistActivity
package com.example.itubeapp;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    LinearLayout container;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        db = new DBHelper(this);

        container = findViewById(R.id.container);
        String username = getIntent().getStringExtra("username");

        List<String> urls = db.getPlaylist(username);
        for (String url : urls) {
            TextView tv = new TextView(this);
            tv.setText(url);
            tv.setPadding(10, 10, 10, 10);
            container.addView(tv);
        }
    }
}

//SignupActivity
package com.example.itubeapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText fullname, username, password, confirm;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = new DBHelper(this);

        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirmpassword);

        findViewById(R.id.btn_create).setOnClickListener(v -> {
            if (password.getText().toString().equals(confirm.getText().toString())) {
                if (db.registerUser(fullname.getText().toString(), username.getText().toString(), password.getText().toString())) {
                    Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Username exists", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
