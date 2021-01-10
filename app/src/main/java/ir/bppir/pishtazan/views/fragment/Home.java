package ir.bppir.pishtazan.views.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.HomeBinding;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.viewmodels.VM_Home;
import ir.bppir.pishtazan.views.activity.MainActivity;

public class Home extends Primary implements Primary.fragmentActions {


    private VM_Home vm_home;
    private boolean doubleExitApplication = false;

    @BindView(R.id.linearLayoutCustomer)
    LinearLayout linearLayoutCustomer;

    @BindView(R.id.linearLayoutColleagues)
    LinearLayout linearLayoutColleagues;

    @BindView(R.id.linearLayoutTutorial)
    LinearLayout linearLayoutTutorial;

    @BindView(R.id.linearLayoutReports)
    LinearLayout linearLayoutReports;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getView() == null) {
            vm_home = new VM_Home(getActivity());
            HomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.home, container, false);
            binding.setHome(vm_home);
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
        setPublishSubjectFromObservable(Home.this, vm_home);
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
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void OnBackPress() {

        if (doubleExitApplication)
            System.exit(1);
        else {
            getView().setAlpha(0.1f);
            doubleExitApplication = true;
            showToast(
                    getResources().getString(R.string.doubleExit),
                    getResources().getColor(R.color.dayColorAccent),
                    getResources().getDrawable(R.drawable.ic_exit),
                    getResources().getColor(R.color.dayColorAccent));
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                doubleExitApplication = false;
                getView().setAlpha(1);
            }, 4000);
        }

    }
    //______________________________________________________________________________________________ OnBackPress




    //______________________________________________________________________________________________ init
    @Override
    public void init() {

    }
    //______________________________________________________________________________________________ init



    //______________________________________________________________________________________________ setOnClicks
    private void setOnClicks() {

        linearLayoutCustomer.setOnClickListener(v -> {
            Panel.panelType = PanelType.customer;
            getNavController().navigate(R.id.action_home_to_panel);
        });

        linearLayoutColleagues.setOnClickListener(v -> {
            Panel.panelType = PanelType.colleagues;
            getNavController().navigate(R.id.action_home_to_panel);
        });

        linearLayoutTutorial.setOnClickListener(v -> {

        });

        linearLayoutReports.setOnClickListener(v -> {

        });
    }
    //______________________________________________________________________________________________ setOnClicks



    //______________________________________________________________________________________________ setTitle
    private void setTitle() {
        MainActivity.showTitle(getContext(), getResources().getString(R.string.home), getResources().getDrawable(R.drawable.ic_quarantine));
    }
    //______________________________________________________________________________________________ setTitle



}
