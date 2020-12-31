package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.moderls.MD_Contact;
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
    private List<MD_Contact> md_contacts;
    private List<MD_Contact> md_contactsFilter;


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


    //______________________________________________________________________________________________ getContactList
    public void getContactList() {

        new Thread(() -> {
            if (md_contacts == null) {
                md_contacts = new ArrayList<>();
                ContentResolver cr = getContext().getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                if ((cur != null ? cur.getCount() : 0) > 0) {
                    while (cur.moveToNext()) {
                        String id = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));
                        if (cur.getInt(cur.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                            Cursor pCur = cr.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                String phoneNo = pCur.getString(pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                                phoneNo = phoneNo.replaceAll("-", "");
                                String temp = phoneNo.substring(0, 3);
                                if (temp.equals("+98")) {
                                    phoneNo = "0" + phoneNo.substring(3);
                                }
                                if (getUtility().getValidations().mobileValidation(phoneNo))
                                    md_contacts.add(new MD_Contact(name, phoneNo));
                            }
                            pCur.close();
                        }
                    }

                }
                if (cur != null) {
                    cur.close();
                }
            }

            if (md_contacts != null && md_contacts.size() > 0) {
                setResponseMessage("");
                getPublishSubject().onNext(ObservableActions.getContact);
            } else {
                setResponseMessage(getContext().getResources().getString(R.string.contactIsEmpty));
                getPublishSubject().onNext(StaticValues.ML_ResponseError);
            }

        }).start();
    }
    //______________________________________________________________________________________________ getContactList


    //______________________________________________________________________________________________ getContactWithFilter
    public void getContactWithFilter(String text) {

        if (text == null || text.length() == 0) {
            setResponseMessage("");
            getPublishSubject().onNext(ObservableActions.getContact);
        } else {

            text = getUtility()
                    .persianToEnglish(text);

            if (md_contactsFilter == null)
                md_contactsFilter = new ArrayList<>();
            else
                md_contactsFilter.clear();

            for (MD_Contact contact : md_contacts) {
                String name = contact.getName();
                String Phone = contact.getPhone();
                if (name.toLowerCase().contains(text.toLowerCase()))
                    md_contactsFilter.add(contact);
                else if (Phone.toLowerCase().contains(text.toLowerCase()))
                    md_contactsFilter.add(contact);
            }
            setResponseMessage("");
            getPublishSubject().onNext(ObservableActions.getContactWithFilter);
        }

    }
    //______________________________________________________________________________________________ getContactWithFilter


    //______________________________________________________________________________________________ getMd_contactsFilter
    public List<MD_Contact> getMd_contactsFilter() {
        return md_contactsFilter;
    }
    //______________________________________________________________________________________________ getMd_contactsFilter



    //______________________________________________________________________________________________ getMd_contacts
    public List<MD_Contact> getMd_contacts() {
        return md_contacts;
    }
    //______________________________________________________________________________________________ getMd_contacts




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
