package com.example.vivek.tablyoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vivek.tablyoutdemo.model.BabyName;

public class DetailActivity extends AppCompatActivity {
    TextView name_full, meaning, origin, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name_full = findViewById(R.id.name_full);
        meaning = findViewById(R.id.meaning);
        origin = findViewById(R.id.origin);
        gender = findViewById(R.id.gender);

        Intent intent = getIntent();
        final BabyName babyName = intent.getParcelableExtra("BabyList");
        final String mName = babyName.getName();
        final String mMeaning = babyName.getMeaning();
        final String mGender = babyName.getGender();
        final String mOrigin=babyName.getOrigin();


        name_full.setText("NAME : "+mName);
        meaning.setText("MEANING :"+mMeaning);
        gender.setText("GENDER :"+mGender);
        origin.setText("ORIGIN :"+mOrigin);



    }
}
