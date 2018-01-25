package uk.co.makosoft.naggingnelly.data.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class LoginDetails implements Comparable<LoginDetails>, Parcelable {

    public abstract String email();
    public abstract String password();

    public static LoginDetails create(LoginDetails loginDetails) {
        return new AutoValue_LoginDetails(loginDetails.email(), loginDetails.password());
    }

    public static Builder builder() {
        return new AutoValue_LoginDetails.Builder();
    }
    public Builder newBuilder() {
        return new AutoValue_LoginDetails.Builder(this);
    }

    public static TypeAdapter<LoginDetails> typeAdapter(Gson gson) {
        return new AutoValue_LoginDetails.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull LoginDetails another) {
        return -email().compareTo(another.email());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract LoginDetails.Builder setEmail(String email);
        public abstract LoginDetails.Builder setPassword(String password);
        public abstract LoginDetails build();
    }
}

