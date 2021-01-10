package ir.bppir.pishtazan.views.adapterts;

import android.content.Context;

import androidx.databinding.BindingAdapter;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.utility.PersonLevel;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_EditText;

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
                            context.getResources().getColor(R.color.dayColorPrimary),
                            context.getResources().getColor(R.color.dayColorAccent), 5);
            url = PishtazanApp.host + url;
            simpleDraweeView.setImageURI(url);
        }

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(context.getResources().getColor(R.color.dayColorPrimary), 1);
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
                            context.getResources().getColor(R.color.dayColorPrimary),
                            context.getResources().getColor(R.color.dayColorAccent), 5);
            url = PishtazanApp.host + url;
            simpleDraweeView.setImageURI(url);
        }

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(context.getResources().getColor(R.color.dayColorPrimary), 4);
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
        roundingParams.setBorder(context.getResources().getColor(R.color.dayColorPrimary), 3);
        roundingParams.setCornersRadii(15, 0, 40, 0);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
    }
    //______________________________________________________________________________________________ setPersonDegree


    //______________________________________________________________________________________________ setMLEditText
    @BindingAdapter(value = "setMlEditText")
    public static void setMLEditText(ML_EditText mlEditText, String value) {

        String tag = mlEditText.getTag().toString();
        Context context = mlEditText.getContext();
        switch (tag) {
            case "birthDay" :
                String text = context.getResources().getString(R.string.birthDay) + System.getProperty("line.separator") + value;
                mlEditText.setText(text);
                break;
        }
    }
    //______________________________________________________________________________________________ setMLEditText


}
