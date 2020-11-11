package ir.bppir.pishtazan.moderls;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MD_PanelActionMenu {

    private String title;

    private Drawable icon;

    private int action;

    private Bundle bundle;

    public MD_PanelActionMenu(String title, Drawable icon, int action, Bundle bundle) {
        this.title = title;
        this.icon = icon;
        this.action = action;
        this.bundle = bundle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
