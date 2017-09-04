package vn.fjobs.app.main.menus;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import co.core.fragments.NavigationManager;
import project.fjobs.R;
import vn.fjobs.app.main.MainActivity;

public class MainMenuLeft implements View.OnClickListener {

    private MainActivity activity;
    private LinearLayout menuLeftRoot;
    private NavigationManager navigationManager;
    private LinearLayout searchNearWork;

    public MainMenuLeft(MainActivity activity, LinearLayout menuLeftRoot, NavigationManager navigationManager) {
        this.activity = activity;
        this.menuLeftRoot = menuLeftRoot;
        this.navigationManager = navigationManager;
        initView();
        display();
    }

    private void initView() {
        searchNearWork = (LinearLayout) menuLeftRoot.findViewById(R.id.layout_near_work);
        searchNearWork.setOnClickListener(this);
    }

    private void display(){

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.layout_near_work:
                Toast.makeText(activity, "Test Menu", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

    }
}
