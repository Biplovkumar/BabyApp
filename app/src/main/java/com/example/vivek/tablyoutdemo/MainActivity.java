package com.example.vivek.tablyoutdemo;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vivek.tablyoutdemo.adapter.BabyAdapter;
import com.example.vivek.tablyoutdemo.database.AppDatabase;
import com.example.vivek.tablyoutdemo.model.BabyName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    VerticalRecyclerViewFastScroller recyclerViewFastScroller;
    AppDatabase database;
    private BabyAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = AppDatabase.getDatabase(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview);
        recyclerViewFastScroller=findViewById(R.id.fast_scroller);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BabyAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerViewFastScroller.setRecyclerView(recyclerView);
        recyclerView.setOnScrollListener(recyclerViewFastScroller.getOnScrollListener());

        progressBar = findViewById(R.id.progressBar);

        performDataInsertion();
        observeBabyList();
    }

    private void observeBabyList() {
        progressBar.setVisibility(View.VISIBLE);
        database.babyDao().getAllData().observe(this, new Observer<List<BabyName>>() {
            @Override
            public void onChanged(@Nullable List<BabyName> babyNames) {
                adapter.setBabyNames(babyNames);
                adapter.notifyDataSetChanged();
                if (!babyNames.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void performDataInsertion() {
        Completable observable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                List<BabyName> oldList = database.babyDao().getAllDataDirect();
                if (oldList.isEmpty()) {
                    List<BabyName> list = readData();
                    database.babyDao().insertAll(list);
                }
                e.onComplete();
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    private List<BabyName> readData() {
        List<BabyName> babyNames = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.babynames);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";

        try {
            reader.readLine();
            String mGender, mMeaning, mName, mOrigin;
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity", "Line: " + line);

                String[] tokens = line.split(",");
                mGender = tokens[1];
                mMeaning = tokens[2];
                mName = tokens[3];
                mOrigin = tokens[4];

                BabyName babyName = new BabyName(mGender, mMeaning, mName, mOrigin);
                babyNames.add(babyName);
                Log.d(TAG, "Just created: " + mGender + mMeaning + mName + mOrigin);
            }

            database.babyDao().insertAll(babyNames);
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }

        return babyNames;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.sort_boy:


                break;

            case R.id.sort_girl:


                break;

        }

        return super.onOptionsItemSelected(item);

    }
}


