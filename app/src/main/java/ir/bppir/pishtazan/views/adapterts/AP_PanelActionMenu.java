package ir.bppir.pishtazan.views.adapterts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterPanelActionMenuBinding;
import ir.bppir.pishtazan.moderls.MD_PanelActionMenu;


public class AP_PanelActionMenu extends RecyclerView.Adapter<AP_PanelActionMenu.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_PanelActionMenu> md_homeActionMenus;
    private menuActionClick actionClick;



    public interface menuActionClick {
        void itemClick(int action, Bundle bundle);
    }


    //______________________________________________________________________________________________ AP_HomeActionMenu
    public AP_PanelActionMenu(List<MD_PanelActionMenu> md_homeActionMenus, menuActionClick actionClick) {
        this.md_homeActionMenus = md_homeActionMenus;
        this.actionClick = actionClick;
    }
    //______________________________________________________________________________________________ AP_HomeActionMenu




    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_panel_action_menu, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_homeActionMenus.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        return md_homeActionMenus.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterPanelActionMenuBinding binding;
        View view;

        @BindView(R.id.constraintLayout)
        ConstraintLayout constraintLayout;

        public customHolder(AdapterPanelActionMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_PanelActionMenu item, int position) {
            binding.setMenu(item);
            constraintLayout.setBackground(item.getBackground());
            view.setOnClickListener(v -> actionClick.itemClick(item.getAction(), item.getBundle()));
            binding.executePendingBindings();
        }
    }
    //______________________________________________________________________________________________ customHolder


}
