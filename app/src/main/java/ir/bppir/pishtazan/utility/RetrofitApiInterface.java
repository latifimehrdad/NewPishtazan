package ir.bppir.pishtazan.utility;

import ir.bppir.pishtazan.moderls.MD_Update;
import ir.bppir.pishtazan.moderls.MR_Person;
import ir.bppir.pishtazan.moderls.MR_Primary;
import ir.bppir.pishtazan.moderls.MR_VerifyCode;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetrofitApiInterface {

    String Version = "/api";
    String Policy = "Policy.";
    String Reminder = "Reminder.";
    String Customer = "Customer.";



    @GET(Version + "/hi")
    Call<MD_Update> GET_UPDATE();



    @FormUrlEncoded
    @POST(Version + "/GenerateCode")
    Call<MR_Primary> REQUEST_GENERATE_CODE_CALL
            (
                    @Field("MobileNumber") String MobileNumber
            );


    @FormUrlEncoded
    @POST(Version + "/VerifyCode")
    Call<MR_VerifyCode> REQUEST_VERIFY_CODE_CALL
            (
                    @Field("MobileNumber") String MobileNumber,
                    @Field("TokenId") String TokenId,
                    @Field("Code") String Code
            );



    @FormUrlEncoded
    @POST(Version + "/GetAllColleagues")
    Call<MR_Person> getAllColleagues
            (
                    @Field("UserInfoId") Integer userInfoId,
                    @Field("ColleagueStatus") Byte colleagueStatus,
                    @Field("IsDeleted") boolean isDeleted
            );


    @FormUrlEncoded
    @POST(Version + "/GetAllCustomers")
    Call<MR_Person> getAllCustomers
            (
                    @Field("UserInfoId") Integer userInfoId,
                    @Field("CustomerStatus") Byte customerStatus,
                    @Field("IsDeleted") boolean isDeleted
            );

}
