package vn.fjobs.base.view.pullrefreshview;


import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;


public class AppRecyclerView extends RecyclerView {

    private OnAppScrollListener onAppScrollListener;

    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onChanged() {
            checkIfEmpty();
        }
    };

    public AppRecyclerView(Context context) {
        super(context);
        initScrollListener();
    }

    public AppRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initScrollListener();
    }

    public AppRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initScrollListener();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        checkIfEmpty();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    private void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            final boolean isEmpty = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(isEmpty ? VISIBLE : GONE);
            setVisibility(isEmpty ? GONE : VISIBLE);
        }
    }

    private void initScrollListener() {
        this.addOnScrollListener(new AppRecyclerScrollListener());
    }

    public void setOnAppScrollListener(OnAppScrollListener onAppScrollListener) {
        this.onAppScrollListener = onAppScrollListener;
    }

    private final class AppRecyclerScrollListener extends RecyclerView.OnScrollListener {

        private LAYOUT_MANAGER_TYPE layoutManagerType;
        private int[] lastPositions;
        private int[] firstPositions;
        private int lastVisibleItemPosition;
        private int firstVisibleItemPosition;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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
                            .findFirstVisibleItemPosition();
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case GRID:
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case STAGGERED_GRID:
                    StaggeredGridLayoutManager staggeredGridLayoutManager
                            = (StaggeredGridLayoutManager) layoutManager;
                    if (lastPositions == null || lastPositions.length != staggeredGridLayoutManager.getSpanCount()) {
                        lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);

                    if (firstPositions == null || firstPositions.length != staggeredGridLayoutManager.getSpanCount()) {
                        firstPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findFirstVisibleItemPositions(firstPositions);
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
            if (visibleItemCount > 0 && mSwipe == SWIPE.SWIPE_TO_BOTTOM
                    && onAppScrollListener != null) {
                onAppScrollListener.onScroll(recyclerView, firstVisibleItemPosition, lastVisibleItemPosition, totalItemCount);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (lastVisibleItemPosition >= totalItemCount - 1 && onAppScrollListener != null)
                    onAppScrollListener.onBottom(recyclerView);
                if (firstVisibleItemPosition == 0 && onAppScrollListener != null)
                    onAppScrollListener.onTop(recyclerView);
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
