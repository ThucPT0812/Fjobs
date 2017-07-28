package vn.fjobs.base.activities.menu;

public interface CustomSlidingMenu {
    /**
     * show Main content
     */
    void showContent();

    /**
     * show left menu
     */
    void show();
    /**
     * hide left menu
     */
    void hide();
    /**
     * show right menu
     */
    void showSecondary();
    /**
     * show right menu
     */
    void hideSecondary();

    /**
     * toggle left menu
     */
    void toggle();

    void setEnableSliding(boolean enable);

    void setTouchMode(int mode);
}
