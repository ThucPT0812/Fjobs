package vn.fjobs.app.top;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.fjobs.R;
import vn.fjobs.app.common.actionbar.controller.ActionbarForTopController;
import vn.fjobs.app.main.adapter.RecyclerTouchListener;
import vn.fjobs.app.main.adapter.TopAdapter;
import vn.fjobs.app.main.entity.TopEntities;
import vn.fjobs.base.fragments.BaseAppFragment;
import vn.fjobs.base.fragments.actionbar.ActionBarControl;
import vn.fjobs.base.fragments.actionbar.ActionBarController;
import vn.fjobs.base.view.pullrefreshview.AppPullRefreshLayout;
import vn.fjobs.base.view.pullrefreshview.OnAppRefreshListener;
import vn.fjobs.base.view.pullrefreshview.OnAppScrollListener;

public class TopFragment extends BaseAppFragment implements ActionBarControl, OnAppRefreshListener, OnAppScrollListener {

    private AppPullRefreshLayout pullRefreshListCompany;
    private TopAdapter topAdapter;
    private List<TopEntities> topEntitiesList = new ArrayList<>();
    private TextView txtEmpty;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_top;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        pullRefreshListCompany = (AppPullRefreshLayout) view.findViewById(R.id.pullRefresh_company);

        txtEmpty = (TextView) view.findViewById(R.id.empty_view);
        txtEmpty.setText(R.string.loading);
        pullRefreshListCompany.setEmptyView(txtEmpty);
        topAdapter = new TopAdapter(topEntitiesList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        pullRefreshListCompany.setAdapter(topAdapter);
        pullRefreshListCompany.setLayoutManager(linearLayoutManager);
        pullRefreshListCompany.setOnAppRefreshListener(this);
        pullRefreshListCompany.setOnAppScrollListener(this);
        pullRefreshListCompany.getRecyclerView().addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                pullRefreshListCompany.getRecyclerView(), new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), "Test", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        mockData();
    }

    @Override
    public ActionBarController getActionBarControl() {
        return new ActionbarForTopController(mNavigationManager);
    }

    private void mockData(){
        TopEntities topEntities = new TopEntities("", "", "Company Name A", "Company descriptions");
        topEntitiesList.add(topEntities);

        topEntities = new TopEntities("", "", "Company Name B", "Company descriptions");
        topEntitiesList.add(topEntities);

        topEntities = new TopEntities("", "", "Company Name C", "Company descriptions");
        topEntitiesList.add(topEntities);

        topEntities = new TopEntities("", "", "Company Name E", "Company descriptions");
        topEntitiesList.add(topEntities);

        topEntities = new TopEntities("", "", "Company Name F", "Company descriptions");
        topEntitiesList.add(topEntities);
        topAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        topAdapter.clearAllData();
        mockData();
        pullRefreshListCompany.setRefreshing(false);
    }

    @Override
    public void onTop(RecyclerView recyclerView) {

    }

    @Override
    public void onBottom(RecyclerView recyclerView) {

    }

    @Override
    public void onScroll(RecyclerView recyclerView, int firstVisibleItemPosition, int lastVisibleItemPosition, int totalItemCount) {

    }
}
