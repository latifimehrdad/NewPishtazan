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
import ir.bppir.pishtazan.databinding.CompleteInformationBinding;
import ir.bppir.pishtazan.viewmodels.VM_CompleteInformation;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.mlcode.latifiarchitecturelibrary.fragments.FR_Latifi;


public class CompleteInformation extends Primary implements FR_Latifi.fragmentActions {

    private VM_CompleteInformation vm_completeInformation;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getView() == null) {
            vm_completeInformation = new VM_CompleteInformation(getActivity());
            CompleteInformationBinding binding = DataBindingUtil.inflate(inflater, R.layout.complete_information, container, false);
            binding.setInformation(vm_completeInformation);
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
        setPublishSubjectFromObservable(CompleteInformation.this, vm_completeInformation);
        setTitle();
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


    //______________________________________________________________________________________________ setTitle
    private void setTitle() {

        MainActivity.showTitle(getContext(), getResources().getString(R.string.completeInformation), getResources().getDrawable(R.drawable.ic_contact_information));
    }
    //______________________________________________________________________________________________ setTitle


}
