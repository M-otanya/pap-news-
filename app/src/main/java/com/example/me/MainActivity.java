package com.example.me;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {
    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private  ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private  CategoryRVAdapter categoryRVAdapter;
    private  NewsRVAdapter newsRVAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        newsRV =findViewById(R.id.idRVNews);
        categoryRV =findViewById(R.id.idRVCategories);
        loadingPB =findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList =new ArrayList<>();
        newsRVAdapter =new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter=new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }
    private  void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://media.istockphoto.com/photos/creativity-picture-id1289513797"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://media.istockphoto.com/photos/in-technology-research-facility-chief-engineer-stands-in-the-middle-picture-id1214111410?s=612x612"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://media.istockphoto.com/photos/asian-scientist-or-doctors-use-microscopes-to-test-for-the-or-a-new-picture-id1221032250?k=20&m=1221032250&s=612x612&w=0&h=lHkWet92VyJWJ5lubeKszMWuxnBpvezWzK7aQEEInjs="));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://media.istockphoto.com/photos/various-sport-equipments-on-grass-picture-id949190756?k=20&m=949190756&s=612x612&w=0&h=9loU1wPMKi9eK89EDxBfz4AarpAl8BFo1iO5XGHpXX0="));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://media.istockphoto.com/vectors/garbag-icon-vector-id844113606?k=20&m=844113606&s=612x612&w=0&h=gMHXgjeJP8vySOGl94yAE5H-AWZ__Ns-Izg8QchNU2c="));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://media.istockphoto.com/photos/young-bearded-businessman-sitting-on-desk-and-posing-picture-id1322913815?k=20&m=1322913815&s=612x612&w=0&h=vDJxUO2lrV1YtG2VoM9IGcZnPJNJ4cvGzAOhaY76A2Y="));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://media.istockphoto.com/photos/young-african-man-wearing-virtual-reality-goggles-with-hands-up-on-picture-id1153003888?k=20&m=1153003888&s=612x612&w=0&h=k27Fpb-F7vIgNC-UN1XpcUhVRdERdVlRBnXN3toVAew="));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://media.istockphoto.com/photos/healthy-eating-exercising-weight-and-blood-pressure-control-picture-id1280587810?k=20&m=1280587810&s=612x612&w=0&h=HbmfdgfBL6s0dayZF8oiweVqC_5qvgVpcGWZtH8_DDY="));
        categoryRVAdapter.notifyDataSetChanged();
    }
    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL ="https://newsapi.org/v2/top-headlines?country=us&category="+category+"&apikey=8931611ef53240efaea401e1f237a35c";
        String url ="https://newsapi.org/v2/top-headlines?country=us&excludeDomain=stackoverflow.com&sortBy=publishedAt&language=en&apikey=8931611ef53240efaea401e1f237a35c";
        String BASE_URL ="https://newsapi.org/";
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if (category.equals("All")){
            call =retrofitAPI.getAllNews(url);
        }else{
            call=retrofitAPI.getNewsByCategory(categoryURL);
        }
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal= response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles=newsModal.getArticles();
                for (int i=0;i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed,check internet connection", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }
}