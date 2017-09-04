package vn.fjobs.base.view.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GestureTouchRelativeLayout extends LinearLayout {

    private GestureDetector mGestureDetector;
    public GestureTouchRelativeLayout(Context context) {
        super(context);
    }

    public GestureTouchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureTouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mGestureDetector!=null)
            mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        this.mGestureDetector = gestureDetector;
    }
}
