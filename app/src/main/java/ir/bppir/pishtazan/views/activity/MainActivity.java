package ir.bppir.pishtazan.views.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.viewmodels.VM_Main;
import ir.bppir.pishtazan.databinding.ActivityMainBinding;
import ir.mlcode.latifiarchitecturelibrary.activity.Activity_Latifi;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_EditText;
import ir.mlcode.latifiarchitecturelibrary.fragments.FR_Latifi;

public class MainActivity extends Activity_Latifi {

    private VM_Main vm_main;
    private boolean preLogin = false;
    private NavController navController;

    private static ML_EditText ml_EditTextTitle;

    ConstraintLayout constraintLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.simpleDraweeViewProfile)
    SimpleDraweeView simpleDraweeViewProfile;

    @BindView(R.id.linearLayoutHeader)
    LinearLayout linearLayoutHeader;

    @BindView(R.id.linearLayoutFooter)
    LinearLayout linearLayoutFooter;

    @BindView(R.id.imageViewMenu)
    ImageView imageViewMenu;

    //______________________________________________________________________________________________ onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm_main = new VM_Main(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(vm_main);
        constraintLayout = findViewById(R.id.constraintLayout);
        ml_EditTextTitle = findViewById(R.id.ml_EditTextTitle);
        FR_Latifi.constraintLayout = constraintLayout;
        ButterKnife.bind(this);
        setListener();
        setProfile();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ setProfile
    private void setProfile() {

        vm_main.getUtility().setRoundImage(simpleDraweeViewProfile, getResources().getColor(R.color.colorPrimary), 3, 30, 30, 0, 0);
    }
    //______________________________________________________________________________________________ setProfile



    //______________________________________________________________________________________________ setListener
    @SuppressLint("RtlHardcoded")
    private void setListener() {

        imageViewMenu.setOnClickListener(v -> {
            drawer_layout.openDrawer(Gravity.RIGHT , true);
        });

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            String fragment = "";
            if (destination.getLabel() != null)
                fragment = destination.getLabel().toString();

            if ((fragment.equalsIgnoreCase("Splash")) ||
                    (fragment.equalsIgnoreCase("Login")) ||
                    (fragment.equalsIgnoreCase("Verify")) ||
                    (fragment.equalsIgnoreCase("AppUpdate"))) {
                if (!preLogin) {
                    linearLayoutHeader.setVisibility(View.GONE);
                    linearLayoutFooter.setVisibility(View.GONE);
                    imageViewMenu.setVisibility(View.GONE);
                    preLogin = true;
                    lockDrawer();
                }

            } else {
                if (preLogin) {
                    linearLayoutHeader.setVisibility(View.VISIBLE);
                    linearLayoutFooter.setVisibility(View.VISIBLE);
                    imageViewMenu.setVisibility(View.VISIBLE);
                    unLockDrawer();
                    preLogin = false;
                }
            }

        });
    }
    //______________________________________________________________________________________________ setListener


    //______________________________________________________________________________________________ unLockDrawer
    public void unLockDrawer() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
    //______________________________________________________________________________________________ unLockDrawer


    //______________________________________________________________________________________________ lockDrawer
    public void lockDrawer() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    //______________________________________________________________________________________________ lockDrawer



    //______________________________________________________________________________________________ showTitle
    public static void showTitle(Context context, String title, Drawable icon) {

        ml_EditTextTitle.setText(title);
        ml_EditTextTitle.setImageIcon(icon);
        ml_EditTextTitle.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ showTitle



    //______________________________________________________________________________________________ hideTitle
    public static void hideTitle() {
        ml_EditTextTitle.setAnimation(null);
        ml_EditTextTitle.setVisibility(View.GONE);
    }
    //______________________________________________________________________________________________ hideTitle


}