package ir.bppir.pishtazan.views.fragment;

import android.os.Bundle;
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
import ir.bppir.pishtazan.databinding.AddPersonBinding;
import ir.bppir.pishtazan.databinding.LoginBinding;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.utility.PanelType;
import ir.bppir.pishtazan.utility.PersonLevel;
import ir.bppir.pishtazan.viewmodels.VM_AddPerson;
import ir.bppir.pishtazan.viewmodels.VM_Login;
import ir.bppir.pishtazan.views.activity.MainActivity;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_EditText;
import ir.mlcode.latifiarchitecturelibrary.fragments.FR_Latifi;

import static ir.bppir.pishtazan.views.fragment.Panel.panelType;

public class AppPerson extends Primary implements FR_Latifi.fragmentActions {

    private VM_AddPerson vm_addPerson;

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
        setPublishSubjectFromObservable(AppPerson.this, vm_addPerson);
        setTitle();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        ml_ButtonSave.stopLoading();

        if (action.equals(ObservableActions.addPerson)) {
            setVariableToNavigation(getResources().getString(R.string.ML_AddPerson), getResources().getString(R.string.ML_AddPerson));
            removeCallBackAndBack();
        }
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
        ml_ButtonSave.stopLoading();
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


}
