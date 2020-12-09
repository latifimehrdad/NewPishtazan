package ir.bppir.pishtazan.views.adapterts;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterPersonBinding;
import ir.bppir.pishtazan.moderls.MD_Person;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;

public class AP_Person extends RecyclerView.Adapter<AP_Person.customHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_Person> md_personList;
    private itemActionClick click;


    //______________________________________________________________________________________________ AP_Person
    public AP_Person(List<MD_Person> md_personList, itemActionClick click) {
        this.md_personList = md_personList;
        this.click = click;
    }
    //______________________________________________________________________________________________ AP_Person


    //______________________________________________________________________________________________ itemActionClick
    public interface itemActionClick{

        void actionClick(Integer position);
    }
    //______________________________________________________________________________________________ itemActionClick


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_person, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder




    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_personList.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder



    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        return md_personList.size();
    }
    //______________________________________________________________________________________________ getItemCount



    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder{

        private AdapterPersonBinding binding;

        @BindView(R.id.ml_ButtonAction)
        ML_Button ml_ButtonAction;

        public customHolder(AdapterPersonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Person item, final int position) {
            binding.setItem(item);
            ml_ButtonAction.setOnClickListener(v -> click.actionClick(position));
            binding.executePendingBindings();
        }
    }
    //______________________________________________________________________________________________ customHolder


}
