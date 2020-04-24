package com.golink.ecommerceb2b.Network;

import com.golink.ecommerceb2b.Models.NewData;
import com.golink.ecommerceb2b.Models.Profile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetUserData {

    @FormUrlEncoded
    @POST("orderProducts")
    Call<NewData> getPassbook(@Field("userid") String userid,
                              @Field("usertoken") String usertoken,
                              @Field("order_payment_id") String order_payment_id);

    @FormUrlEncoded
    @POST("vendor_detail")
    Call<Profile> getVendorDetails(@Field("userid") String userid,
                                   @Field("usertoken") String usertoken,
                                   @Field("vendor_id") String vendor_id);

}
