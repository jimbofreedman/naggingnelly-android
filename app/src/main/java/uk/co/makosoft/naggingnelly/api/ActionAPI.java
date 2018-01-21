package uk.co.makosoft.naggingnelly.api;

import android.drm.DrmStore;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ActionAPI {
    @GET("gtd/actions/")
    Call<List<Action>> list();

    @POST("gtd/actions/")
    Call<Action> create(@Body Action action);

    @GET("gtd/actions/{id}/")
    Call<Action> detail(@Path("id") int id);

    @PUT("gtd/actions/{id}/")
    Call<Action> update(@Path("id") int id, @Body Action action);

    @PATCH("gtd/actions/{id}/")
    Call<Action> partialUpdate(@Path("id") int id, @Body Action action);

    @DELETE("gtd/actions/{id}/")
    Call<Action> delete(@Path("id") int id);
}