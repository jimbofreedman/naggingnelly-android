package uk.co.makosoft.naggingnelly.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @GET("gtd/actions/")
    Call<List<Action>> getActions();

    @POST("gtd/actions/")
    Call<Action> postAction(@Body Action action);
}