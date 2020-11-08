package ir.bppir.pishtazan.moderls;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MR_VerifyCode extends MR_Primary {

    @SerializedName("UserInfo")
    MD_UserInfo UserInfo;


    public MR_VerifyCode(Integer statue, String message, List<String> messages) {
        super(statue, message, messages);
    }


    public MD_UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(MD_UserInfo userInfo) {
        UserInfo = userInfo;
    }


}
