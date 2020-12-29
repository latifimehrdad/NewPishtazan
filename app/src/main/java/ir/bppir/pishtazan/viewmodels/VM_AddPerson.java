package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import ir.bppir.pishtazan.moderls.MR_AddCustomer;
import ir.bppir.pishtazan.moderls.MR_Primary;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.bppir.pishtazan.views.fragment.Panel;
import ir.mlcode.latifiarchitecturelibrary.utility.StaticValues;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_AddPerson extends VM_Primary {

    private String fullName;
    private String mobileNumber;
    private Byte degree;


    //______________________________________________________________________________________________ VM_AddPerson
    public VM_AddPerson(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_AddPerson


    //______________________________________________________________________________________________ addPerson
    public void addPerson() {
        if (Panel.panelType.equals(PanelType.colleagues))
            addColleague();
        else if (Panel.panelType.equals(PanelType.customer))
            addCustomer();
    }
    //______________________________________________________________________________________________ addPerson



    //______________________________________________________________________________________________ addCustomer
    private void addCustomer() {

        setMobileNumber(getUtility().persianToEnglish(getMobileNumber()));

        Integer colleagueId = getColleagueId();
        if (colleagueId == 0) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("FullName", getFullName());
        params.put("ColleagueId", colleagueId.toString());
        params.put("MobileNumber", getMobileNumber());
        params.put("Level", getDegree().toString());


        setPrimaryCall(PishtazanApp.getApplication(getContext())
                .getRetrofitApiInterface()
                .addCustomer(params));

        getPrimaryCall().enqueue(new Callback<MR_AddCustomer>() {
            @Override
            public void onResponse(Call<MR_AddCustomer> call, Response<MR_AddCustomer> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.addPerson);
                    else
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                }
            }

            @Override
            public void onFailure(Call<MR_AddCustomer> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ addCustomer


    //______________________________________________________________________________________________ addColleague
    private void addColleague() {

        setMobileNumber(getUtility().persianToEnglish(getMobileNumber()));

        Integer userInfoId = getUserId();
        if (userInfoId == 0) {
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("FullName", getFullName());
        params.put("UserInfoId", userInfoId.toString());
        params.put("MobileNumber", getMobileNumber());
        params.put("Level", getDegree().toString());


        setPrimaryCall(PishtazanApp.getApplication(getContext())
                .getRetrofitApiInterface()
                .addColleague(params));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.addPerson);
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
    //______________________________________________________________________________________________ addColleague




    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Byte getDegree() {
        return degree;
    }

    public void setDegree(Byte degree) {
        this.degree = degree;
    }
}
