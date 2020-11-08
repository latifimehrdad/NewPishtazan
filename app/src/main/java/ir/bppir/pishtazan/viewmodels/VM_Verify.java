package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;
import android.os.Handler;

import ir.bppir.pishtazan.moderls.MD_UserInfo;
import ir.bppir.pishtazan.moderls.MR_Primary;
import ir.bppir.pishtazan.moderls.MR_VerifyCode;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.utility.StaticValues;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Verify extends VM_Primary {

    private String nationalCode;
    private String code;
    private MD_UserInfo md_userInfo;

    //______________________________________________________________________________________________ VM_Verify
    public VM_Verify(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Verify


    //______________________________________________________________________________________________ sendNationalCode
    public void sendNationalCode() {

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .REQUEST_GENERATE_CODE_CALL(nationalCode));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessages(response.body()));
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.gotoVerify);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                } else
                    getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_Primary> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ sendNationalCode


    //______________________________________________________________________________________________ verifyNationalCode
    public void verifyNationalCode() {

        setPrimaryCall(PishtazanApp.getApplication(getContext())
                .getRetrofitApiInterface()
                .REQUEST_VERIFY_CODE_CALL(nationalCode, "null", code));

        getPrimaryCall().enqueue(new Callback<MR_VerifyCode>() {
            @Override
            public void onResponse(Call<MR_VerifyCode> call, Response<MR_VerifyCode> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    setResponseMessage(getResponseMessages(response.body()));
                    if (response.body().getStatue() == 1) {
                        saveUserInfo(response.body().getUserInfo());
                    } else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_VerifyCode> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ verifyNationalCode



    //______________________________________________________________________________________________ saveUserInfo
    private void saveUserInfo(MD_UserInfo md_userInfo) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (PishtazanApp.getApplication(getContext()).saveProfile(md_userInfo))
                getPublishSubject().onNext(ObservableActions.gotoHome);
        }, 2000);
    }
    //______________________________________________________________________________________________ saveUserInfo


    //______________________________________________________________________________________________ getMd_userInfo
    public MD_UserInfo getMd_userInfo() {
        return md_userInfo;
    }
    //______________________________________________________________________________________________ getMd_userInfo



    //______________________________________________________________________________________________ getNationalCode
    public String getNationalCode() {
        return nationalCode;
    }
    //______________________________________________________________________________________________ getNationalCode


    //______________________________________________________________________________________________ setNationalCode
    public void setNationalCode(String nationalCode) {
        nationalCode = getUtility().persianToEnglish(nationalCode);
        this.nationalCode = nationalCode;
    }
    //______________________________________________________________________________________________ setNationalCode


    //______________________________________________________________________________________________ getCode
    public String getCode() {
        return code;
    }
    //______________________________________________________________________________________________ getCode


    //______________________________________________________________________________________________ setCode
    public void setCode(String code) {
        code = getUtility().persianToEnglish(code);
        this.code = code;
    }
    //______________________________________________________________________________________________ setCode


}