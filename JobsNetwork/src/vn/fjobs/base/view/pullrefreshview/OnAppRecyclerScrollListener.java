package vn.fjobs.base.view.pullrefreshview;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class OnAppRecyclerScrollListener extends RecyclerView.OnScrollListener implements OnAppScrollListener {

    private LAYOUT_MANAGER_TYPE layoutManagerType;
    private int[] lastPositions;
    private int[] firstPositions;
    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;

    @Override
    public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        SWIPE mSwipe;
        if (dy > 0) {
            mSwipe = SWIPE.SWIPE_TO_BOTTOM;
        } else if (dy < 0) {
            mSwipe = SWIPE.SWIPE_TO_TOP;
        } else {
            mSwipe = SWIPE.NONE;
        }

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
        } else if (layoutManager instanceof LinearLayoutManager) {
            layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
        } else {
            throw new RuntimeException(
                    "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }

        switch (layoutManagerType) {
            case LINEAR:
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findFirstCompletelyVisibleItemPosition();
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPosition();
                break;
            case GRID:
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findFirstCompletelyVisibleItemPosition();
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null || lastPositions.length != staggeredGridLayoutManager.getSpanCount()) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);

                if (firstPositions == null || firstPositions.length != staggeredGridLayoutManager.getSpanCount()) {
                    firstPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(firstPositions);
                firstVisibleItemPosition = findMin(firstPositions);

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ((StaggeredGridLayoutManager) layoutManager).invalidateSpanAssignments();
                    }
                });
                break;
        }

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (visibleItemCount > 0 && mSwipe == SWIPE.SWIPE_TO_BOTTOM) {
            onScroll(recyclerView, firstVisibleItemPosition, lastVisibleItemPosition, totalItemCount);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (lastVisibleItemPosition >= totalItemCount - 1)
                onBottom(recyclerView);
            if (firstVisibleItemPosition == 0)
                onTop(recyclerView);
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    @Override
    public void onTop(RecyclerView recyclerView) {

    }

    @Override
    public void onBottom(RecyclerView recyclerView) {

    }

    @Override
    public void onScroll(RecyclerView recyclerView, int firstVisibleItemPosition,
                         int lastVisibleItemPosition, int totalItemCount) {

    }


    private enum SWIPE {
        NONE,
        SWIPE_TO_TOP,
        SWIPE_TO_BOTTOM
    }


    private enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }
}
