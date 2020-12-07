package ir.bppir.pishtazan.viewmodels;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.moderls.MD_Person;
import ir.bppir.pishtazan.moderls.MR_Person;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.utility.StaticValues;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Panel extends VM_Primary {


    private List<MD_Person> md_personList;


    //______________________________________________________________________________________________ VM_Panel
    public VM_Panel(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Panel



    //______________________________________________________________________________________________ getPerson
    public void getPerson(int panelType, Byte personType, boolean isDeleted) {

        if (panelType == PanelType.customer)
            getAllCustomers(personType, isDeleted);
        else
            getAllColleagues(personType, isDeleted);
    }
    //______________________________________________________________________________________________ getPerson


    //______________________________________________________________________________________________ getAllCustomers
    private void getAllCustomers(Byte personType, boolean isDeleted) {

        Integer userInfoId = getUserId();
        if (userInfoId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .getAllCustomers(userInfoId, personType, isDeleted,"",false));

        getPrimaryCall().enqueue(new Callback<MR_Person>() {
            @Override
            public void onResponse(Call<MR_Person> call, Response<MR_Person> response) {
                if (responseIsOk(response)) {
                    if (response.body().getStatue() == 1) {
                        md_personList = response.body().getCustomers();
                        if (md_personList.size() > 0)
                            setResponseMessage("");
                        else
                            setResponseMessage(getResponseMessages(response.body()));
                        getPublishSubject().onNext(ObservableActions.getPersonList);
                    } else {
                        setResponseMessage(response.body().getMessage());
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Person> call, Throwable t) {
                onFailureRequest();
            }
        });


    }
    //______________________________________________________________________________________________ getAllCustomers


    //______________________________________________________________________________________________ getAllColleagues
    private void getAllColleagues(Byte PersonType, boolean isDeleted) {

        Integer userInfoId = getUserId();
        if (userInfoId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .getAllColleagues(userInfoId, PersonType, isDeleted,"",false));

        getPrimaryCall().enqueue(new Callback<MR_Person>() {
            @Override
            public void onResponse(Call<MR_Person> call, Response<MR_Person> response) {
                if (responseIsOk(response)) {
                    if (response.body().getStatue() == 1) {
                        md_personList = response.body().getColleagues();
                        if (md_personList.size() > 0)
                            setResponseMessage("");
                        else
                            setResponseMessage(getResponseMessages(response.body()));
                        getPublishSubject().onNext(ObservableActions.getPersonList);
                    } else {
                        setResponseMessage(response.body().getMessage());
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_Person> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getAllColleagues



    //______________________________________________________________________________________________ getMd_personList
    public List<MD_Person> getMd_personList() {
        if (md_personList == null)
            md_personList = new ArrayList<>();
        return md_personList;
    }
    //______________________________________________________________________________________________ getMd_personList


}
