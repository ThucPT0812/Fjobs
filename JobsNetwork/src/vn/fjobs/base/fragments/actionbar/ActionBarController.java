package vn.fjobs.base.fragments.actionbar;

import android.support.v4.app.Fragment;
import android.view.View;

public interface ActionBarController {

    int findResourceIdForActionbar(Fragment fragment);

    void findChildViews(View view);

    void setupChildViews();

    void updateStackChildViews();

    void syncChildView(Fragment activePage);

    void updateTitle(String title);

    void displayUnreadChatMessage(int notificationNum);

    void displayUnreadNotification(int notificationNum);

}
