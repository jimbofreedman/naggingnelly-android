package uk.co.makosoft.naggingnelly.ui.main;

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
import uk.co.makosoft.naggingnelly.R;
import uk.co.makosoft.naggingnelly.data.model.Folder;

public class FoldersAdapter extends RecyclerView.Adapter<FoldersAdapter.FolderViewHolder> {

    private List<Folder> mFolders;

    @Inject
    public FoldersAdapter() {
        mFolders = new ArrayList<>();
    }

    public void setFolders(List<Folder> folders) {
        mFolders = folders;
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FolderViewHolder holder, int position) {
        Folder folder = mFolders.get(position);
        holder.nameTextView.setText(String.format("%s", folder.name()));
    }

    @Override
    public int getItemCount() {
        return mFolders.size();
    }

    class FolderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name) TextView nameTextView;

        public FolderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
