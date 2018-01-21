package uk.co.makosoft.naggingnelly.api;


import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
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
import uk.co.makosoft.naggingnelly.MyAdapter;

/**
 * Created by jimbo on 20/01/2018.
 */

public class ContextAPIWrapper {
    protected String TAG = "ContextAPI";
    ContextAPI api;

    public ContextAPIWrapper() {
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

        api = retrofit.create(ContextAPI.class);
    }

    List<Context> entities = new ArrayList<>();
    public List<Context> getEntities() {
        return entities;
    }

    public void list(final ProgressBar pb, final MyAdapter adapter) {
        Log.i(TAG, String.format("Retrieving entities"));

        pb.setVisibility(View.VISIBLE);

        Call<List<Context>> call = api.list();
        call.enqueue(new Callback<List<Context>>() {
            @Override
            public void onResponse(Call<List<Context>> call, Response<List<Context>> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    entities.clear();
                    entities.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, String.format("Retrieved %d entities", entities.size(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<List<Context>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void create(Context entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Context> call = api.create(entity);

        Log.i(TAG, String.format("Adding entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Context>() {
            @Override
            public void onResponse(Call<Context> call, Response<Context> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Context returnedEntity = response.body();
                    entities.add(returnedEntity);
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, String.format("Added entity: %s %s", returnedEntity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Context> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void detail(Context entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Context> call = api.detail(entity.getId());

        Log.i(TAG, String.format("Adding entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Context>() {
            @Override
            public void onResponse(Call<Context> call, Response<Context> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Context returnedEntity = response.body();
                    entities.add(returnedEntity);
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, String.format("Added entity: %s %s", returnedEntity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Context> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void put(final Context entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Context> call = api.update(entity.getId(), entity);

        Log.i(TAG, String.format("Putting entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Context>() {
            @Override
            public void onResponse(Call<Context> call, Response<Context> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Context returnedEntity = response.body();
                    entities.remove(entity);
                    entities.add(returnedEntity);
                    Log.i(TAG, String.format("Put entity: %s %s", returnedEntity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Context> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void patch(final Context entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Context> call = api.update(entity.getId(), entity);

        Log.i(TAG, String.format("Patching entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Context>() {
            @Override
            public void onResponse(Call<Context> call, Response<Context> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Context returnedEntity = response.body();
                    entities.remove(entity);
                    entities.add(returnedEntity);
                    Log.i(TAG, String.format("Patched entity: %s %s", returnedEntity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Context> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void delete(final Context entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Context> call = api.delete(entity.getId());

        Log.i(TAG, String.format("Deleting entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Context>() {
            @Override
            public void onResponse(Call<Context> call, Response<Context> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    entities.remove(entity);
                    Log.i(TAG, String.format("Deleted entity: %s %s", entity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Context> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
