package uk.co.makosoft.naggingnelly.data.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Folder implements Comparable<Folder>, Parcelable {

    public abstract Integer id();
    public abstract String name();

    public static Folder create(Folder folder) {
        return new AutoValue_Folder(folder.id(), folder.name());
    }

    public static Builder builder() {
        return new AutoValue_Folder.Builder();
    }

    public static TypeAdapter<Folder> typeAdapter(Gson gson) {
        return new AutoValue_Folder.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull Folder another) {
        return id().compareTo(another.id());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Folder.Builder setId(Integer id);
        public abstract Folder.Builder setName(String name);
        public abstract Folder build();
    }
}

