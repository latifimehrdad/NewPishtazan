package ir.bppir.pishtazan.viewmodels;

import android.app.Activity;

public class VM_CompleteInformation extends VM_Primary{


    private String Image;

    //______________________________________________________________________________________________ VM_CompleteInformation
    public VM_CompleteInformation(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_CompleteInformation


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
