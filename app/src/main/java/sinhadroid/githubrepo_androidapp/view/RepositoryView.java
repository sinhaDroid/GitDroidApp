package sinhadroid.githubrepo_androidapp.view;

import android.content.Context;

import sinhadroid.githubrepo_androidapp.model.User;

/**
 * Created by deepanshu on 28/11/16.
 */
public interface RepositoryView {

    void showOwner(final User owner);

    Context getContext();
}
