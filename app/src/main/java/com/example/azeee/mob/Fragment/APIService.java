package com.example.azeee.mob.Fragment;
import com.example.azeee.mob.Notification.MyResponse;
import com.example.azeee.mob.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {

                    "Content-Type:application/json",
                    "Authorization:key=AAAAH5Eyfu4:APA91bFoi6-yBT5xtZGGMLUR3SFNJtcCC-1-aZsE9l7O91uzFvHK9YAuPVGjxlCTysKJn3L_tvhd6rTQ_uYcY8txSKT6r-9UAfb6sPQuDAzzYIzRtDjv-qBar3CfoWrzlJt3kx-_W-Yd"

            }
    )
@POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
