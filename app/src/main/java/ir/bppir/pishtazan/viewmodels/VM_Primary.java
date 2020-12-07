package ir.bppir.pishtazan.viewmodels;

import android.database.Observable;

import org.json.JSONArray;
import org.json.JSONObject;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.moderls.MR_Primary;
import ir.bppir.pishtazan.views.application.PishtazanApp;
import ir.mlcode.latifiarchitecturelibrary.utility.StaticValues;
import ir.mlcode.latifiarchitecturelibrary.viewmodels.VM_Latifi;
import retrofit2.Call;
import retrofit2.Response;

public class VM_Primary extends VM_Latifi {


    //______________________________________________________________________________________________ responseIsOk
    public boolean responseIsOk(Response response) {
        if (response.body() == null) {
            setResponseMessage(responseErrorMessage(response));
            getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
            return false;
        } else
            return true;
    }
    //______________________________________________________________________________________________ responseIsOk



    //______________________________________________________________________________________________ responseErrorMessage
    public String responseErrorMessage(Response response) {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            String jobErrorString = jObjError.toString();
            StringBuilder message = new StringBuilder();
            if (jobErrorString.contains("messages")) {
                JSONArray jsonArray = jObjError.getJSONArray("messages");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = new JSONObject(jsonArray.get(i).toString());
                    message.append(temp.getString("message"));
                    message.append("\n");
                }
            } else {
                message.append(jObjError.getString("Message"));
            }
            return message.toString();
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    //______________________________________________________________________________________________ responseErrorMessage


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
