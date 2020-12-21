package ir.bppir.pishtazan.views.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.VerifyBinding;
import ir.bppir.pishtazan.utility.ObservableActions;
import ir.bppir.pishtazan.viewmodels.VM_Verify;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;

public class verify extends Primary implements Primary.fragmentActions {


    private VM_Verify vm_verify;

    private Handler timer;
    private Handler timerLoading;
    private Runnable runnableLoading;
    private int index = 1;


    @BindView(R.id.VerifyCode1)
    EditText VerifyCode1;

    @BindView(R.id.VerifyCode2)
    EditText VerifyCode2;

    @BindView(R.id.VerifyCode3)
    EditText VerifyCode3;

    @BindView(R.id.VerifyCode4)
    EditText VerifyCode4;

    @BindView(R.id.VerifyCode5)
    EditText VerifyCode5;

    @BindView(R.id.VerifyCode6)
    EditText VerifyCode6;

    @BindView(R.id.progressBarElapse)
    ProgressBar progressBarElapse;

    @BindView(R.id.textViewElapseTime)
    TextView textViewElapseTime;

    @BindView(R.id.textViewElapseMessage)
    TextView textViewElapseMessage;

    @BindView(R.id.ml_ButtonReTry)
    ML_Button ml_ButtonReTry;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getView() == null) {
            vm_verify = new VM_Verify(getActivity());
            VerifyBinding binding = DataBindingUtil.inflate(inflater, R.layout.verify, container, false);
            binding.setVerify(vm_verify);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            setTextChangeListener();
            reTryGetSMS();
            startTimer(120);
            setClick();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onCreateView
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(verify.this, vm_verify);
        assert getArguments() != null;
        vm_verify.setNationalCode(getArguments().getString(getResources().getString(R.string.ML_NationalCode), ""));
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        ml_ButtonReTry.stopLoading();
        stopLoading();

        if (action.equals(ObservableActions.gotoVerify)) {
            startTimer(120);
            return;
        }

        if (action.equals(ObservableActions.gotoHome)) {
            String verified = getResources().getString(R.string.ML_Verified);
            setVariableToNavigation(verified, verified);
            removeCallBackAndBack();
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
        ml_ButtonReTry.stopLoading();
        stopLoading();
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



    //______________________________________________________________________________________________ setTextChangeListener
    private void setTextChangeListener() {

        VerifyCode1.addTextChangedListener(TextChange(VerifyCode2));
        VerifyCode2.addTextChangedListener(TextChange(VerifyCode3));
        VerifyCode3.addTextChangedListener(TextChange(VerifyCode4));
        VerifyCode4.addTextChangedListener(TextChange(VerifyCode5));
        VerifyCode5.addTextChangedListener(TextChange(VerifyCode6));
        VerifyCode6.addTextChangedListener(TextChange(VerifyCode6));

        VerifyCode1.setOnKeyListener(setKeyBackSpace(VerifyCode1));
        VerifyCode2.setOnKeyListener(setKeyBackSpace(VerifyCode1));
        VerifyCode3.setOnKeyListener(setKeyBackSpace(VerifyCode2));
        VerifyCode4.setOnKeyListener(setKeyBackSpace(VerifyCode3));
        VerifyCode5.setOnKeyListener(setKeyBackSpace(VerifyCode4));
        VerifyCode6.setOnKeyListener(setKeyBackSpace(VerifyCode5));


    }
    //______________________________________________________________________________________________ setTextChangeListener


    //______________________________________________________________________________________________ textChange
    private TextWatcher TextChange(final EditText eNext) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    eNext.requestFocus();

                }
                SetBackVerifyCode();
            }
        };

    }
    //______________________________________________________________________________________________ textChange


    //______________________________________________________________________________________________ setKeyBackSpace
    private View.OnKeyListener setKeyBackSpace(final EditText view) {
        return (v, keyCode, event) -> {

            EditText edit = (EditText) v;
            if (keyCode == 67) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
                if (edit.getText().length() == 0) {
                    view.setText("");
                    view.requestFocus();
                    SetBackVerifyCode();
                    return true;
                } else
                    return false;
            }
            return false;
        };
    }
    //______________________________________________________________________________________________ setKeyBackSpace


    //______________________________________________________________________________________________ setBackVerifyCode
    private void SetBackVerifyCode() {

        Boolean c1 = setBackVerifyCodeView(VerifyCode1);
        Boolean c2 = setBackVerifyCodeView(VerifyCode2);
        Boolean c3 = setBackVerifyCodeView(VerifyCode3);
        Boolean c4 = setBackVerifyCodeView(VerifyCode4);
        Boolean c5 = setBackVerifyCodeView(VerifyCode5);
        Boolean c6 = setBackVerifyCodeView(VerifyCode6);

        if (c1 && c2 && c3 && c4 && c5 && c6) {
            String code = VerifyCode1.getText().toString() +
                    VerifyCode2.getText().toString() +
                    VerifyCode3.getText().toString() +
                    VerifyCode4.getText().toString() +
                    VerifyCode5.getText().toString() +
                    VerifyCode6.getText().toString();

            hideKeyboard();
            editTextLoading(code);
        }

    }
    //______________________________________________________________________________________________ SetBackVerifyCode


    //______________________________________________________________________________________________ setBackVerifyCodeView
    private Boolean setBackVerifyCodeView(EditText editText) {

        boolean ret = false;
        if (editText.getText().length() != 0)
            ret = true;
        return ret;

    }
    //______________________________________________________________________________________________ setBackVerifyCodeView


    //______________________________________________________________________________________________ startTimer
    private void startTimer(int Elapse) {

        textViewElapseTime.setVisibility(View.VISIBLE);
        textViewElapseMessage.setVisibility(View.VISIBLE);
         ml_ButtonReTry.setVisibility(View.GONE);

        Elapse = Elapse * 10;
        progressBarElapse.setMax(Elapse * 2);
        progressBarElapse.setProgress(Elapse);
        timer = new Handler();
        Runnable runnable = new Runnable() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void run() {
                progressBarElapse.setProgress(progressBarElapse.getProgress() - 1);
                int mili = progressBarElapse.getProgress() + 10;
                int seconds = (mili / 10) % 60;
                int minutes = (mili / (10 * 60)) % 60;
                textViewElapseTime.setText(String.format("%02d", minutes) + " : " + String.format("%02d", seconds));

                if (progressBarElapse.getProgress() > 0)
                    timer.postDelayed(this, 100);
                else
                    reTryGetSMS();
            }
        };
        timer.postDelayed(runnable, 100);

    }
    //______________________________________________________________________________________________ startTimer


    //______________________________________________________________________________________________ reTryGetSMS
    private void reTryGetSMS() {
        textViewElapseTime.setVisibility(View.GONE);
        textViewElapseMessage.setVisibility(View.GONE);
        ml_ButtonReTry.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ reTryGetSMS


    //______________________________________________________________________________________________ setClick
    private void setClick() {

        ml_ButtonReTry.setOnClickListener(v -> {
            if (ml_ButtonReTry.isClick())
                vm_verify.cancelRequestByUser();
            else {
                ml_ButtonReTry.startLoading();
                vm_verify.sendNationalCode();
            }
        });

    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ editTextLoading
    @SuppressLint("UseCompatLoadingForDrawables")
    private void editTextLoading(String code) {
        index = 1;
        VerifyCode1.setBackground(getResources().getDrawable(R.drawable.dw_edit_loading));
        timerLoading = new Handler();
        runnableLoading = () -> {
            switch (index) {
                case 1:
                    VerifyCode1.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
                    VerifyCode2.setBackground(getResources().getDrawable(R.drawable.dw_edit_loading));
                    index = 2;
                    break;

                case 2:
                    VerifyCode2.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
                    VerifyCode3.setBackground(getResources().getDrawable(R.drawable.dw_edit_loading));
                    index = 3;
                    break;

                case 3:
                    VerifyCode3.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
                    VerifyCode4.setBackground(getResources().getDrawable(R.drawable.dw_edit_loading));
                    index = 4;
                    break;

                case 4:
                    VerifyCode4.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
                    VerifyCode5.setBackground(getResources().getDrawable(R.drawable.dw_edit_loading));
                    index = 5;
                    break;

                case 5:
                    VerifyCode5.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
                    VerifyCode6.setBackground(getResources().getDrawable(R.drawable.dw_edit_loading));
                    index = 6;
                    break;

                case 6:
                    VerifyCode6.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
                    VerifyCode1.setBackground(getResources().getDrawable(R.drawable.dw_edit_loading));
                    index = 1;
                    break;
            }
            timerLoading.postDelayed(runnableLoading, 80);
        };
        timerLoading.postDelayed(runnableLoading, 100);

        vm_verify.setCode(code);
        vm_verify.verifyNationalCode();
    }
    //______________________________________________________________________________________________ editTextLoading


    //______________________________________________________________________________________________ stopLoading
    @SuppressLint("UseCompatLoadingForDrawables")
    private void stopLoading() {

        VerifyCode1.requestFocus();

        VerifyCode1.getText().clear();
        VerifyCode2.getText().clear();
        VerifyCode3.getText().clear();
        VerifyCode4.getText().clear();
        VerifyCode5.getText().clear();
        VerifyCode6.getText().clear();

        VerifyCode1.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
        VerifyCode2.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
        VerifyCode3.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
        VerifyCode4.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
        VerifyCode5.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
        VerifyCode6.setBackground(getResources().getDrawable(R.drawable.dw_back_edit));
        if (timerLoading != null && runnableLoading != null)
            timerLoading.removeCallbacks(runnableLoading);
    }
    //______________________________________________________________________________________________ stopLoading

}