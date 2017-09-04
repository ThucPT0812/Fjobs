package vn.fjobs.base.view.pullrefreshview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import project.fjobs.R;

public class AppPullRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    private AppRecyclerView recyclerView;
    private OnAppScrollListener onAppScrollListener;
    private OnAppRefreshListener onAppRefreshListener;

    public AppPullRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public AppPullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.setOnRefreshListener(this);
        recyclerView = new AppRecyclerView(context);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setId(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);

        recyclerView.addOnScrollListener(new OnAppRecyclerScrollListener() {
            @Override
            public void onTop(RecyclerView recyclerView) {
                super.onTop(recyclerView);
                if (onAppScrollListener != null) {
                    onAppScrollListener.onTop(recyclerView);
                }
            }

            @Override
            public void onScroll(RecyclerView recyclerView, int firstVisibleItemPosition, int lastVisibleItemPosition, int totalItemCount) {
                super.onScroll(recyclerView, firstVisibleItemPosition, lastVisibleItemPosition, totalItemCount);
                if (onAppScrollListener != null) {
                    onAppScrollListener.onScroll(recyclerView, firstVisibleItemPosition, lastVisibleItemPosition, totalItemCount);
                }
            }

            @Override
            public void onBottom(RecyclerView recyclerView) {
                super.onBottom(recyclerView);
                if (onAppScrollListener != null) {
                    onAppScrollListener.onBottom(recyclerView);
                }
            }
        });

        this.addView(recyclerView);
    }

    public AppRecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        if (this.recyclerView.getLayoutManager() != layout)
            this.recyclerView.setLayoutManager(layout);
    }

    public void setEmptyView(View emptyView) {
        this.recyclerView.setEmptyView(emptyView);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.recyclerView.setAdapter(adapter);
    }


    public void setOnAppScrollListener(OnAppScrollListener onAppScrollListener) {
        this.onAppScrollListener = onAppScrollListener;
    }

    public void setOnAppRefreshListener(OnAppRefreshListener onAppRefreshListener) {
        this.onAppRefreshListener = onAppRefreshListener;
    }

    @Override
    public void onRefresh() {
        if (onAppRefreshListener != null)
            onAppRefreshListener.onRefresh();
    }

}