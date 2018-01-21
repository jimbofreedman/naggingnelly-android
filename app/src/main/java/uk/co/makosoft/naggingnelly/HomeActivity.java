package uk.co.makosoft.naggingnelly;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.co.makosoft.naggingnelly.api.Action;
import uk.co.makosoft.naggingnelly.api.EntityAPI;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "Home";

    private EntityAPI apiWrapper;

    private TextView mTextMessage;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private ProgressBar progressBar;
    private EditText editNewActionShortDescription;
    private RecyclerView actionList;
    private MyAdapter mAdapter;

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


        actionList.setHasFixedSize(true);
        actionList.setLayoutManager(new LinearLayoutManager(this));


        apiWrapper = new EntityAPI("gtd/actions");

        mAdapter = new MyAdapter(apiWrapper.getEntities());
        actionList.setAdapter(mAdapter);

        apiWrapper.list(progressBar, mAdapter);
    }


    public void sendMessage(View view) {
        String message = editNewActionShortDescription.getText().toString();
        apiWrapper.create(new Action(message), progressBar, mAdapter);
        editNewActionShortDescription.setText("");
    }
}
