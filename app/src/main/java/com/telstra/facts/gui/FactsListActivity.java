package com.telstra.facts.gui;

/**
 * Created by ravi.gami on 3/8/17.
 */

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.telstra.facts.R;
import com.telstra.facts.commons.RecyclerItemClickListener;
import com.telstra.facts.gui.adapter.FactAdapter;
import com.telstra.facts.http.ServiceManager;
import com.telstra.facts.http.ServiceManagerResponse;
import com.telstra.facts.model.Error;
import com.telstra.facts.model.FactResponse;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String FACTS_LIST_TAG = FactsListActivity.class.getName();

    @BindView(R.id.activity_text_view)
    AppCompatTextView activityTextView;
    @BindView(R.id.fact_recycler_view)
    RecyclerView factRecyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private Menu topMenu;
    private FactAdapter factAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(FactsListActivity.this);

        initUI();
        getFactInfo();
    }

    /**
     * Initializes the UI elements.
     */
    private void initUI() {
        Log.d(FACTS_LIST_TAG, "initUI called");
        swipeRefreshLayout.setOnRefreshListener(this);

        factRecyclerView.setLayoutManager(new LinearLayoutManager(FactsListActivity.this));
        factRecyclerView.setItemAnimator(new DefaultItemAnimator());
        factAdapter = new FactAdapter(this);
        factRecyclerView.setAdapter(factAdapter);

        factRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, factRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(FACTS_LIST_TAG, "onItemClick at " + position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Log.d(FACTS_LIST_TAG, "onLongItemClick at " + position);
            }
        }));
    }

    /**
     * Updates the UI elements based on values received from server.
     * @param factResponse
     * @param success
     */
    private void plotUI(FactResponse factResponse, boolean success) {
        swipeRefreshLayout.setRefreshing(false);
        showHideTopMenuItem(true);

        if (success) {
            setToolbarTitle(factResponse.getTitle());
            activityTextView.setVisibility(View.GONE);
            if (factAdapter != null) {
                factAdapter.updateFactList(factResponse.getFilteredFacts());
            }
        } else {
            setToolbarTitle(getString(R.string.title_activity_facts_list));
            activityTextView.setText(getString(R.string.no_data_available));
            activityTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_facts_list, menu);
        this.topMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Item selection event handler
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getFactInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Callback method when user performs pull to refresh.
     */
    @Override
    public void onRefresh() {
        getFactInfo();
    }

    /**
     * Enable/Disable top tool bar based on flag.
     * @param isEnabled
     */
    private void showHideTopMenuItem(final boolean isEnabled) {
        if(topMenu != null){
            if(isEnabled) {
                topMenu.findItem(R.id.action_refresh).setEnabled(true);
            }else{
                topMenu.findItem(R.id.action_refresh).setEnabled(false);
            }
        }
    }

    /**
     * Upates the toolbar title with title string value.
     * @param toolbarTitle
     */
    private void setToolbarTitle(String toolbarTitle) {
        if(!StringUtils.isEmpty(toolbarTitle)){
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(toolbarTitle);
            }
        }
    }

    /**
     * Send REST API Call to retrieve the information from remote server.
     */
    private void getFactInfo() {
        Log.d(FACTS_LIST_TAG, "getFactInfo");
        swipeRefreshLayout.setRefreshing(true);
        activityTextView.setText(getString(R.string.loading_data));
        showHideTopMenuItem(false);

        ServiceManager.getInstance().getFactsInformation(this, new ServiceManagerResponse() {

            @Override
            public void onResponse(boolean success, Object response, Error error) {
                Log.d(FACTS_LIST_TAG, "<getFactInfo>::onResponse");
                if (success && response != null) {
                    FactResponse factResponse = (FactResponse) response;
                    plotUI(factResponse, success);
                }
            }

            @Override
            public void onError(Error error) {
                Log.d(FACTS_LIST_TAG, "<getFactInfo>::onError");
                if (error != null && !StringUtils.isEmpty(error.getError())) {
                    Toast.makeText(FactsListActivity.this, error.getError(), Toast.LENGTH_LONG).show();
                }
                plotUI(null, false);
            }
        });
    }
}
