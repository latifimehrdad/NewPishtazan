package ir.bppir.pishtazan.views.adapterts;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BindingAdapter;

import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.views.application.PishtazanApp;

public class BindingAdapters {


    //______________________________________________________________________________________________ setImageUrl
    @BindingAdapter(value = "setImageUrl")
    public static void setImageUrl(SimpleDraweeView draweeView, String imageUrl) {

        Context context = draweeView.getContext();
        PishtazanApp.getApplication(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .setRoundImage(draweeView, context.getResources().getColor(R.color.colorAccent),2, 10,10,10,10);


        if (imageUrl != null && !imageUrl.isEmpty()) {
            PishtazanApp.getApplication(context)
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .setProgressBarForLoadImage(draweeView,
                            context.getResources().getColor(R.color.colorPrimary),
                            context.getResources().getColor(R.color.colorAccent),
                            5);

            draweeView.getHierarchy().setOnFadeFinishedListener(new FadeDrawable.OnFadeFinishedListener() {
                @Override
                public void onFadeFinished() {
/*                    PishtazanApp.getApplication(context)
                            .getUtilityComponent()
                            .getApplicationUtility()
                            .setRoundCircleImage(draweeView, context.getResources().getColor(R.color.colorAccent),2);*/
                }
            });


            String url = PishtazanApp.getApplication(context).host + imageUrl;
            draweeView.setImageURI(url);
        }
    }
    //______________________________________________________________________________________________ setImageUrl


}
