package com.example.gio.autostop.server;

import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gio.autostop.AutostopSettings;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UploadPosition extends StringRequest {
    private static final String REQUEST_URL="http://autostop1.comxa.com/upload.php";
    private Map<String,String> params;
    public UploadPosition(Positions position,Response.Listener<String> listener){
       super(Method.POST,REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("mac",position.getMac());
        params.put("android_id",position.getAndroidId());
        params.put("latitude",position.getLatitude()+"");
        params.put("longitude",position.getLongitude()+"");
        params.put("latitudeDestination",position.getLatitudeDestination()+"");
        params.put("longitudeDestination",position.getLongitudeDestination()+"");
        params.put("kindOfUser",position.getIsKindOfUser().toString()+"");
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
