package ir.bppir.pishtazan.viewmodels;

import ir.bppir.pishtazan.moderls.MR_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.viewmodels.VM_Latifi;

public class VM_Primary extends VM_Latifi {


    //______________________________________________________________________________________________ getResponseMessages
    public String getResponseMessages(MR_Primary mr_primary) {
        StringBuilder result = new StringBuilder();
        if (mr_primary.getMessages() != null && mr_primary.getMessages().size() > 0)
            for (String message : mr_primary.getMessages())
                result.append(message).append(System.getProperty("line.separator"));
        else
            result = new StringBuilder(mr_primary.getMessage());

        return result.toString();
    }
    //______________________________________________________________________________________________ getResponseMessages


    //______________________________________________________________________________________________ getUserId
    public int getUserId() {
        return PishtazanApp.getApplication(getContext()).getUserId();
    }
    //______________________________________________________________________________________________ getUserId



}
