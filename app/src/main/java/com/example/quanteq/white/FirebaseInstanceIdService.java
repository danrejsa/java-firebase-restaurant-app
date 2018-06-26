package com.example.quanteq.white;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Quanteq on 6/6/2018.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {
    public static final String TAG = "Notice";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token" + token);
    }
}
