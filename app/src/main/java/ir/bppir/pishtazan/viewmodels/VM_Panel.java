package ir.bppir.pishtazan.viewmodels;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ir.bppir.pishtazan.moderls.MD_Person;
import ir.bppir.pishtazan.moderls.MR_Person;
import ir.bppir.pishtazan.moderls.MR_Primary;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.utility.StaticValues;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.bppir.pishtazan.views.fragment.Panel.panelType;
import static ir.bppir.pishtazan.views.fragment.Panel.personType;

public class VM_Panel extends VM_Primary {


    private List<MD_Person> md_personList;


    //______________________________________________________________________________________________ VM_Panel
    public VM_Panel(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_Panel



    //______________________________________________________________________________________________ getPerson
    public void getPerson(boolean isDeleted, String name, boolean sort) {

        if (panelType == PanelType.customer)
            getAllCustomers(isDeleted,name, sort);
        else
            getAllColleagues(isDeleted,name, sort);
    }
    //______________________________________________________________________________________________ getPerson


    //______________________________________________________________________________________________ getAllCustomers
    private void getAllCustomers(boolean isDeleted, String name, boolean sort) {

        Integer userInfoId = getUserId();
        if (userInfoId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .getAllCustomers(userInfoId, personType, isDeleted,name,sort));

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
    private void getAllColleagues(boolean isDeleted, String name, boolean sort) {

        Integer userInfoId = getUserId();
        if (userInfoId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .getAllColleagues(userInfoId, personType, isDeleted,name,sort));

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



    //______________________________________________________________________________________________ archivePerson
    public void archivePerson(Integer personId){
        if (panelType.equals(PanelType.customer))
            archiveCustomer(personId);
        else if (panelType.equals(PanelType.colleagues))
            archiveColleague(personId);
    }
    //______________________________________________________________________________________________ archivePerson



    //______________________________________________________________________________________________ archiveCustomer
    private void archiveCustomer(Integer personId){

        Integer userId = getUserId();
        if (userId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .archiveCustomer(personId, userId));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.archivePerson);
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
    //______________________________________________________________________________________________ archiveCustomer



    //______________________________________________________________________________________________ archiveColleague
    private void archiveColleague(Integer personId){

        Integer userId = getUserId();
        if (userId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .archiveColleague(personId, userId));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.archivePerson);
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
    //______________________________________________________________________________________________ archiveColleague



    //______________________________________________________________________________________________ deletePersonFromArchive
    public void deletePersonFromArchive(Integer personId) {
        if (panelType.equals(PanelType.customer))
            deleteCustomerFromArchive(personId);
        else if (panelType.equals(PanelType.colleagues))
            deleteColleagueFromArchive(personId);
    }
    //______________________________________________________________________________________________ deletePersonFromArchive




    //______________________________________________________________________________________________ deleteCustomerFromArchive
    private void deleteCustomerFromArchive(Integer personId) {

        Integer userId = getUserId();
        if (userId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .deleteCustomerFromArchive(personId, userId));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.deleteFromArchive);
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
    //______________________________________________________________________________________________ deleteCustomerFromArchive




    //______________________________________________________________________________________________ deleteColleagueFromArchive
    private void deleteColleagueFromArchive(Integer personId) {

        Integer userId = getUserId();
        if (userId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .deleteColleagueFromArchive(personId, userId));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.deleteFromArchive);
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
    //______________________________________________________________________________________________ deleteColleagueFromArchive



    //______________________________________________________________________________________________ deletePersonFromArchive
    public void moveToPossible(Integer personId) {
        if (panelType.equals(PanelType.customer))
            moveToPossibleCustomer(personId);
        else if (panelType.equals(PanelType.colleagues))
            moveToPossibleColleague(personId);
    }
    //______________________________________________________________________________________________ deletePersonFromArchive



    //______________________________________________________________________________________________ moveToPossibleCustomer
    private void moveToPossibleCustomer(Integer personId) {

        Integer userId = getUserId();
        if (userId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .moveToPossibleCustomer(personId, userId));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.moveToPossible);
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
    //______________________________________________________________________________________________ moveToPossibleCustomer



    //______________________________________________________________________________________________ moveToPossibleColleague
    private void moveToPossibleColleague(Integer personId) {

        Integer userId = getUserId();
        if (userId == 0) {
            return;
        }

        setPrimaryCall(PishtazanApp
                .getApplication(getContext())
                .getRetrofitApiInterface()
                .moveToPossibleColleague(personId, userId));

        getPrimaryCall().enqueue(new Callback<MR_Primary>() {
            @Override
            public void onResponse(Call<MR_Primary> call, Response<MR_Primary> response) {
                if (responseIsOk(response)) {
                    setResponseMessage(response.body().getMessage());
                    if (response.body().getStatue() == 1)
                        getPublishSubject().onNext(ObservableActions.moveToPossible);
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
    //______________________________________________________________________________________________ moveToPossibleColleague




    //______________________________________________________________________________________________ getMd_personList
    public List<MD_Person> getMd_personList() {
        if (md_personList == null)
            md_personList = new ArrayList<>();
        return md_personList;
    }
    //______________________________________________________________________________________________ getMd_personList


}
