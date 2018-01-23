package uk.co.makosoft.naggingnelly.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.makosoft.naggingnelly.R;
import uk.co.makosoft.naggingnelly.data.model.Action;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ActionViewHolder> {

    private List<Action> mActions;

    @Inject
    public ActionsAdapter() {
        mActions = new ArrayList<>();
    }

    public void setActions(List<Action> actions) {
        mActions = actions;
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_action, parent, false);
        return new ActionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ActionViewHolder holder, int position) {
        Action action = mActions.get(position);
        holder.nameTextView.setText(String.format("%s", action.shortDescription()));
        holder.completeButton.setTag(action);
        holder.statusTextView.setText(action.status().toString());
        holder.priorityTextView.setText(action.priority().toString());
    }

    @Override
    public int getItemCount() {
        return mActions.size();
    }

    class ActionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name) TextView nameTextView;
        @BindView(R.id.btn_complete) ImageButton completeButton;
        @BindView(R.id.statusText) TextView statusTextView;
        @BindView(R.id.priorityTextView) TextView priorityTextView;

        Action action;
        public Action getAction() { return this.action; };
        public void setAction(Action action) {
            this.action = action;
            // this.itemView.setTag(R.id.tag_action, action);
            this.completeButton.setTag(action.id());
        }

        public ActionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
