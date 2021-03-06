package uk.co.makosoft.naggingnelly.data.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Action implements Comparable<Action>, Parcelable {

    public abstract Integer id();
    @SerializedName("short_description") public abstract String shortDescription();
    public abstract Integer status();
    public abstract Integer priority();

    public static Action create(Action action) {
        return new AutoValue_Action(action.id(), action.shortDescription(), action.status(), action.priority());
    }

    public static Builder builder() {
        return new AutoValue_Action.Builder();
    }
    public Builder newBuilder() {
        return new AutoValue_Action.Builder(this);
    }

    public static TypeAdapter<Action> typeAdapter(Gson gson) {
        return new AutoValue_Action.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull Action another) {
        return -priority().compareTo(another.priority());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Action.Builder setId(Integer id);
        public abstract Action.Builder setShortDescription(String shortDescription);
        public abstract Action.Builder setStatus(Integer status);
        public abstract Action.Builder setPriority(Integer status);
        public abstract Action build();
    }
}

