package vn.fjobs.base.fragments.actionbar;

import android.view.View;

import co.core.actionbar.CustomActionbar;

public interface AppActionbar extends CustomActionbar {

    void updateTitle(String title);

    void displayUnreadChatMessage(int notificationNum);

    void displayUnreadNotification(int notificationNum);

    View getActionbarLayout();
}
