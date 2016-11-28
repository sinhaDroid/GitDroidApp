package sinhadroid.githubrepo_androidapp.view;

import android.content.Context;

import java.util.List;

import sinhadroid.githubrepo_androidapp.model.Repository;

/**
 * Created by deepanshu on 10/10/16.
 */
public interface GithubView {

    void showProgressIndicator();

    Context getContext();

    void showRepositories(List<Repository> repositories);

    void showMessage(int text_empty_repos);
}
