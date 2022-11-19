package com.example.me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.http.Url;

public class NewsDetailActivity extends AppCompatActivity {
    String title,desc,content,imageURl,url;
    private TextView titleTV,subDescTV,contentTV;
    private ImageView newsIv;
    private Button yesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURl = getIntent().getStringExtra("image");
        url= getIntent().getStringExtra("url");
        titleTV =findViewById(R.id.idTVTitle);
        subDescTV =findViewById(R.id.idTVSubDesc);
        contentTV =findViewById(R.id.idTVContent);
        newsIv =findViewById(R.id.IdIVNews);
        yesButton = findViewById(R.id.YesButton);
        titleTV.setText(title);
        subDescTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageURl).into(newsIv);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}