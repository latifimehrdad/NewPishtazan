package ir.bppir.pishtazan.views.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.PanelBinding;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.utility.PersonType;
import ir.bppir.pishtazan.viewmodels.VM_Panel;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.adapterts.AP_Person;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;


public class Panel extends Primary implements Primary.fragmentActions {

    private VM_Panel vm_panel;
    public static Byte panelType;
    public static Byte personType;
    private boolean isDeleted = false;

    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;

    @BindView(R.id.recyclerViewPanel)
    RecyclerView recyclerViewPanel;

    @BindView(R.id.ml_ButtonNew)
    ML_Button ml_ButtonNew;

    @BindView(R.id.ml_ButtonUser)
    ML_Button ml_ButtonUser;

    @BindView(R.id.ml_ButtonCertain)
    ML_Button ml_ButtonCertain;

    @BindView(R.id.ml_ButtonPossible)
    ML_Button ml_ButtonPossible;

    @BindView(R.id.ml_ButtonMaybe)
    ML_Button ml_ButtonMaybe;

    @BindView(R.id.textViewNoItemForShow)
    TextView textViewNoItemForShow;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getView() == null) {
            vm_panel = new VM_Panel(getActivity());
            PanelBinding binding = DataBindingUtil.inflate(inflater, R.layout.panel, container, false);
            binding.setPanel(vm_panel);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            setOnClicksAndListener();
            clickOnMaybe();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onCreateView
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(Panel.this, vm_panel);
        setTitle();
    }
    //______________________________________________________________________________________________ onCreateView



    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (action.equals(ObservableActions.getPersonList)){
            setAdapter();
        }
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


    //______________________________________________________________________________________________ setOnClicksAndListener
    private void setOnClicksAndListener() {


        ml_ButtonMaybe.setOnClickListener(v -> clickOnMaybe());

        ml_ButtonUser.setOnClickListener(v -> clickOnUser());

        ml_ButtonPossible.setOnClickListener(v -> clickOnPossible());

        ml_ButtonCertain.setOnClickListener(v -> clickOnCertain());

        recyclerViewPanel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (personType.equals(PersonType.maybe)) {
                    if (dy >= 2)
                        hiddenAddButton();
                        // Scrolling up
                    else if (dy <= -2)
                        showAddButton();
                    // Scrolling down
                }
            }

        });


        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            int downX, upX;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = (int) event.getX();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    upX = (int) event.getX();
                    if (upX - downX > 100) {
                        swipeListRight();
                        // swipe right
                    } else if (downX - upX > -100) {
                        swipeListLeft();
                        // swipe left
                    }
                    return true;

                }
                return false;
            }
        };


        recyclerViewPanel.setOnTouchListener(onTouchListener);
        constraintLayout.setOnTouchListener(onTouchListener);

    }
    //______________________________________________________________________________________________ setOnClicksAndListener


    //______________________________________________________________________________________________ setTitle
    private void setTitle() {
        if (panelType.equals(PanelType.customer)) {
            MainActivity.showTitle(getContext(), getResources().getString(R.string.customer), getResources().getDrawable(R.drawable.ic_group));
            ml_ButtonUser.setVisibility(View.GONE);
        } else {
            MainActivity.showTitle(getContext(), getResources().getString(R.string.colleagues), getResources().getDrawable(R.drawable.ic_hierarchy));
            ml_ButtonUser.setVisibility(View.VISIBLE);
        }
    }
    //______________________________________________________________________________________________ setTitle



    //______________________________________________________________________________________________ resetBackButtonPersonType
    private void resetBackButtonPersonType() {
        ml_ButtonMaybe.setBackground(null);
        ml_ButtonPossible.setBackground(null);
        ml_ButtonCertain.setBackground(null);
        ml_ButtonUser.setBackground(null);
        hiddenAddButton();

        ml_ButtonUser.setTextAndTintColor(getResources().getColor(R.color.colorAccent));
        ml_ButtonMaybe.setTextAndTintColor(getResources().getColor(R.color.colorAccent));
        ml_ButtonPossible.setTextAndTintColor(getResources().getColor(R.color.colorAccent));
        ml_ButtonCertain.setTextAndTintColor(getResources().getColor(R.color.colorAccent));
    }
    //______________________________________________________________________________________________ resetBackButtonPersonType



    //______________________________________________________________________________________________ showAddButton
    private void showAddButton() {
        if (ml_ButtonNew.getVisibility() != View.VISIBLE) {
            ml_ButtonNew.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom));
            ml_ButtonNew.setVisibility(View.VISIBLE);
        }
    }
    //______________________________________________________________________________________________ showAddButton



    //______________________________________________________________________________________________ hiddenAddButton
    private void hiddenAddButton() {
        if (ml_ButtonNew.getVisibility() != View.GONE) {
            ml_ButtonNew.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom));
            ml_ButtonNew.setVisibility(View.GONE);
        }
    }
    //______________________________________________________________________________________________ hiddenAddButton


    //______________________________________________________________________________________________ getPersonList
    private void getPersonList() {
        textViewNoItemForShow.setVisibility(View.GONE);
        recyclerViewPanel.setVisibility(View.VISIBLE);
        setRecyclerLoading(recyclerViewPanel, R.layout.adapter_default_loading);
        vm_panel.getPerson(panelType, personType, isDeleted);
    }
    //______________________________________________________________________________________________ getPersonList



    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {
        stopLoadingRecycler();
        if (vm_panel.getMd_personList().size() > 0){
            AP_Person ap_person = new AP_Person(vm_panel.getMd_personList());
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerViewPanel.setLayoutManager(manager);
            recyclerViewPanel.setAdapter(ap_person);
        } else {
            textViewNoItemForShow.setVisibility(View.VISIBLE);
            recyclerViewPanel.setVisibility(View.GONE);
        }
    }
    //______________________________________________________________________________________________ setAdapter



    //______________________________________________________________________________________________ swipeListLeft
    private void swipeListLeft() {
        if (personType.equals(PersonType.user))
            clickOnCertain();
        else if (personType.equals(PersonType.certain))
            clickOnPossible();
        else if (personType.equals(PersonType.possible))
            clickOnMaybe();
    }
    //______________________________________________________________________________________________ swipeListLeft


    //______________________________________________________________________________________________ swipeListRight
    private void swipeListRight() {

        if (personType.equals(PersonType.maybe))
            clickOnPossible();
        else if (personType.equals(PersonType.possible))
            clickOnCertain();
        else if (personType.equals(PersonType.certain))
            if (panelType.equals(PanelType.colleagues))
                clickOnUser();

    }
    //______________________________________________________________________________________________ swipeListRight


    //______________________________________________________________________________________________ clickOnUser
    private void clickOnUser() {
        resetBackButtonPersonType();
        ml_ButtonUser.stopLoading();
        ml_ButtonUser.setTextAndTintDefaultColor();
        personType = PersonType.user;
        getPersonList();
    }
    //______________________________________________________________________________________________ clickOnUser



    //______________________________________________________________________________________________ clickOnPossible
    private void clickOnPossible() {
        resetBackButtonPersonType();
        ml_ButtonPossible.stopLoading();
        ml_ButtonPossible.setTextAndTintDefaultColor();
        personType = PersonType.possible;
        getPersonList();
    }
    //______________________________________________________________________________________________ clickOnPossible


    //______________________________________________________________________________________________ clickOnCertain
    private void clickOnCertain() {
        resetBackButtonPersonType();
        ml_ButtonCertain.stopLoading();
        ml_ButtonCertain.setTextAndTintDefaultColor();
        personType = PersonType.certain;
        getPersonList();
    }
    //______________________________________________________________________________________________ clickOnCertain



    //______________________________________________________________________________________________ clickOnMaybe
    private void clickOnMaybe() {
        personType = PersonType.maybe;
        resetBackButtonPersonType();
        ml_ButtonMaybe.stopLoading();
        ml_ButtonMaybe.setTextAndTintDefaultColor();
        showAddButton();
        getPersonList();
    }
    //______________________________________________________________________________________________ clickOnMaybe

}
