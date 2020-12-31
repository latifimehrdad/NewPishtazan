package ir.bppir.pishtazan.views.fragment;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AddPersonBinding;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.utility.PersonLevel;
import ir.bppir.pishtazan.viewmodels.VM_AddPerson;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.adapterts.AP_Contact;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_EditText;
import ir.mlcode.latifiarchitecturelibrary.fragments.FR_Latifi;
import ir.mlcode.latifiarchitecturelibrary.utility.StaticValues;

import static ir.bppir.pishtazan.views.fragment.Panel.panelType;

public class AddPerson extends Primary implements FR_Latifi.fragmentActions,
        AP_Contact.itemActionClick {

    private VM_AddPerson vm_addPerson;
    private Dialog contactDialog;
    private CompositeDisposable contactDisposable = new CompositeDisposable();


    RecyclerView recyclerViewContact;

    @BindView(R.id.ml_ButtonContact)
    ML_Button ml_ButtonContact;

    @BindView(R.id.ml_EditTextName)
    ML_EditText ml_EditTextName;

    @BindView(R.id.ml_EditTextMobile)
    ML_EditText ml_EditTextMobile;

    @BindView(R.id.linearLayoutNormal)
    LinearLayout linearLayoutNormal;

    @BindView(R.id.linearLayoutPeach)
    LinearLayout linearLayoutPeach;

    @BindView(R.id.linearLayoutGiant)
    LinearLayout linearLayoutGiant;

    @BindView(R.id.ml_ButtonSave)
    ML_Button ml_ButtonSave;

    @BindView(R.id.ml_ButtonCancel)
    ML_Button ml_ButtonCancel;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getView() == null) {
            vm_addPerson = new VM_AddPerson(getActivity());
            AddPersonBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_person, container, false);
            binding.setAddPerson(vm_addPerson);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            init();

        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onCreateView
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(AddPerson.this, vm_addPerson);
        setTitle();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        ml_ButtonSave.stopLoading();
        ml_ButtonContact.stopLoading();

        if (action.equals(ObservableActions.addPerson)) {
            setVariableToNavigation(getResources().getString(R.string.ML_AddPerson), vm_addPerson.getResponseMessage());
            removeCallBackAndBack();
            return;
        }

        if (action.equals(StaticValues.ML_CheckPermission)) {
            ml_ButtonContact.startLoading();
            vm_addPerson.getContactList();
            return;
        }

        if (action.equals(ObservableActions.getContact)) {
            showContactDialog();
            return;
        }

        if (action.equals(ObservableActions.getContactWithFilter)) {
            setContactAdapter(true);
        }
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
        ml_ButtonSave.stopLoading();
        ml_ButtonContact.stopLoading();
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


    //______________________________________________________________________________________________ OnBackPress
    @Override
    public void OnBackPress() {
        removeCallBackAndBack();
    }
    //______________________________________________________________________________________________ OnBackPress


    //______________________________________________________________________________________________ init
    @Override
    public void init() {

        resetBackDegrees();
        setOnClicks();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ setTitle
    private void setTitle() {
        if (panelType.equals(PanelType.customer)) {
            MainActivity.showTitle(getContext(), getResources().getString(R.string.addCustomer), getResources().getDrawable(R.drawable.ic_group));
        } else {
            MainActivity.showTitle(getContext(), getResources().getString(R.string.addColleague), getResources().getDrawable(R.drawable.ic_hierarchy));
        }
    }
    //______________________________________________________________________________________________ setTitle


    //______________________________________________________________________________________________ setOnClicks
    private void setOnClicks() {

        linearLayoutNormal.setOnClickListener(v -> chooseNormalDegree());
        linearLayoutPeach.setOnClickListener(v -> choosePeachDegree());
        linearLayoutGiant.setOnClickListener(v -> chooseGiantDegree());
        ml_ButtonCancel.setOnClickListener(v -> removeCallBackAndBack());
        ml_ButtonSave.setOnClickListener(v -> addPerson());
        ml_ButtonContact.setOnClickListener(v -> getContactList());
    }
    //______________________________________________________________________________________________ setOnClicks


    //______________________________________________________________________________________________ resetBackDegrees
    private void resetBackDegrees() {
        linearLayoutGiant.setBackground(getResources().getDrawable(R.drawable.dw_back_recycler));
        linearLayoutPeach.setBackground(getResources().getDrawable(R.drawable.dw_back_recycler));
        linearLayoutNormal.setBackground(getResources().getDrawable(R.drawable.dw_back_recycler));
        vm_addPerson.setDegree(PersonLevel.none);
    }
    //______________________________________________________________________________________________ resetBackDegrees


    //______________________________________________________________________________________________ chooseNormalDegree
    private void chooseNormalDegree() {

        resetBackDegrees();
        vm_addPerson.setDegree(PersonLevel.normal);
        linearLayoutNormal.setBackground(getResources().getDrawable(R.drawable.dw_back_choose_degree));
    }
    //______________________________________________________________________________________________ chooseNormalDegree


    //______________________________________________________________________________________________ choosePeachDegree
    private void choosePeachDegree() {

        resetBackDegrees();
        vm_addPerson.setDegree(PersonLevel.peach);
        linearLayoutPeach.setBackground(getResources().getDrawable(R.drawable.dw_back_choose_degree));
    }
    //______________________________________________________________________________________________ choosePeachDegree


    //______________________________________________________________________________________________ chooseGiantDegree
    private void chooseGiantDegree() {

        resetBackDegrees();
        vm_addPerson.setDegree(PersonLevel.giant);
        linearLayoutGiant.setBackground(getResources().getDrawable(R.drawable.dw_back_choose_degree));
    }
    //______________________________________________________________________________________________ chooseGiantDegree


    //______________________________________________________________________________________________ addPerson
    private void addPerson() {

        hideKeyboard();
        if (ml_ButtonSave.isClick())
            vm_addPerson.cancelRequestByUser();
        else {

            if (!ml_EditTextName.checkValidation()) {
                ml_EditTextName.setErrorLayout(getResources().getString(R.string.emptyFullName));
                return;
            }

            if (!ml_EditTextMobile.checkValidation()) {
                ml_EditTextMobile.setErrorLayout(getResources().getString(R.string.emptyMobileNumber));
                return;
            }

            if (vm_addPerson.getDegree().equals(PersonLevel.none)) {
                if (panelType.equals(PanelType.colleagues))
                    showMessageDialog(
                            getResources().getString(R.string.emptyColleagueDegree),
                            getResources().getColor(R.color.mlDelimiter),
                            getResources().getDrawable(R.drawable.svg_warning),
                            getResources().getColor(R.color.ML_Harmony));
                else
                    showMessageDialog(
                            getResources().getString(R.string.emptyCustomerDegree),
                            getResources().getColor(R.color.mlDelimiter),
                            getResources().getDrawable(R.drawable.svg_warning),
                            getResources().getColor(R.color.ML_Harmony));

                return;
            }

            ml_ButtonSave.startLoading();
            vm_addPerson.addPerson();

        }
    }
    //______________________________________________________________________________________________ addPerson


    //______________________________________________________________________________________________ getContactList
    private void getContactList() {
        if (!ml_ButtonContact.isClick()) {
            List<String> list = new ArrayList<>();
            list.add(Manifest.permission.READ_CONTACTS);
            setPermission(list);
        }
    }
    //______________________________________________________________________________________________ getContactList


    //______________________________________________________________________________________________ showContactDialog
    private void showContactDialog() {
        dismissDialog();
        contactDialog = createDialog(R.layout.dialog_contact);

        ML_Button ml_ButtonCancel = contactDialog.findViewById(R.id.ml_ButtonCancel);
        ml_ButtonCancel.setOnClickListener(v -> dismissDialog());

        recyclerViewContact = contactDialog.findViewById(R.id.recyclerViewContact);

        setContactAdapter(false);

        ML_EditText ml_EditTextSearch = contactDialog.findViewById(R.id.ml_EditTextSearch);
        contactDisposable.add(RxTextView.textChangeEvents(ml_EditTextSearch.getEditText())
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher()));


        contactDialog.show();

    }
    //______________________________________________________________________________________________ showContactDialog


    //______________________________________________________________________________________________ searchContactsTextWatcher
    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                hideKeyboard();
                String text = textViewTextChangeEvent.text().toString();
                vm_addPerson.getContactWithFilter(text);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }
    //______________________________________________________________________________________________ searchContactsTextWatcher


    //______________________________________________________________________________________________ dismissDialog
    private void dismissDialog() {
        if (contactDialog != null)
            contactDialog.dismiss();
        contactDialog = null;
    }
    //______________________________________________________________________________________________ dismissDialog


    //______________________________________________________________________________________________ setContactAdapter
    private void setContactAdapter(boolean filter) {
        recyclerViewContact.setAdapter(null);
        AP_Contact ap_contact;
        if (filter)
            ap_contact = new AP_Contact(vm_addPerson.getMd_contactsFilter(), AddPerson.this);
        else
            ap_contact = new AP_Contact(vm_addPerson.getMd_contacts(), AddPerson.this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewContact.setLayoutManager(manager);
        recyclerViewContact.setAdapter(ap_contact);

    }
    //______________________________________________________________________________________________ setContactAdapter


    //______________________________________________________________________________________________ actionClick
    @Override
    public void actionClick(Integer position) {

        ml_EditTextName.setText(vm_addPerson.getMd_contactsFilter().get(position).getName());
        ml_EditTextMobile.setText(vm_addPerson.getMd_contactsFilter().get(position).getPhone());
        dismissDialog();
    }
    //______________________________________________________________________________________________ actionClick


}
