package uk.co.makosoft.naggingnelly.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;
import uk.co.makosoft.naggingnelly.injection.ApplicationContext;

@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "android_boilerplate_pref_file";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public String getToken() {
        return mPref.getString("token", "");
    }

    public String setToken(String token) {
        Timber.i(String.format("Saving token %s", token));
        mPref.edit().putString("token", token).commit();
        return token;
    }

}
