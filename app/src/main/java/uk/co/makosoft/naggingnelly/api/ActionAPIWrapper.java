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

public class ActionAPIWrapper {
    protected String TAG = "ActionAPI";
    ActionAPI api;

    public ActionAPIWrapper(Retrofit retrofit) {
        api = retrofit.create(ActionAPI.class);
    }

    List<Action> entities = new ArrayList<>();
    public List<Action> getEntities() {
        return entities;
    }

    public void list(final ProgressBar pb, final MyAdapter adapter) {
        Log.d(TAG, String.format("Retrieving entities"));

        pb.setVisibility(View.VISIBLE);

        Call<List<Action>> call = api.list();
        call.enqueue(new Callback<List<Action>>() {
            @Override
            public void onResponse(Call<List<Action>> call, Response<List<Action>> response) {
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
            public void onFailure(Call<List<Action>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void create(Action entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Action> call = api.create(entity);

        Log.d(TAG, String.format("Adding entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Action>() {
            @Override
            public void onResponse(Call<Action> call, Response<Action> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Action returnedEntity = response.body();
                    entities.add(returnedEntity);
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, String.format("Added entity: %s %s", returnedEntity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Action> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void detail(Action entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Action> call = api.detail(entity.getId());

        Log.d(TAG, String.format("Adding entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Action>() {
            @Override
            public void onResponse(Call<Action> call, Response<Action> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Action returnedEntity = response.body();
                    entities.add(returnedEntity);
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, String.format("Added entity: %s %s", returnedEntity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Action> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void put(final Action entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Action> call = api.update(entity.getId(), entity);

        Log.d(TAG, String.format("Putting entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Action>() {
            @Override
            public void onResponse(Call<Action> call, Response<Action> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Action returnedEntity = response.body();
                    entities.remove(entity);
                    entities.add(returnedEntity);
                    Log.i(TAG, String.format("Put entity: %s %s", returnedEntity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Action> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void patch(final Action entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Action> call = api.update(entity.getId(), entity);

        Log.d(TAG, String.format("Patching entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Action>() {
            @Override
            public void onResponse(Call<Action> call, Response<Action> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Action returnedEntity = response.body();
                    entities.remove(entity);
                    entities.add(returnedEntity);
                    Log.i(TAG, String.format("Patched entity: %s %s", returnedEntity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Action> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void delete(final Action entity, final ProgressBar pb, final MyAdapter adapter) {
        Call<Action> call = api.delete(entity.getId());

        Log.d(TAG, String.format("Deleting entity: %s", entity.toString()));

        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Action>() {
            @Override
            public void onResponse(Call<Action> call, Response<Action> response) {
                pb.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    entities.remove(entity);
                    Log.i(TAG, String.format("Deleted entity: %s %s", entity.toString(), call.request().body()));
                } else {
                    Log.e(TAG, String.format("%s %s %s %s", response.code(), response.errorBody(), response.message(), response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Action> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                Log.e(TAG, t.getMessage());
            }
        });
    }
}