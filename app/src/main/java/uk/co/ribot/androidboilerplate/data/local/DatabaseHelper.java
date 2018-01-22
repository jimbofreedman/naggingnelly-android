package uk.co.ribot.androidboilerplate.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;

import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import uk.co.ribot.androidboilerplate.data.model.Action;
import uk.co.ribot.androidboilerplate.data.model.Folder;
import uk.co.ribot.androidboilerplate.data.model.Ribot;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        this(dbOpenHelper, Schedulers.io());
    }

    @VisibleForTesting
    public DatabaseHelper(DbOpenHelper dbOpenHelper, Scheduler scheduler) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, scheduler);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<Ribot> setRibots(final Collection<Ribot> newRibots) {
        return Observable.create(new ObservableOnSubscribe<Ribot>() {
            @Override
            public void subscribe(ObservableEmitter<Ribot> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.RibotProfileTable.TABLE_NAME, null);
                    for (Ribot ribot : newRibots) {
                        long result = mDb.insert(Db.RibotProfileTable.TABLE_NAME,
                                Db.RibotProfileTable.toContentValues(ribot.profile()),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) emitter.onNext(ribot);
                    }
                    transaction.markSuccessful();
                    emitter.onComplete();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Ribot>> getRibots() {
        return mDb.createQuery(Db.RibotProfileTable.TABLE_NAME,
                "SELECT * FROM " + Db.RibotProfileTable.TABLE_NAME)
                .mapToList(new Function<Cursor, Ribot>() {
                    @Override
                    public Ribot apply(@NonNull Cursor cursor) throws Exception {
                        return Ribot.create(Db.RibotProfileTable.parseCursor(cursor));
                    }
                });
    }

    public Observable<Folder> setFolders(final Collection<Folder> newFolders) {
        return Observable.create(new ObservableOnSubscribe<Folder>() {
            @Override
            public void subscribe(ObservableEmitter<Folder> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.FolderTable.TABLE_NAME, null);
                    for (Folder folder : newFolders) {
                        long result = mDb.insert(Db.FolderTable.TABLE_NAME,
                                Db.FolderTable.toContentValues(folder),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) emitter.onNext(folder);
                    }
                    transaction.markSuccessful();
                    emitter.onComplete();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Folder>> getFolders() {
        return mDb.createQuery(Db.FolderTable.TABLE_NAME,
                "SELECT * FROM " + Db.FolderTable.TABLE_NAME)
                .mapToList(new Function<Cursor, Folder>() {
                    @Override
                    public Folder apply(@NonNull Cursor cursor) throws Exception {
                        return Folder.create(Db.FolderTable.parseCursor(cursor));
                    }
                });
    }

    public Observable<Action> setActions(final Collection<Action> newActions) {
        return Observable.create(new ObservableOnSubscribe<Action>() {
            @Override
            public void subscribe(ObservableEmitter<Action> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.ActionTable.TABLE_NAME, null);
                    for (Action action : newActions) {
                        long result = mDb.insert(Db.ActionTable.TABLE_NAME,
                                Db.ActionTable.toContentValues(action),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) emitter.onNext(action);
                    }
                    transaction.markSuccessful();
                    emitter.onComplete();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Action>> getActions() {
        return mDb.createQuery(Db.ActionTable.TABLE_NAME,
                "SELECT * FROM " + Db.ActionTable.TABLE_NAME)
                .mapToList(new Function<Cursor, Action>() {
                    @Override
                    public Action apply(@NonNull Cursor cursor) throws Exception {
                        return Action.create(Db.ActionTable.parseCursor(cursor));
                    }
                });
    }
}
