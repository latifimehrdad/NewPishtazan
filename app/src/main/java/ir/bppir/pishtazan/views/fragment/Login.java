package ir.bppir.pishtazan.views.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.LoginBinding;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.viewmodels.VM_Login;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_EditText;

public class Login extends Primary implements Primary.fragmentActions {


    private VM_Login vm_login;

    @BindView(R.id.ml_ButtonLogin)
    ML_Button ml_ButtonLogin;

    @BindView(R.id.ml_EditTextPersonalCode)
    ML_EditText ml_EditTextPersonalCode;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getView() == null) {
            vm_login = new VM_Login(getActivity());
            LoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.login, container, false);
            binding.setLogin(vm_login);
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
        setPublishSubjectFromObservable(Login.this, vm_login);
        String verified = getVariableFromNavigation(getResources().getString(R.string.ML_Verified));
        if (verified != null)
            removeCallBackAndBack();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {
        ml_ButtonLogin.stopLoading();
        if (action.equals(ObservableActions.gotoVerify)) {
            Bundle bundle = new Bundle();
            bundle.putString(getResources().getString(R.string.ML_NationalCode), vm_login.getNationalCode());
            getNavController().navigate(R.id.action_login_to_verify, bundle);
        }
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
        ml_ButtonLogin.stopLoading();
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


    //______________________________________________________________________________________________ OnBackPress
    @Override
    public void OnBackPress() {

    }
    //______________________________________________________________________________________________ OnBackPress



    //______________________________________________________________________________________________ init
    @Override
    public void init() {

    }
    //______________________________________________________________________________________________ init




    //______________________________________________________________________________________________ setOnClicks
    private void setOnClicks() {

        ml_ButtonLogin.setOnClickListener(v -> {
            if (ml_ButtonLogin.isClick())
                vm_login.cancelRequestByUser();
            else {
                if (ml_EditTextPersonalCode.checkValidation()) {
                    ml_ButtonLogin.startLoading();
                    vm_login.sendNumber();
                } else {
                    ml_EditTextPersonalCode.setErrorLayout(getResources().getString(R.string.enterNationalCode));
                }
            }
        });
    }
    //______________________________________________________________________________________________ setOnClicks



}