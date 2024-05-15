package com.example.api_one;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvList;
    private static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/3/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TeamService service = retrofit.create(TeamService.class);
        Call<TeamResponce> call = service.getTeams();
        call.enqueue(new Callback<TeamResponce>() {
            @Override
            public void onResponse(Call<TeamResponce> call, Response<TeamResponce> response) {
                if (response.isSuccessful()) {
                    TeamResponce teamResponse = response.body();
                    if (teamResponse != null) {
                        List<Team> teams = teamResponse.getTeams();
                        TeamAdapter adapter = new TeamAdapter(teams);
                        rvList.setAdapter(adapter);
                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<TeamResponce> call, Throwable t) {
                // Handle network failures
            }
        });
    }
}
