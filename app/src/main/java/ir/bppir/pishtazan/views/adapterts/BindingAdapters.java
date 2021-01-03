package ir.bppir.pishtazan.views.adapterts;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.databinding.BindingAdapter;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.utility.PersonLevel;
import ir.bppir.pishtazan.views.application.PishtazanApp;

public class BindingAdapters {


    //______________________________________________________________________________________________ setPersonImage
    @BindingAdapter(value = "setPersonImage")
    public static void setPersonImage(SimpleDraweeView simpleDraweeView, String url) {

        Context context = simpleDraweeView.getContext();
        if (url == null || url.isEmpty())
            simpleDraweeView.setActualImageResource(R.drawable.logo_pishtazan);
        else {
            PishtazanApp
                    .getApplication(context)
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .setProgressBarForLoadImage(simpleDraweeView,
                            context.getResources().getColor(R.color.colorPrimary),
                            context.getResources().getColor(R.color.colorAccent), 5);
            url = PishtazanApp.host + url;
            simpleDraweeView.setImageURI(url);
        }

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(context.getResources().getColor(R.color.colorPrimary), 1);
        roundingParams.setCornersRadii(15, 15, 15, 15);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
    }
    //______________________________________________________________________________________________ setPersonImage



    //______________________________________________________________________________________________ setProfileImage
    @BindingAdapter(value = "setProfileImage")
    public static void setProfileImage(SimpleDraweeView simpleDraweeView, String url) {

        Context context = simpleDraweeView.getContext();
        if (url == null || url.isEmpty())
            simpleDraweeView.setActualImageResource(R.drawable.logo_pishtazan);
        else {
            PishtazanApp
                    .getApplication(context)
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .setProgressBarForLoadImage(simpleDraweeView,
                            context.getResources().getColor(R.color.colorPrimary),
                            context.getResources().getColor(R.color.colorAccent), 5);
            url = PishtazanApp.host + url;
            simpleDraweeView.setImageURI(url);
        }

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(context.getResources().getColor(R.color.colorPrimary), 4);
        roundingParams.setCornersRadii(15, 15, 0, 0);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
    }
    //______________________________________________________________________________________________ setProfileImage




    //______________________________________________________________________________________________ setPersonDegree
    @BindingAdapter(value = "setPersonDegree")
    public static void setPersonDegree(SimpleDraweeView simpleDraweeView, Integer degree) {

        Context context = simpleDraweeView.getContext();

        if (degree.byteValue() == PersonLevel.normal)
            simpleDraweeView.setActualImageResource(R.drawable.normal_icon);
        else if (degree.byteValue() == PersonLevel.peach)
            simpleDraweeView.setActualImageResource(R.drawable.peach_icon);
        else if (degree.byteValue() == PersonLevel.giant)
            simpleDraweeView.setActualImageResource(R.drawable.giant_icon);

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(context.getResources().getColor(R.color.colorPrimary), 3);
        roundingParams.setCornersRadii(15, 0, 40, 0);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
    }
    //______________________________________________________________________________________________ setPersonDegree


}
