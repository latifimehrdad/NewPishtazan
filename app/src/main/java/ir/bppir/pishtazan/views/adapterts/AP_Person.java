package ir.bppir.pishtazan.views.adapterts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterPersonBinding;
import ir.bppir.pishtazan.moderls.MD_Person;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;

public class AP_Person extends RecyclerView.Adapter<AP_Person.customHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_Person> md_personList;
    private Context context;
    private itemClick click;


    //______________________________________________________________________________________________ AP_Person
    public AP_Person(List<MD_Person> md_personList, itemClick click) {
        this.md_personList = md_personList;
        this.click = click;
    }
    //______________________________________________________________________________________________ AP_Person



    //______________________________________________________________________________________________ itemClick
    public interface itemClick {

        void actionButtonClick(Integer position);
    }
    //______________________________________________________________________________________________ itemClick


    //______________________________________________________________________________________________ AP_Person
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
            context = parent.getContext();
        }

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_person, parent, false));
    }
    //______________________________________________________________________________________________ AP_Person


    //______________________________________________________________________________________________ AP_Person
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_personList.get(position), position);
    }
    //______________________________________________________________________________________________ AP_Person


    //______________________________________________________________________________________________ AP_Person
    @Override
    public int getItemCount() {
        return md_personList.size();
    }
    //______________________________________________________________________________________________ AP_Person


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterPersonBinding binding;
        View view;

        @BindView(R.id.ml_ButtonAction)
        ML_Button ml_ButtonAction;

        public customHolder(AdapterPersonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_Person item, final int position) {
            binding.setItem(item);
            ml_ButtonAction.setOnClickListener(v -> click.actionButtonClick(position));
            binding.executePendingBindings();
        }

    }
    //______________________________________________________________________________________________ customHolder


}
