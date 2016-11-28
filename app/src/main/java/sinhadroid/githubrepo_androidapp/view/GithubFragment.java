package sinhadroid.githubrepo_androidapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import sinhadroid.githubrepo_androidapp.R;
import sinhadroid.githubrepo_androidapp.RepositoryAdapter;
import sinhadroid.githubrepo_androidapp.model.Repository;
import sinhadroid.githubrepo_androidapp.presenter.GithubPresenter;
import sinhadroid.githubrepo_androidapp.presenter.GithubPresenterImpl;

/**
 * Created by deepanshu on 10/10/16.
 */
public class GithubFragment extends Fragment implements GithubView {

    private GithubPresenter mGithubPresenter;

    private RecyclerView mRecyclerView;
    private EditText editTextUsername;
    private ProgressBar progressBar;
    private TextView infoTextView;
    private ImageButton searchButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progress);
        infoTextView = (TextView) getActivity().findViewById(R.id.text_info);

        // Set up RecyclerView
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.repos_recycler_view);
        setupRecyclerView(mRecyclerView);

        // Set up search button
        searchButton = (ImageButton) getActivity().findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGithubPresenter.loadRepositories(editTextUsername.getText().toString());
            }
        });

        //Set up username EditText
        editTextUsername = (EditText) getActivity().findViewById(R.id.edit_text_username);
        editTextUsername.addTextChangedListener(mHideShowButtonTextWatcher);
        editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mGithubPresenter.loadRepositories(editTextUsername.getText().toString());
                    return true;
                }
                return false;
            }
        });

        // Set up presenter
        mGithubPresenter = GithubPresenterImpl.newInstance(this);
    }

    @Override
    public void onDestroyView() {
        mGithubPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showRepositories(List<Repository> repositories) {
        RepositoryAdapter adapter = (RepositoryAdapter) mRecyclerView.getAdapter();
        adapter.setRepositories(repositories);
        adapter.notifyDataSetChanged();
        mRecyclerView.requestFocus();
        hideSoftKeyboard();
        progressBar.setVisibility(View.INVISIBLE);
        infoTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(int text_empty_repos) {
        progressBar.setVisibility(View.INVISIBLE);
        infoTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        infoTextView.setText(getString(text_empty_repos));
    }

    @Override
    public void showProgressIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        infoTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        RepositoryAdapter repositoryAdapter = new RepositoryAdapter();
        repositoryAdapter.setCallback(new RepositoryAdapter.Callback() {
            @Override
            public void onItemClick(Repository repository) {
                startActivity(RepositoryActivity.newIntent(getActivity(), repository));
            }
        });
        recyclerView.setAdapter(repositoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
    }

    private TextWatcher mHideShowButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
