package com.example.gio.autostop.user_interface.activities;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gio.autostop.Constants;
import com.example.gio.autostop.user_interface.OnSwipeTouchListener;
import com.example.gio.autostop.R;



public class MainActivity extends AppCompatActivity {
    public ImageView goImageView;
    public TextView driver;
    public TextView passenger;
    View slideView;
    LinearLayout chooseButtonLayout;
    Boolean choose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slideView = findViewById(R.id.slide_view);
        passenger = (TextView) findViewById(R.id.passenger);
        driver = (TextView) findViewById(R.id.driver);
        chooseButtonLayout = (LinearLayout) findViewById(R.id.chooseType);
        goImageView = (ImageView) findViewById(R.id.imageGo);
        goImageView.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               if(checkIfInternetIsAvailable(v.getContext()))
                                               displayMap(choose);
                                               else
                                                   Toast.makeText(v.getContext(),"No Internet",Toast.LENGTH_LONG).show();
                                           }
                                       }
        );

        passenger.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams params = slideView.getLayoutParams();
                params.height = passenger.getHeight();
                params.width = passenger.getWidth();
                slideView.setLayoutParams(params);
                passenger.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        passenger.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                slideView.animate().x(0);
                choose = false;
                return false;
            }
        });
        driver.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                slideView.animate().x(slideView.getWidth());
                choose = true;
                return false;
            }
        });
        chooseButtonLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                slideView.animate().x(slideView.getWidth());
                choose = true;
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                slideView.animate().x(0);
                choose = false;
            }
        });
    }
    public boolean checkIfInternetIsAvailable(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public void displayMap(boolean choose) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(Constants.chosenMode, choose);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as. you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.request_permission) {

            // Permission to access the location is missing.
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}