package uk.co.makosoft.naggingnelly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Console;
import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import uk.co.makosoft.naggingnelly.api.API;
import uk.co.makosoft.naggingnelly.api.Action;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "Home";

    private API api;

    private TextView mTextMessage;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private ProgressBar progressBar;
    private EditText editNewActionShortDescription;
    private RecyclerView actionList;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        editNewActionShortDescription = (EditText)findViewById(R.id.editNewActionShortDescription);
        actionList = (RecyclerView)findViewById(R.id.actionList);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", "Token 1d9d51931c542249ce5430e3d81332e38260ec35").build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        api = retrofit.create(API.class);

        progressBar.setVisibility(View.VISIBLE);
        final ProgressBar pb = progressBar;

        Call<List<Action>> call = api.getActions();
        call.enqueue(new Callback<List<Action>>() {
            @Override
            public void onResponse(Call<List<Action>> call, Response<List<Action>> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    List<Action> actions = response.body();

                    for (Action action: actions) {
                        Log.i(TAG, String.format("Retrieved action: %s %s", action.getShortDescription(), call.request().body()));
                    }
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<List<Action>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


    public void sendMessage(View view) {
        String message = editNewActionShortDescription.getText().toString();
        Call<Action> call = api.postAction(new Action(message));

        Log.i(TAG, String.format("Adding action: %s", message));

        progressBar.setVisibility(View.VISIBLE);
        final ProgressBar pb = progressBar;

        final Intent intent = new Intent(this, EditActionActivity.class);

        call.enqueue(new Callback<Action>() {
            @Override
            public void onResponse(Call<Action> call, Response<Action> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Action action = response.body();
                    intent.putExtra(EXTRA_MESSAGE, action.getShortDescription());
                    startActivity(intent);
                    Log.i(TAG, String.format("Added action: %s %s", action.getShortDescription(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Action> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
