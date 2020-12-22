package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.moderls.MD_Update;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Splash extends VM_Primary {

    private MD_Update md_update;

    //______________________________________________________________________________________________ VM_Splash
    public VM_Splash(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Splash


    //______________________________________________________________________________________________ getRecourse
    public void getUpdate() {


        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .getUpdate());

        if (getPrimaryCall() == null)
            return;


        getPrimaryCall().enqueue(new Callback<MD_Update>() {
            @Override
            public void onResponse(Call<MD_Update> call, Response<MD_Update> response) {
                if (responseIsOk(response)) {
                    setResponseMessage("");
                    md_update = response.body();
                    checkUpdate();
                }
            }

            @Override
            public void onFailure(Call<MD_Update> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getRecourse


    //______________________________________________________________________________________________ checkUpdate
    private void checkUpdate() {
        PackageInfo pInfo;
        float versionName = 0;
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            versionName = Float.parseFloat(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        float lastVersion = md_update.getVresion();

        if (versionName < lastVersion) {
            setResponseMessage(getContext().getResources().getString(R.string.newVersionIsAvailable));
            sendActionToObservable(ObservableActions.gotoUpdate);
        } else
            checkToken();
    }
    //______________________________________________________________________________________________ checkUpdate


    //______________________________________________________________________________________________ checkToken
    public void checkToken() {

        if (PishtazanApp.getApplication(getContext()).getUserId() == 0)
            sendActionToObservable(ObservableActions.gotoLogin);
        else {
            Handler handler = new Handler();
            handler.postDelayed(() -> sendActionToObservable(ObservableActions.gotoHome), 5000);
        }
    }
    //______________________________________________________________________________________________ checkToken


    //______________________________________________________________________________________________ getMd_update
    public MD_Update getMd_update() {
        return md_update;
    }
    //______________________________________________________________________________________________ getMd_update


}
