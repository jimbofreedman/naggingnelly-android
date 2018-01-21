package uk.co.makosoft.naggingnelly.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContextAPI {
    @GET("gtd/contexts/")
    Call<List<Context>> list();

    @POST("gtd/contexts/")
    Call<Context> create(@Body Context context);

    @GET("gtd/contexts/{id}/")
    Call<Context> detail(@Path("id") int id);

    @PUT("gtd/contexts/{id}/")
    Call<Context> update(@Path("id") int id, @Body Context context);

    @PATCH("gtd/contexts/{id}/")
    Call<Context> partialUpdate(@Path("id") int id, @Body Context context);

    @DELETE("gtd/contexts/{id}/")
    Call<Context> delete(@Path("id") int id);
}