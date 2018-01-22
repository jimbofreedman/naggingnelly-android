package uk.co.ribot.androidboilerplate.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Action;
import uk.co.ribot.androidboilerplate.data.model.Folder;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.FolderViewHolder> {

    private List<Action> mActions;

    @Inject
    public ActionsAdapter() {
        mActions = new ArrayList<>();
    }

    public void setActions(List<Action> actions) {
        mActions = actions;
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FolderViewHolder holder, int position) {
        Action action = mActions.get(position);
        holder.nameTextView.setText(String.format("%s", action.shortDescription()));
    }

    @Override
    public int getItemCount() {
        return mActions.size();
    }

    class FolderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name) TextView nameTextView;

        public FolderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
