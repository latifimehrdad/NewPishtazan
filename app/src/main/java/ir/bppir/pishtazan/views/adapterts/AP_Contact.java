package ir.bppir.pishtazan.views.adapterts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterContactBinding;
import ir.bppir.pishtazan.databinding.AdapterPersonBinding;
import ir.bppir.pishtazan.moderls.MD_Contact;
import ir.bppir.pishtazan.moderls.MD_Person;
import ir.mlcode.latifiarchitecturelibrary.customs.ML_Button;

public class AP_Contact extends RecyclerView.Adapter<AP_Contact.customHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_Contact> md_contactList;
    private itemActionClick click;


    //______________________________________________________________________________________________ AP_Contact
    public AP_Contact(List<MD_Contact> md_contactList, itemActionClick click) {
        this.md_contactList = md_contactList;
        this.click = click;
    }
    //______________________________________________________________________________________________ AP_Contact



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

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_contact, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder



    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_contactList.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder



    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        return md_contactList.size();
    }
    //______________________________________________________________________________________________ getItemCount




    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder{

        private AdapterContactBinding binding;

        public customHolder(AdapterContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Contact item, final int position) {
            binding.setContact(item);
            binding.getRoot().setOnClickListener(v -> click.actionClick(position));
            binding.executePendingBindings();
        }
    }
    //______________________________________________________________________________________________ customHolder


}
