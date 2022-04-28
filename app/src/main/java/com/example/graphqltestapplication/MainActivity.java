package com.example.graphqltestapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executeGraphQLAPICall();

    }

    private void executeGraphQLAPICall() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl("https://rickandmortyapi.com/graphql")
                .okHttpClient(okHttpClient)
                .build();

        apolloClient.query(FeedResultsQuery.builder()
                .build()).enqueue(new ApolloCall.Callback<FeedResultsQuery.Data>() {
            @Override
            public void onResponse(@NonNull Response<FeedResultsQuery.Data> response) {
                Log.e("Called","Response from api is :" + response.getData().characters().results.get(0).name);
            }

            @Override
            public void onFailure(@NonNull ApolloException e) {
                Log.e("Called","API failure response :"+e.getMessage());
            }
        });
    }
}