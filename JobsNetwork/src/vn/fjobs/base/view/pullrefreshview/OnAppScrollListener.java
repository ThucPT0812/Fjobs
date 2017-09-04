package vn.fjobs.base.view.pullrefreshview;

import android.support.v7.widget.RecyclerView;

public interface OnAppScrollListener {

    void onTop(RecyclerView recyclerView);

    void onBottom(RecyclerView recyclerView);

    void onScroll(RecyclerView recyclerView, int firstVisibleItemPosition,
                  int lastVisibleItemPosition, int totalItemCount);
}
