package ir.bppir.pishtazan.views.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.bppir.pishtazan.R;
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


}
