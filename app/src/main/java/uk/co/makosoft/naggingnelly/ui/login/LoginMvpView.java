package uk.co.makosoft.naggingnelly.ui.login;

import java.util.List;

import uk.co.makosoft.naggingnelly.data.model.Action;
import uk.co.makosoft.naggingnelly.data.model.Folder;
import uk.co.makosoft.naggingnelly.data.model.Ribot;
import uk.co.makosoft.naggingnelly.ui.base.MvpView;

public interface LoginMvpView extends MvpView {

    void showLogin();

    void showLoginSuccessful();

    void showError();
}
