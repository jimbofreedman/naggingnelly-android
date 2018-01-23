package uk.co.ribot.androidboilerplate.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.data.model.Action;
import uk.co.ribot.androidboilerplate.data.model.Folder;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;
import uk.co.ribot.androidboilerplate.util.DialogFactory;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject MainPresenter mMainPresenter;
    @Inject RibotsAdapter mRibotsAdapter;
    @Inject FoldersAdapter mFoldersAdapter;
    @Inject ActionsAdapter mActionsAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setAdapter(mActionsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);
        mMainPresenter.loadRibots();
        mMainPresenter.loadFolders();
        mMainPresenter.loadActions();

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showRibots(List<Ribot> ribots) {
        mRibotsAdapter.setRibots(ribots);
        mRibotsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots))
                .show();
    }

    @Override
    public void showRibotsEmpty() {
        mRibotsAdapter.setRibots(Collections.<Ribot>emptyList());
        mRibotsAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_ribots, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFolders(List<Folder> folders) {
        mFoldersAdapter.setFolders(folders);
        mFoldersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFoldersEmpty() {
        mFoldersAdapter.setFolders(Collections.<Folder>emptyList());
        mFoldersAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_folders, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showActions(List<Action> actions) {
        Collections.sort(actions);
        actions.removeIf((p) -> p.status() != 0); // only Status = OPEN
        if (actions.isEmpty()) {
            showActionsEmpty();
        } else {
            mActionsAdapter.setActions(actions);
            mActionsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showActionsEmpty() {
        mActionsAdapter.setActions(Collections.<Action>emptyList());
        mActionsAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_actions, Toast.LENGTH_LONG).show();
    }

    public void completeClick(View view) {
        Object o = view.getTag();
        Action action = (Action)o;

        Timber.i(String.format("Complete clicked! %s", action.shortDescription()));
        mMainPresenter.completeAction(action);
        mActionsAdapter.notifyDataSetChanged();
    }
}
