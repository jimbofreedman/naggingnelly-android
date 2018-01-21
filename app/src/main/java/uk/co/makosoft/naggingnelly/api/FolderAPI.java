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

public interface FolderAPI {
    @GET("gtd/folders/")
    Call<List<Folder>> list();

    @POST("gtd/folders/")
    Call<Folder> create(@Body Folder folder);

    @GET("gtd/folders/{id}/")
    Call<Folder> detail(@Path("id") int id);

    @PUT("gtd/folders/{id}/")
    Call<Folder> update(@Path("id") int id, @Body Folder folder);

    @PATCH("gtd/folders/{id}/")
    Call<Folder> partialUpdate(@Path("id") int id, @Body Folder folder);

    @DELETE("gtd/folders/{id}/")
    Call<Folder> delete(@Path("id") int id);
}