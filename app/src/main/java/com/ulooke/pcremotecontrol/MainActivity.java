package com.ulooke.pcremotecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;
    ControlFragment controlFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        controlFragment = new ControlFragment();
    }

    public void onFragmentChanged(int index){
        if(index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.visibleFragment, mainFragment).commit();
        }
        else if(index == 1){
            Log.d("test call", "onFragmentChanged called");
            getSupportFragmentManager().beginTransaction().replace(R.id.visibleFragment, controlFragment).commit();
        }
    }
}