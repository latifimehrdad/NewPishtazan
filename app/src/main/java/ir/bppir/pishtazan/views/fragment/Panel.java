package ir.bppir.pishtazan.views.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.PanelBinding;
import ir.bppir.pishtazan.moderls.MD_PanelActionMenu;
import ir.bppir.pishtazan.moderls.MD_Person;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.utility.PersonType;
import ir.bppir.pishtazan.viewmodels.VM_Panel;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.adapterts.AP_PanelActionMenu;
import ir.bppir.pishtazan.views.adapterts.AP_Person;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;


public class Panel extends Primary implements Primary.fragmentActions, AP_Person.itemActionClick,
        AP_PanelActionMenu.menuActionClick {

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

    @BindView(R.id.constraintLayoutAction)
    ConstraintLayout constraintLayoutAction;

    @BindView(R.id.recyclerViewActions)
    RecyclerView recyclerViewActions;

    @BindView(R.id.ml_ButtonClose)
    ML_Button ml_ButtonClose;

    @BindView(R.id.expandableLayoutSearch)
    ExpandableLayout expandableLayoutSearch;

    @BindView(R.id.ml_ButtonShowSearch)
    ML_Button ml_ButtonShowSearch;


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
            init();
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

        if (action.equals(ObservableActions.getPersonList)) {
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


    //______________________________________________________________________________________________ init
    @Override
    public void init() {

        setOnClicksAndListener();
        clickOnMaybe();

    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ setOnClicksAndListener
    private void setOnClicksAndListener() {

        constraintLayoutAction.setOnClickListener(v -> closeLayoutAction());

        recyclerViewActions.setOnClickListener(v -> closeLayoutAction());

        ml_ButtonClose.setOnClickListener(v -> closeLayoutAction());

        ml_ButtonMaybe.setOnClickListener(v -> clickOnMaybe());

        ml_ButtonUser.setOnClickListener(v -> clickOnUser());

        ml_ButtonPossible.setOnClickListener(v -> clickOnPossible());

        ml_ButtonCertain.setOnClickListener(v -> clickOnCertain());

        recyclerViewPanel.addOnScrollListener(onScrollListenerUpDown());

        ml_ButtonShowSearch.setOnClickListener(v -> showSearchLayout());


        recyclerViewPanel.setOnTouchListener(onTouchListenerSwipe());
        constraintLayout.setOnTouchListener(onTouchListenerSwipe());

    }
    //______________________________________________________________________________________________ setOnClicksAndListener


    //______________________________________________________________________________________________ onTouchListenerSwipe
    private View.OnTouchListener onTouchListenerSwipe() {

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {

            int downX, upX;
            int downY, upY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    upX = (int) event.getX();
                    upY = (int) event.getY();

                    if (Math.abs(downY - upY) > 90)
                        return false;


                    if (upX == downX)
                        return false;

                    if (Math.abs(upX - downX) < 120)
                        return false;

                    if (upX - downX > 130) {
                        swipeListRight();
                        // swipe right
                    } else if (downX - upX > -130) {
                        swipeListLeft();
                        // swipe left
                    }
                    return true;

                }
                return false;
            }
        };

        return onTouchListener;

    }
    //______________________________________________________________________________________________ onTouchListenerSwipe


    //______________________________________________________________________________________________ onScrollListenerUpDown
    private RecyclerView.OnScrollListener onScrollListenerUpDown() {

        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy >= 2) {
                    closeLayoutAction();
                    if (personType.equals(PersonType.maybe))
                        hiddenAddButton();
                } else if (dy <= -2) {
                    if (personType.equals(PersonType.maybe))
                        showAddButton();
                }
            }

        };
    }
    //______________________________________________________________________________________________ onScrollListenerUpDown


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


    //______________________________________________________________________________________________ showSearchLayout
    private void showSearchLayout() {
        if (expandableLayoutSearch.isExpanded())
            expandableLayoutSearch.collapse();
        else
            expandableLayoutSearch.expand();
    }
    //______________________________________________________________________________________________ showSearchLayout


    //______________________________________________________________________________________________ getPersonList
    private void getPersonList() {
        textViewNoItemForShow.setVisibility(View.GONE);
        recyclerViewPanel.setVisibility(View.VISIBLE);
        closeLayoutAction();
        setRecyclerLoading(recyclerViewPanel, R.layout.adapter_loading_person);
        vm_panel.getPerson(panelType, personType, isDeleted);
    }
    //______________________________________________________________________________________________ getPersonList


    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {
        stopLoadingRecycler();
        if (vm_panel.getMd_personList().size() > 0) {
            AP_Person ap_person = new AP_Person(vm_panel.getMd_personList(), Panel.this);
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


    //______________________________________________________________________________________________ actionClick
    @Override
    public void actionClick(Integer position) {
        if (panelType.equals(PanelType.customer))
            customerAction(vm_panel.getMd_personList().get(position));
        else if (panelType.equals(PanelType.colleagues))
            colleaguesAction(vm_panel.getMd_personList().get(position));
    }
    //______________________________________________________________________________________________ actionClick


    //______________________________________________________________________________________________ customerAction
    private void customerAction(MD_Person person) {

        if (personType.equals(PersonType.maybe))
            createHomeActionMenu(customerActionMaybe(person));
        else if (personType.equals(PersonType.possible))
            createHomeActionMenu(customerActionPossible(person));
        else if (personType.equals(PersonType.certain))
            createHomeActionMenu(customerActionCertain(person));
    }
    //______________________________________________________________________________________________ customerAction


    //______________________________________________________________________________________________ colleaguesAction
    private void colleaguesAction(MD_Person person) {

        if (personType.equals(PersonType.maybe))
            createHomeActionMenu(colleaguesActionMaybe(person));
        else if (personType.equals(PersonType.possible))
            createHomeActionMenu(colleaguesActionPossible(person));
        else if (personType.equals(PersonType.certain))
            createHomeActionMenu(colleaguesActionCertain(person));
        else if (personType.equals(PersonType.user))
            createHomeActionMenu(colleaguesActionUser(person));
    }
    //______________________________________________________________________________________________ colleaguesAction


    //______________________________________________________________________________________________ customerActionMaybe
    private List<MD_PanelActionMenu> customerActionMaybe(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();
        menus.add(actionMoveToPossible(person));
        menus.add(actionDeletePerson(person));

        return menus;

    }
    //______________________________________________________________________________________________ customerActionMaybe


    //______________________________________________________________________________________________ customerActionPossible
    private List<MD_PanelActionMenu> customerActionPossible(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();

        menus.add(actionCompleteInformation(person));
        menus.add(actionCallsReminder(person));
        menus.add(actionMeetingsReminder(person));
        menus.add(actionMoveToCustomerCertain(person));
        menus.add(actionDeletePerson(person));

        return menus;
    }
    //______________________________________________________________________________________________ customerActionPossible


    //______________________________________________________________________________________________ customerActionCertain
    private List<MD_PanelActionMenu> customerActionCertain(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();

        menus.add(actionCompleteInformation(person));
        menus.add(actionCallsReminder(person));
        menus.add(actionMeetingsReminder(person));
        menus.add(actionDrafts(person));
        menus.add(actionInsurances(person));
        menus.add(actionDeletePerson(person));

        return menus;
    }
    //______________________________________________________________________________________________ customerActionCertain


    //______________________________________________________________________________________________ colleaguesActionMaybe
    private List<MD_PanelActionMenu> colleaguesActionMaybe(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();
        menus.add(actionMoveToPossible(person));
        menus.add(actionDeletePerson(person));

        return menus;

    }
    //______________________________________________________________________________________________ colleaguesActionMaybe


    //______________________________________________________________________________________________ colleaguesActionPossible
    private List<MD_PanelActionMenu> colleaguesActionPossible(MD_Person person) {
        List<MD_PanelActionMenu> menus = new ArrayList<>();
        menus.add(actionCompleteInformation(person));
        menus.add(actionCallsReminder(person));
        menus.add(actionMeetingsReminder(person));
        menus.add(actionMoveToColleagueCertain(person));
        menus.add(actionDeletePerson(person));

        return menus;
    }
    //______________________________________________________________________________________________ colleaguesActionPossible


    //______________________________________________________________________________________________ colleaguesActionCertain
    private List<MD_PanelActionMenu> colleaguesActionCertain(MD_Person person) {
        List<MD_PanelActionMenu> menus = new ArrayList<>();
        menus.add(actionCompleteInformation(person));
        menus.add(actionCallsReminder(person));
        menus.add(actionMeetingsReminder(person));
        menus.add(actionDrafts(person));
        menus.add(actionInsurances(person));
        menus.add(actionDeletePerson(person));

        return menus;
    }
    //______________________________________________________________________________________________ colleaguesActionCertain


    //______________________________________________________________________________________________ colleaguesActionUser
    private List<MD_PanelActionMenu> colleaguesActionUser(MD_Person person) {
        List<MD_PanelActionMenu> menus = new ArrayList<>();
        menus.add(actionCallsReminder(person));
        menus.add(actionMeetingsReminder(person));
        menus.add(actionDeletePerson(person));

        return menus;
    }
    //______________________________________________________________________________________________ colleaguesActionUser


    //______________________________________________________________________________________________ actionDeletePerson
    private MD_PanelActionMenu actionDeletePerson(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.archive),
                getResources().getDrawable(R.drawable.ic_archive_user),
                getResources().getDrawable(R.drawable.dw_back_panel_menu_delete),
                getResources().getColor(R.color.ML_White),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionDeletePerson


    //______________________________________________________________________________________________ actionMoveToPossible
    private MD_PanelActionMenu actionMoveToPossible(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionMoveToPossible),
                getResources().getDrawable(R.drawable.ic_resource_switch),
                getResources().getDrawable(R.drawable.dw_back_panel_menu_move),
                getResources().getColor(R.color.ML_White),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionMoveToPossible


    //______________________________________________________________________________________________ actionCompleteInformation
    private MD_PanelActionMenu actionCompleteInformation(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionCompleteInformation),
                getResources().getDrawable(R.drawable.ic_contact_information),
                getResources().getDrawable(R.drawable.dw_back_panel_menu),
                getResources().getColor(R.color.colorPrimary),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionCompleteInformation


    //______________________________________________________________________________________________ actionCallsReminder
    private MD_PanelActionMenu actionCallsReminder(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionCallsReminder),
                getResources().getDrawable(R.drawable.ic_call_reminder),
                getResources().getDrawable(R.drawable.dw_back_panel_menu),
                getResources().getColor(R.color.colorPrimary),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionCallsReminder


    //______________________________________________________________________________________________ actionMeetingsReminder
    private MD_PanelActionMenu actionMeetingsReminder(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionMeetingsReminder),
                getResources().getDrawable(R.drawable.ic_meeting_reminder),
                getResources().getDrawable(R.drawable.dw_back_panel_menu),
                getResources().getColor(R.color.colorPrimary),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionMeetingsReminder


    //______________________________________________________________________________________________ actionMoveToCustomerCertain
    private MD_PanelActionMenu actionMoveToCustomerCertain(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionMoveToCustomerCertain),
                getResources().getDrawable(R.drawable.ic_resource_switch),
                getResources().getDrawable(R.drawable.dw_back_panel_menu_move),
                getResources().getColor(R.color.ML_White),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionMoveToCustomerCertain


    //______________________________________________________________________________________________ actionMoveToCustomerCertain
    private MD_PanelActionMenu actionMoveToColleagueCertain(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionMoveToColleagueCertain),
                getResources().getDrawable(R.drawable.ic_resource_switch),
                getResources().getDrawable(R.drawable.dw_back_panel_menu_move),
                getResources().getColor(R.color.ML_White),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionMoveToCustomerCertain


    //______________________________________________________________________________________________ actionDrafts
    private MD_PanelActionMenu actionDrafts(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionDrafts),
                getResources().getDrawable(R.drawable.ic_draft),
                getResources().getDrawable(R.drawable.dw_back_panel_menu),
                getResources().getColor(R.color.colorPrimary),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionDrafts


    //______________________________________________________________________________________________ actionInsurances
    private MD_PanelActionMenu actionInsurances(MD_Person person) {

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionInsurances),
                getResources().getDrawable(R.drawable.ic_family),
                getResources().getDrawable(R.drawable.dw_back_panel_menu),
                getResources().getColor(R.color.colorPrimary),
                R.id.action_home_to_panel,
                null);
    }
    //______________________________________________________________________________________________ actionInsurances


    //______________________________________________________________________________________________ createHomeActionMenu
    private void createHomeActionMenu(List<MD_PanelActionMenu> menus) {
        AP_PanelActionMenu ap_homeActionMenu = new AP_PanelActionMenu(menus, Panel.this);
        recyclerViewActions.setAdapter(ap_homeActionMenu);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewActions.setLayoutManager(manager);
        openLayoutAction();
    }
    //______________________________________________________________________________________________ createHomeActionMenu


    //______________________________________________________________________________________________ closeLayoutAction
    private void closeLayoutAction() {

        expandableLayoutSearch.collapse();

        if (constraintLayoutAction.getVisibility() != View.GONE) {
            constraintLayoutAction.setAnimation(null);
            constraintLayoutAction.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom));
            constraintLayoutAction.setVisibility(View.GONE);
        }
    }
    //______________________________________________________________________________________________ closeLayoutAction


    //______________________________________________________________________________________________ openLayoutAction
    private void openLayoutAction() {

        expandableLayoutSearch.collapse();

        if (constraintLayoutAction.getVisibility() == View.GONE) {
            constraintLayoutAction.setAnimation(null);
            constraintLayoutAction.setVisibility(View.GONE);
            constraintLayoutAction.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom));
            constraintLayoutAction.setVisibility(View.VISIBLE);
        } else {
            closeLayoutAction();
            Handler handler = new Handler();
            handler.postDelayed(() -> openLayoutAction(), 300);
        }
//            gotoFragment(R.id.action_home_to_workVacation, null);
    }
    //______________________________________________________________________________________________ openLayoutAction


    //______________________________________________________________________________________________ itemClick
    @Override
    public void itemClick(int action, Bundle bundle) {

    }
    //______________________________________________________________________________________________ itemClick

}
