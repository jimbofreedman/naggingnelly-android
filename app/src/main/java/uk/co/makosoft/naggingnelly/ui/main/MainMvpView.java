package uk.co.makosoft.naggingnelly.ui.main;

import java.util.List;

import uk.co.makosoft.naggingnelly.data.model.Action;
import uk.co.makosoft.naggingnelly.data.model.Folder;
import uk.co.makosoft.naggingnelly.data.model.Ribot;
import uk.co.makosoft.naggingnelly.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showFolders(List<Folder> folders);

    void showFoldersEmpty();

    void showActions(List<Action> actions);

    void showActionsEmpty();

    void showError();

}
