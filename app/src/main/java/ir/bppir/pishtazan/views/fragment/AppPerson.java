package ir.bppir.pishtazan.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AddPersonBinding;
import ir.bppir.pishtazan.databinding.LoginBinding;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.viewmodels.VM_AddPerson;
import ir.bppir.pishtazan.viewmodels.VM_Login;
import ir.mlcode.latifiarchitecturelibrary.fragments.FR_Latifi;

public class AppPerson extends Primary implements FR_Latifi.fragmentActions{

    private VM_AddPerson vm_addPerson;

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
            setOnClicks();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onCreateView
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(AppPerson.this, vm_addPerson);
        String verified = getVariableFromNavigation(getResources().getString(R.string.ML_Verified));
        if (verified != null)
            removeCallBackAndBack();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {

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

    }
    //______________________________________________________________________________________________ init



    //______________________________________________________________________________________________ setOnClicks
    private void setOnClicks() {

    }
    //______________________________________________________________________________________________ setOnClicks


}
