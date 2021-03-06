package ir.bppir.pishtazan.moderls;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_AddCustomer extends MR_Primary{

    @SerializedName("CustomerId")
    private int CustomerId;


    public MR_AddCustomer(Integer statue, String message, List<String> messages, int customerId) {
        super(statue, message, messages);
        CustomerId = customerId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }
}
