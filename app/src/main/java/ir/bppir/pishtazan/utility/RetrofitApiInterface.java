package ir.bppir.pishtazan.utility;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import ir.bppir.pishtazan.moderls.MD_Update;
import ir.bppir.pishtazan.moderls.MR_AddCustomer;
import ir.bppir.pishtazan.moderls.MR_GetAllPerson;
import ir.bppir.pishtazan.moderls.MR_Person;
import ir.bppir.pishtazan.moderls.MR_Primary;
import ir.bppir.pishtazan.moderls.MR_VerifyCode;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RetrofitApiInterface {

    String Version = "/api";
    String Policy = "Policy.";
    String Reminder = "Reminder.";
    String Customer = "Customer.";


    @GET(Version + "/hi")
    Call<MD_Update> getUpdate();



    @FormUrlEncoded
    @POST(Version + "/GenerateCode")
    Call<MR_Primary> requestGenerateCodeCall
            (
                    @Field("MobileNumber") String mobileNumber
            );


    @FormUrlEncoded
    @POST(Version + "/VerifyCode")
    Call<MR_VerifyCode> requestVerifyCodeCall
            (
                    @Field("MobileNumber") String mobileNumber,
                    @Field("TokenId") String tokenId,
                    @Field("Code") String code
            );


    //______________________________________________________________________________________________ Customer
    @GET(Version + "/GetAllCustomers")
    Call<MR_Person> getAllCustomers
            (
                    @Query("UserInfoId") Integer userInfoId,
                    @Query("CustomerStatus") Byte customerStatus,
                    @Query("IsDeleted") boolean isDeleted,
                    @Query("FullName") String fullName,
                    @Query("SortByLevel") boolean sortByLevel
            );



    @FormUrlEncoded
    @POST(Version + "/DeleteCustomer")
    Call<MR_Primary> archiveCustomer
            (
                    @Field("Id") Integer id,
                    @Field("UserInfoId") Integer userInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/UnDeleteCustomer")
    Call<MR_Primary> deleteCustomerFromArchive
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/ConvertToPossibleCustomer")
    Call<MR_Primary> moveToPossibleCustomer
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/CreateCustomer")
    Call<MR_AddCustomer> addCustomer
            (
                    @FieldMap Map<String, String> params

            );

    @FormUrlEncoded
    @POST(Version + "/GetCustomerById")
    Call<MR_GetAllPerson> getCustomerInfo
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("Id") Integer Id
            );


    //______________________________________________________________________________________________ Colleagues
    @GET(Version + "/GetAllColleagues")
    Call<MR_Person> getAllColleagues
            (
                    @Query("UserInfoId") Integer userInfoId,
                    @Query("ColleagueStatus") Byte colleagueStatus,
                    @Query("IsDeleted") boolean isDeleted,
                    @Query("FullName") String fullName,
                    @Query("SortByLevel") boolean sortByLevel
            );


    @FormUrlEncoded
    @POST(Version + "/DeleteColleague")
    Call<MR_Primary> archiveColleague
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );

    @FormUrlEncoded
    @POST(Version + "/UnDeleteColleague")
    Call<MR_Primary> deleteColleagueFromArchive
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/ConvertToPossibleColleague")
    Call<MR_Primary> moveToPossibleColleague
            (
                    @Field("Id") Integer Id,
                    @Field("UserInfoId") Integer UserInfoId
            );


    @FormUrlEncoded
    @POST(Version + "/CreateColleague")
    Call<MR_Primary> addColleague
            (
                    @FieldMap Map<String, String> params
            );

    @FormUrlEncoded
    @POST(Version + "/GetColleagueById")
    Call<MR_GetAllPerson> getColleagueInfo
            (
                    @Field("UserInfoId") Integer UserInfoId,
                    @Field("Id") Integer Id
            );




}
