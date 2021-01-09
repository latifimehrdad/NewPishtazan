package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;

import ir.bppir.pishtazan.moderls.MD_Person;
import ir.bppir.pishtazan.moderls.MR_GetAllPerson;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.bppir.pishtazan.views.fragment.Panel;
import ir.mlcode.latifiarchitecturelibrary.utility.StaticValues;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_CompleteInformation extends VM_Primary{


    private String Image;
    private MD_Person md_person;

    //______________________________________________________________________________________________ VM_CompleteInformation
    public VM_CompleteInformation(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_CompleteInformation


    //______________________________________________________________________________________________ getPersonInfo
    public void getPersonInfo(Integer personId) {

        if (Panel.panelType == PanelType.colleagues)
            getColleagueInfo(personId);
        else if (Panel.panelType == PanelType.customer)
            getCustomerInfo(personId);
        else
            getUserInfo(personId);
    }
    //______________________________________________________________________________________________ getPersonInfo



    //______________________________________________________________________________________________ getColleagueInfo
    public void getColleagueInfo(Integer personId) {

        Integer userInfoId = getUserId();
        if (userInfoId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .getColleagueInfo(userInfoId, personId));

        getPrimaryCall().enqueue(new Callback<MR_GetAllPerson>() {
            @Override
            public void onResponse(Call<MR_GetAllPerson> call, Response<MR_GetAllPerson> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_person = response.body().getColleague();
                        notifyChange();
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_GetAllPerson> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getColleagueInfo



    //______________________________________________________________________________________________ getCustomerInfo
    public void getCustomerInfo(Integer personId) {

        Integer userInfoId = getUserId();
        if (userInfoId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .getCustomerInfo(userInfoId, personId));

        getPrimaryCall().enqueue(new Callback<MR_GetAllPerson>() {
            @Override
            public void onResponse(Call<MR_GetAllPerson> call, Response<MR_GetAllPerson> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 0)
                        getPublishSubject().onNext(StaticValues.ML_ResponseError);
                    else {
                        md_person = response.body().getCustomer();
                        notifyChange();
                    }
                }
            }

            @Override
            public void onFailure(Call<MR_GetAllPerson> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getCustomerInfo


    //______________________________________________________________________________________________ getUserInfo
    public void getUserInfo(Integer personId) {


    }
    //______________________________________________________________________________________________ getUserInfo


    //______________________________________________________________________________________________ getMd_person
    public MD_Person getMd_person() {
        return md_person;
    }
    //______________________________________________________________________________________________ getMd_person



    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
