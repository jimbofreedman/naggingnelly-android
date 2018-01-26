package uk.co.makosoft.naggingnelly.data.remote;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import uk.co.makosoft.naggingnelly.data.local.PreferencesHelper;
import uk.co.makosoft.naggingnelly.data.model.Action;
import uk.co.makosoft.naggingnelly.util.MyGsonTypeAdapterFactory;

public interface ActionsService {

    String ENDPOINT = "http://10.0.2.2:8000";

    @GET("/gtd/actions/")
    Observable<List<Action>> getActions();

    @PATCH("/gtd/actions/{id}/")
    Observable<Action> putAction(@Path("id") Integer id, @Body Action actions);

    /******** Helper class that sets up a new services *******/
    class Creator {
        public static ActionsService newActionsService(PreferencesHelper preferencesHelper) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(15, TimeUnit.SECONDS);
            httpClient.readTimeout(15, TimeUnit.SECONDS);
            httpClient.addInterceptor(logging);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", String.format("Token {}", preferencesHelper.getToken())).build();
                    return chain.proceed(request);
                }
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ActionsService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
            return retrofit.create(ActionsService.class);
        }
    }
}
