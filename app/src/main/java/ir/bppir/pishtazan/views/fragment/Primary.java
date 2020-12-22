package ir.bppir.pishtazan.views.fragment;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.utility.PersonType;
import ir.bppir.pishtazan.utility.loadings.RecyclerViewSkeletonScreen;
import ir.bppir.pishtazan.utility.loadings.Skeleton;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.adapterts.AP_Loading;
import ir.mlcode.latifiarchitecturelibrary.fragments.FR_Latifi;

public class Primary extends FR_Latifi {

    RecyclerViewSkeletonScreen skeletonScreen;
    AP_Loading ap_loading;


    //______________________________________________________________________________________________ onCreateView
    @Override
    public void onStart() {
        super.onStart();
        MainActivity.hideTitle();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ setRecyclerLoading
    public void setRecyclerLoading(RecyclerView recyclerLoading, int layout) {

        ap_loading = new AP_Loading();
        recyclerLoading.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        skeletonScreen = Skeleton.bind(recyclerLoading)
                .adapter(ap_loading)
                .load(layout)
                .shimmer(true)      // whether show shimmer animation.                      default is true
                .count(3)          // the recycler view item count.                        default is 10
                .color(R.color.ML_recyclerLoading)       // the shimmer color.                                   default is #a2878787
                .angle(20)          // the shimmer angle.                                   default is 20;
                .duration(1200)     // the shimmer animation duration.                      default is 1000;
                .frozen(false)
                .show();
    }
    //______________________________________________________________________________________________ setRecyclerLoading


    //______________________________________________________________________________________________ stopLoadingRecycler
    public void stopLoadingRecycler() {

        if (skeletonScreen != null) {
            skeletonScreen.hide();
            skeletonScreen = null;
        }

        if (ap_loading != null)
            ap_loading = null;
    }
    //______________________________________________________________________________________________ stopLoadingRecycler


    //______________________________________________________________________________________________ createDialog
    public Dialog createDialog(@LayoutRes int layoutResID){

        Dialog dialog;
        dialog = new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutResID);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        return dialog;
    }
    //______________________________________________________________________________________________ createDialog


    //______________________________________________________________________________________________ configImageView
    public void configImageView(ImageView imageView, Drawable icon, int tint){
        imageView.setImageDrawable(icon);
        imageView.setColorFilter(tint);
    }
    //______________________________________________________________________________________________ configImageView



}
