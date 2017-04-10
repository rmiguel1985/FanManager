package com.adictosalainformatica.fanmanager.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.adictosalainformatica.fanmanager.R;
import com.adictosalainformatica.fanmanager.model.FanModel;
import com.adictosalainformatica.fanmanager.rest.ApiClient;
import com.adictosalainformatica.fanmanager.rest.ApiInterface;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_btn_fan)
    ImageButton btnFan;

    // Constants
    private static final String TAG = MainActivity.class.getName();
    private static int PIN = 8;

    private int fanStatus = 0;
    private static ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Picasso
                .with(getApplicationContext())
                .load(R.drawable.fan_image)
                .transform(new CropCircleTransformation())

                .into(btnFan);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        if(isConetctionEnabled(getApplicationContext())){
            getFanStatus(PIN);
        }else{
            Toast.makeText(getApplicationContext(),"Internet connection failed",Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.main_btn_fan)
    public void switchFanStatus() {
        if(isConetctionEnabled(getApplicationContext())){
            if(fanStatus == 0){
                setFanStatus(PIN,1);
            }else{
                setFanStatus(PIN, 0);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Internet connection failed",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Get current Fan status
     * @param pin
     */
    private void getFanStatus(int pin) {
        Call <FanModel> call = apiService.getStatus(pin);
        call.enqueue(new Callback<FanModel>() {
            @Override
            public void onResponse(Call<FanModel> call, Response<FanModel> response) {
                if (response.isSuccessful()) {
                    fanStatus =  response.body().getStatus();
                    Log.d(TAG, "Fan status: " + fanStatus);

                    if(fanStatus == 0){
                        int color = Color.parseColor("#33ee092b");
                        setColorFilter(color);

                    }else{
                        int color = Color.parseColor("#3300ff80");
                        setColorFilter(color);
                    }
                } else {
                    //request not successful (like 400,401,403 etc)
                    Log.e(TAG,response.message());
                }
            }

            @Override
            public void onFailure(Call<FanModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "Error: " + t.toString());
            }
        });
    }

    /**
     * Set Fan status
     * @param pin
     */
    private void setFanStatus(int pin, int status){
        Call call = apiService.setPin(pin,status);
        call.enqueue(new Callback<FanModel>() {
            @Override
            public void onResponse(Call<FanModel> call, Response<FanModel> response) {
                if (response.isSuccessful()) {
                    fanStatus =  response.body().getStatus();
                    Log.d(TAG, "Fan status: " + fanStatus);

                    if(fanStatus == 0){
                        int color = Color.parseColor("#33ee092b");
                        setColorFilter(color);

                    }else{
                        int color = Color.parseColor("#3300ff80");
                        setColorFilter(color);
                    }
                } else {
                    //request not successful (like 400,401,403 etc)
                    Log.e(TAG,response.message());
                }
            }

            @Override
            public void onFailure(Call<FanModel> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "Error: " + t.toString());
            }
        });
    }

    /**
     * Set image filter color
     * @param color
     */
    private void setColorFilter(int color){
        Picasso
                .with(getApplicationContext())
                .load(R.drawable.fan_image)
                .transform(new ColorFilterTransformation(color))
                .transform(new CropCircleTransformation())
                .into(btnFan);
    }

    /**
     * Tests if there's connection
     * @param cx context application
     * @return true or false
     */
    public static boolean isConetctionEnabled(Context cx){
        ConnectivityManager conMgr =  (ConnectivityManager)cx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
