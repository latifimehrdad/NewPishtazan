package ir.bppir.pishtazan.views.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.switchmaterial.SwitchMaterial;

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
import ir.bppir.pishtazan.utility.PanelAction;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.utility.PersonType;
import ir.bppir.pishtazan.viewmodels.VM_Panel;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.bppir.pishtazan.views.adapterts.AP_PanelActionMenu;
import ir.bppir.pishtazan.views.adapterts.AP_Person;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_EditText;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Toast;


public class Panel extends Primary implements Primary.fragmentActions, AP_Person.itemActionClick,
        AP_PanelActionMenu.menuActionClick {

    private VM_Panel vm_panel;
    public static Byte panelType;
    public static Byte personType;
    private Dialog dialog;
    private String addNewPersonMessage;

    @BindView(R.id.constraintLayoutPanel)
    ConstraintLayout constraintLayoutPanel;

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

    @BindView(R.id.ml_EditTextName)
    ML_EditText ml_EditTextName;

    @BindView(R.id.switchMaterialArchive)
    SwitchMaterial switchMaterialArchive;

    @BindView(R.id.switchMaterialSort)
    SwitchMaterial switchMaterialSort;

    @BindView(R.id.ml_ButtonSearch)
    ML_Button ml_ButtonSearch;


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
        checkReturnFromTheAddPerson();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        dismissDialog();
        if (action.equals(ObservableActions.getPersonList)) {
            if (addNewPersonMessage != null) {
                showToast(addNewPersonMessage
                        , getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.svg_checked),
                        getResources().getColor(R.color.mlWave4));
            }
            addNewPersonMessage = null;
            setAdapter();
            return;
        }

        if (action.equals(ObservableActions.archivePerson) ||
                action.equals(ObservableActions.deleteFromArchive) ||
                action.equals(ObservableActions.moveToPossible)) {
            getPersonList();
            return;
        }


    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {

        dismissDialog();
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



    //______________________________________________________________________________________________ checkReturnFromTheAddPerson
    private void checkReturnFromTheAddPerson() {

        addNewPersonMessage = getVariableFromNavigation(getResources().getString(R.string.ML_AddPerson));
        if (addNewPersonMessage != null) {
            clickOnMaybe();
        }
    }
    //______________________________________________________________________________________________ checkReturnFromTheAddPerson


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

        constraintLayoutPanel.setOnTouchListener(onTouchListenerSwipe());

        switchMaterialArchive.setOnClickListener(v -> getPersonList());

        switchMaterialSort.setOnClickListener(v -> getPersonList());

        ml_ButtonSearch.setOnClickListener(v -> getPersonList());

        ml_ButtonNew.setOnClickListener(v -> goToAddPerson());

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

        dismissDialog();
        ml_ButtonMaybe.setBackground(null);
        ml_ButtonPossible.setBackground(null);
        ml_ButtonCertain.setBackground(null);
        ml_ButtonUser.setBackground(null);
        hiddenAddButton();
        resetSearchPerson();

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


    //______________________________________________________________________________________________ resetSearchPerson
    private void resetSearchPerson() {
        switchMaterialArchive.setChecked(false);
        switchMaterialSort.setChecked(false);
        ml_EditTextName.setText(null);
    }
    //______________________________________________________________________________________________ resetSearchPerson


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
        ML_Toast.hide(constraintLayout);
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
        vm_panel.getPerson(switchMaterialArchive.isChecked(), ml_EditTextName.getText().toString(), switchMaterialSort.isChecked());
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
        if (switchMaterialArchive.isChecked()) {
            menus.add(actionDeleteFromArchivePerson(person));
        } else {
            menus.add(actionMoveToPossible(person));
            menus.add(actionDeletePerson(person));
        }

        return menus;

    }
    //______________________________________________________________________________________________ customerActionMaybe


    //______________________________________________________________________________________________ customerActionPossible
    private List<MD_PanelActionMenu> customerActionPossible(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();
        if (switchMaterialArchive.isChecked()) {
            menus.add(actionDeleteFromArchivePerson(person));
        } else {
            menus.add(actionCompleteInformation(person));
            menus.add(actionCallsReminder(person));
            menus.add(actionMeetingsReminder(person));
            menus.add(actionMoveToCustomerCertain(person));
            menus.add(actionDeletePerson(person));
        }
        return menus;
    }
    //______________________________________________________________________________________________ customerActionPossible


    //______________________________________________________________________________________________ customerActionCertain
    private List<MD_PanelActionMenu> customerActionCertain(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();
        if (switchMaterialArchive.isChecked()) {
            menus.add(actionDeleteFromArchivePerson(person));
        } else {
            menus.add(actionCompleteInformation(person));
            menus.add(actionCallsReminder(person));
            menus.add(actionMeetingsReminder(person));
            menus.add(actionDrafts(person));
            menus.add(actionInsurances(person));
        }
        return menus;
    }
    //______________________________________________________________________________________________ customerActionCertain


    //______________________________________________________________________________________________ colleaguesActionMaybe
    private List<MD_PanelActionMenu> colleaguesActionMaybe(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();
        if (switchMaterialArchive.isChecked()) {
            menus.add(actionDeleteFromArchivePerson(person));
        } else {
            menus.add(actionMoveToPossible(person));
            menus.add(actionDeletePerson(person));
        }
        return menus;

    }
    //______________________________________________________________________________________________ colleaguesActionMaybe


    //______________________________________________________________________________________________ colleaguesActionPossible
    private List<MD_PanelActionMenu> colleaguesActionPossible(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();
        if (switchMaterialArchive.isChecked()) {
            menus.add(actionDeleteFromArchivePerson(person));
        } else {
            menus.add(actionCompleteInformation(person));
            menus.add(actionCallsReminder(person));
            menus.add(actionMeetingsReminder(person));
            menus.add(actionMoveToColleagueCertain(person));
            menus.add(actionDeletePerson(person));
        }
        return menus;
    }
    //______________________________________________________________________________________________ colleaguesActionPossible


    //______________________________________________________________________________________________ colleaguesActionCertain
    private List<MD_PanelActionMenu> colleaguesActionCertain(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();
        if (switchMaterialArchive.isChecked()) {
            menus.add(actionDeleteFromArchivePerson(person));
        } else {
            menus.add(actionCompleteInformation(person));
            menus.add(actionCallsReminder(person));
            menus.add(actionMeetingsReminder(person));
            menus.add(actionDrafts(person));
            menus.add(actionInsurances(person));
        }
        return menus;
    }
    //______________________________________________________________________________________________ colleaguesActionCertain


    //______________________________________________________________________________________________ colleaguesActionUser
    private List<MD_PanelActionMenu> colleaguesActionUser(MD_Person person) {

        List<MD_PanelActionMenu> menus = new ArrayList<>();
        if (switchMaterialArchive.isChecked()) {
            menus.add(actionDeleteFromArchivePerson(person));
        } else {
            menus.add(actionCallsReminder(person));
            menus.add(actionMeetingsReminder(person));
        }
        return menus;
    }
    //______________________________________________________________________________________________ colleaguesActionUser


    //______________________________________________________________________________________________ actionDeletePerson
    private MD_PanelActionMenu actionDeletePerson(MD_Person person) {

        Bundle bundle = new Bundle();
        bundle.putString(getContext().getString(R.string.ML_FullName), person.getFullName());
        bundle.putInt(getContext().getString(R.string.ML_PersonId), person.getId());

        return new MD_PanelActionMenu(
                getResources().getString(R.string.archive),
                getResources().getDrawable(R.drawable.ic_archive_user),
                getResources().getDrawable(R.drawable.dw_back_panel_menu_delete),
                getResources().getColor(R.color.ML_White),
                PanelAction.deletePerson,
                bundle,
                false);
    }
    //______________________________________________________________________________________________ actionDeletePerson


    //______________________________________________________________________________________________ actionDeleteFromArchivePerson
    private MD_PanelActionMenu actionDeleteFromArchivePerson(MD_Person person) {

        Bundle bundle = new Bundle();
        bundle.putString(getContext().getString(R.string.ML_FullName), person.getFullName());
        bundle.putInt(getContext().getString(R.string.ML_PersonId), person.getId());

        return new MD_PanelActionMenu(
                getResources().getString(R.string.deleteFromArchive),
                getResources().getDrawable(R.drawable.ic_list_person),
                getResources().getDrawable(R.drawable.dw_back_panel_menu_move),
                getResources().getColor(R.color.ML_White),
                PanelAction.deleteFromArchive,
                bundle,
                false);
    }
    //______________________________________________________________________________________________ actionDeleteFromArchivePerson


    //______________________________________________________________________________________________ actionMoveToPossible
    private MD_PanelActionMenu actionMoveToPossible(MD_Person person) {

        Bundle bundle = new Bundle();
        bundle.putString(getContext().getString(R.string.ML_FullName), person.getFullName());
        bundle.putInt(getContext().getString(R.string.ML_PersonId), person.getId());

        return new MD_PanelActionMenu(
                getResources().getString(R.string.actionMoveToPossible),
                getResources().getDrawable(R.drawable.ic_resource_switch),
                getResources().getDrawable(R.drawable.dw_back_panel_menu_move),
                getResources().getColor(R.color.ML_White),
                PanelAction.moveToPossible,
                bundle,
                false);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
    public void itemClick(MD_PanelActionMenu md_panelActionMenu) {

        dismissDialog();
        closeLayoutAction();

        if (md_panelActionMenu.isGoFragment())
            goToFragmentWhenClickAction(md_panelActionMenu);
        else
            gotoFunctionWhenClickAction(md_panelActionMenu);
    }
    //______________________________________________________________________________________________ itemClick


    //______________________________________________________________________________________________ goToFragmentWhenClickAction
    private void goToFragmentWhenClickAction(MD_PanelActionMenu md_panelActionMenu) {

    }
    //______________________________________________________________________________________________ goToFragmentWhenClickAction


    //______________________________________________________________________________________________ gotoFunctionWhenClickAction
    private void gotoFunctionWhenClickAction(MD_PanelActionMenu md_panelActionMenu) {

        if (md_panelActionMenu.getAction() == PanelAction.deletePerson)
            deletePerson(md_panelActionMenu);
        else if (md_panelActionMenu.getAction() == PanelAction.deleteFromArchive)
            deletePersonOfArchive(md_panelActionMenu);
        else if (md_panelActionMenu.getAction() == PanelAction.moveToPossible)
            movePersonToPossible(md_panelActionMenu);
    }
    //______________________________________________________________________________________________ gotoFunctionWhenClickAction


    //______________________________________________________________________________________________ deletePerson
    private void deletePerson(MD_PanelActionMenu md_panelActionMenu) {

        dismissDialog();
        dialog = createDialog(R.layout.dialog_delete_person);

        ImageView imageViewIcon = dialog.findViewById(R.id.imageViewIcon);
        configImageView(imageViewIcon,
                getResources().getDrawable(R.drawable.ic_archive_user),
                getResources().getColor(R.color.ML_Red));

        TextView textViewTitle = dialog.findViewById(R.id.textViewTitle);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getContext().getString(R.string.titleDialogDeletePerson));
        stringBuilder.append(" ");
        stringBuilder.append(md_panelActionMenu.getBundle().getString(getContext().getString(R.string.ML_FullName)));
        stringBuilder.append(" ");
        stringBuilder.append(getContext().getString(R.string.titleDialogAreYouShore));
        textViewTitle.setText(stringBuilder.toString());

        ML_Button buttonNo = dialog.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(v -> dialog.dismiss());

        ML_Button buttonYes = dialog.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(v -> {
            if (buttonYes.isClick())
                vm_panel.cancelRequestByUser();
            else {
                buttonYes.startLoading();
                vm_panel.archivePerson(md_panelActionMenu.getBundle().getInt(getContext().getString(R.string.ML_PersonId)));
            }
        });

        dialog.show();

    }
    //______________________________________________________________________________________________ deletePerson


    //______________________________________________________________________________________________ deletePersonOfArchive
    private void deletePersonOfArchive(MD_PanelActionMenu md_panelActionMenu) {
        dismissDialog();
        dialog = createDialog(R.layout.dialog_delete_person);

        ImageView imageViewIcon = dialog.findViewById(R.id.imageViewIcon);
        configImageView(imageViewIcon,
                getResources().getDrawable(R.drawable.ic_archive_user),
                getResources().getColor(R.color.ML_Red));

        TextView textViewTitle = dialog.findViewById(R.id.textViewTitle);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getContext().getString(R.string.titleDialogDeletePersonOFArchive));
        stringBuilder.append(" ");
        stringBuilder.append(md_panelActionMenu.getBundle().getString(getContext().getString(R.string.ML_FullName)));
        stringBuilder.append(" ");
        stringBuilder.append(getContext().getString(R.string.titleDialogAreYouShore));
        textViewTitle.setText(stringBuilder.toString());

        ML_Button buttonNo = dialog.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(v -> dialog.dismiss());

        ML_Button buttonYes = dialog.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(v -> {
            if (buttonYes.isClick())
                vm_panel.cancelRequestByUser();
            else {
                buttonYes.startLoading();
                vm_panel.deletePersonFromArchive(md_panelActionMenu.getBundle().getInt(getContext().getString(R.string.ML_PersonId)));
            }
        });

        dialog.show();
    }
    //______________________________________________________________________________________________ deletePersonOfArchive


    //______________________________________________________________________________________________ movePersonToPossible
    private void movePersonToPossible(MD_PanelActionMenu md_panelActionMenu) {
        dismissDialog();
        dialog = createDialog(R.layout.dialog_delete_person);

        ImageView imageViewIcon = dialog.findViewById(R.id.imageViewIcon);
        configImageView(imageViewIcon,
                getResources().getDrawable(R.drawable.ic_archive_user),
                getResources().getColor(R.color.ML_Red));

        TextView textViewTitle = dialog.findViewById(R.id.textViewTitle);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getContext().getString(R.string.titleDialogMovePerson));
        stringBuilder.append(" ");
        stringBuilder.append(md_panelActionMenu.getBundle().getString(getContext().getString(R.string.ML_FullName)));
        stringBuilder.append(" ");
        stringBuilder.append(getContext().getString(R.string.titleDialogAreYouShore));
        textViewTitle.setText(stringBuilder.toString());

        ML_Button buttonNo = dialog.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(v -> dialog.dismiss());

        ML_Button buttonYes = dialog.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(v -> {
            if (buttonYes.isClick())
                vm_panel.cancelRequestByUser();
            else {
                buttonYes.startLoading();
                vm_panel.moveToPossible(md_panelActionMenu.getBundle().getInt(getContext().getString(R.string.ML_PersonId)));
            }
        });

        dialog.show();
    }
    //______________________________________________________________________________________________ movePersonToPossible


    //______________________________________________________________________________________________ dismissDialog
    private void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
    }
    //______________________________________________________________________________________________ dismissDialog



    //______________________________________________________________________________________________ goToAddPerson
    private void goToAddPerson() {
        ML_Toast.hide(constraintLayout);
        getNavController().navigate(R.id.action_panel_to_addPerson);
    }
    //______________________________________________________________________________________________ goToAddPerson

}
