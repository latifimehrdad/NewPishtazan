package ir.bppir.pishtazan.moderls;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class MD_PanelActionMenu {

    private String title;

    private Drawable icon;

    private Drawable background;

    private int tint;

    private int action;

    private Bundle bundle;

    private boolean goFragment;


    public MD_PanelActionMenu(String title, Drawable icon, Drawable background, int tint, int action, Bundle bundle, boolean goFragment) {
        this.title = title;
        this.icon = icon;
        this.background = background;
        this.tint = tint;
        this.action = action;
        this.bundle = bundle;
        this.goFragment = goFragment;
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

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public int getTint() {
        return tint;
    }

    public void setTint(int tint) {
        this.tint = tint;
    }

    public boolean isGoFragment() {
        return goFragment;
    }

    public void setGoFragment(boolean goFragment) {
        this.goFragment = goFragment;
    }

}
