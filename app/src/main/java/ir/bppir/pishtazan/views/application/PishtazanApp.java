package ir.bppir.pishtazan.views.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.moderls.MD_UserInfo;
import ir.bppir.pishtazan.utility.RetrofitApiInterface;
import ir.mlcode.latifiarchitecturelibrary.application.APP_Latifi;

public class PishtazanApp extends APP_Latifi {

    private Context context;
    public static String host = "https://pishtazan.bppir.com";
    private RetrofitApiInterface retrofitApiInterface;


    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        setContext(context);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                .create();
        setHost(host, gson);
        configurationRetrofit();
    }
    //______________________________________________________________________________________________ onCreate



    //______________________________________________________________________________________________ configurationRetrofit
    private void configurationRetrofit() {
        retrofitApiInterface = getRetrofitComponent().getRetrofit().create(RetrofitApiInterface.class);
    }
    //______________________________________________________________________________________________ configurationRetrofit



    //______________________________________________________________________________________________ getRetrofitApiInterface
    public RetrofitApiInterface getRetrofitApiInterface() {
        return retrofitApiInterface;
    }
    //______________________________________________________________________________________________ getRetrofitApiInterface



    //______________________________________________________________________________________________ getAutomationApp
    public static PishtazanApp getApplication(Context context) {
        return (PishtazanApp) context.getApplicationContext();
    }
    //______________________________________________________________________________________________ getAutomationApp



    //______________________________________________________________________________________________ saveProfile
    public boolean saveProfile(MD_UserInfo md_userInfo) {

        SharedPreferences.Editor token = context
                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
                .edit();

        token.putString(context.getString(R.string.ML_FullName), md_userInfo.getFullName());
        token.putString(context.getString(R.string.ML_NationalCode), md_userInfo.getNationalCode());
        token.putInt(context.getString(R.string.ML_Id), md_userInfo.getId());
        token.putInt(context.getString(R.string.ML_ColleagueId), md_userInfo.getColleagueId());
        token.apply();
        return true;
    }
    //______________________________________________________________________________________________ saveProfile



    //______________________________________________________________________________________________ logOut
    public boolean logOut(Context context) {
        SharedPreferences.Editor token = context
                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
                .edit();

        token.putString(context.getString(R.string.ML_FullName), null);
        token.putString(context.getString(R.string.ML_NationalCode), null);
        token.putInt(context.getString(R.string.ML_Id), 0);
        token.putInt(context.getString(R.string.ML_ColleagueId), 0);
        token.apply();
        return true;
    }
    //______________________________________________________________________________________________ logOut



    //______________________________________________________________________________________________ getUserId
    public int getUserId() {
        SharedPreferences share = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (share == null)
            return 0;
        else
            return share.getInt(context.getResources().getString(R.string.ML_Id), 0);
    }
    //______________________________________________________________________________________________ getUserId



    //______________________________________________________________________________________________ getColleagueId
    public int getColleagueId() {
        SharedPreferences share = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (share == null)
            return 0;
        else
            return share.getInt(context.getResources().getString(R.string.ML_ColleagueId), 0);
    }
    //______________________________________________________________________________________________ getColleagueId

}
