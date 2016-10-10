package sinhadroid.githubrepo_androidapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sinhadroid.githubrepo_androidapp.R;
import sinhadroid.githubrepo_androidapp.presenter.GithubPresenter;

/**
 * Created by deepanshu on 10/10/16.
 */
public class GithubFragment extends Fragment implements GithubView {

    private GithubPresenter mGithubPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up presenter
        mGithubPresenter =
    }
}
