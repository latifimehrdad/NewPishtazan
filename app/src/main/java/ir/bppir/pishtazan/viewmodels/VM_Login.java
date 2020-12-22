package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;

import ir.bppir.pishtazan.moderls.MR_Primary;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.utility.StaticValues;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VM_Login extends VM_Primary {

    private String nationalCode;

    //______________________________________________________________________________________________ VM_SignUp
    public VM_Login(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_SignUp


    //______________________________________________________________________________________________ sendNumber
    public void sendNumber() {

        nationalCode = getUtility().persianToEnglish(nationalCode);

        setPrimaryCall(PishtazanApp.getApplication(getContext())
                .getRetrofitApiInterface()
                .requestGenerateCodeCall(nationalCode));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(getResponseMessages(response.body()));
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.gotoVerify);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ sendNumber


    //______________________________________________________________________________________________ getPhoneNumber
    public String getNationalCode() {
        return nationalCode;
    }
    //______________________________________________________________________________________________ getPhoneNumber


    //______________________________________________________________________________________________ setPhoneNumber
    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
    //______________________________________________________________________________________________ setPhoneNumber


}
