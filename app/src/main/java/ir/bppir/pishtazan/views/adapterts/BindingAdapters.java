package ir.bppir.pishtazan.views.adapterts;

import android.content.Context;

import androidx.databinding.BindingAdapter;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.views.application.PishtazanApp;

public class BindingAdapters {


    @BindingAdapter(value = "setPersonImage")
    public static void setPersonImage(SimpleDraweeView simpleDraweeView, String url) {

        Context context = simpleDraweeView.getContext();

        simpleDraweeView.setActualImageResource(R.drawable.logo_pishtazan);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(context.getResources().getColor(R.color.colorPrimary), 3);
        roundingParams.setCornersRadii(30, 30, 0, 0);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
        PishtazanApp
                .getApplication(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .setProgressBarForLoadImage(simpleDraweeView,
                        context.getResources().getColor(R.color.colorPrimary),
                        context.getResources().getColor(R.color.colorAccent),5);
        url = PishtazanApp.host + url;
        simpleDraweeView.setImageURI(url);

    }


}
