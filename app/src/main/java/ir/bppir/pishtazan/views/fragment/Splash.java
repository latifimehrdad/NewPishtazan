package ir.bppir.pishtazan.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.SplashBinding;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.viewmodels.VM_Splash;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;


public class Splash extends Primary implements Primary.fragmentActions {


    private VM_Splash vm_splash;

    @BindView(R.id.imageViewSplash)
    ImageView imageViewSplash;

    @BindView(R.id.ml_ButtonRefresh)
    ML_Button ml_ButtonRefresh;

    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getView() == null) {
            vm_splash = new VM_Splash(getActivity());
            SplashBinding binding = DataBindingUtil.inflate(inflater, R.layout.splash, container, false);
            binding.setSplash(vm_splash);
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
        setPublishSubjectFromObservable(Splash.this, vm_splash);
        startAnimationSplash();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (action.equals(ObservableActions.gotoLogin)) {
            getNavController().navigate(R.id.action_splash_to_login);
            return;
        }

        if (action.equals(ObservableActions.gotoHome)) {
            getNavController().navigate(R.id.action_splash_to_home);
            return;
        }

        if (action.equals(ObservableActions.gotoUpdate)) {
            String host  = PishtazanApp.getApplication(getContext()).getHost();
            host = host + vm_splash.getMd_update().getUpdateAddress();
            Bundle bundle = new Bundle();
            bundle.putString(getResources().getString(R.string.ML_ApplicationId), "ir.bppir.pishtazan");
            bundle.putString(getResources().getString(R.string.ML_AppName), getContext().getResources().getString(R.string.app_name));
            bundle.putString(getResources().getString(R.string.ML_UpdateUrl), host);
            bundle.putString(getResources().getString(R.string.ML_UpdateFileName), "NewVersion.apk");
            getNavController().navigate(R.id.action_splash_to_update, bundle);
        }
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
        ml_ButtonRefresh.stopLoading();
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
        ml_ButtonRefresh.setOnClickListener(v -> {
            startAnimationSplash();
        });
    }
    //______________________________________________________________________________________________ setOnClicks


    //______________________________________________________________________________________________ startAnimationSplash
    private void startAnimationSplash() {
        ml_ButtonRefresh.startLoading();
        imageViewSplash.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        vm_splash.getUpdate();
    }
    //______________________________________________________________________________________________ startAnimationSplash


}
