package com.ulooke.pcremotecontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

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

    @Override
    public void onBackPressed() {
        Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.visibleFragment);
        if(curFragment.getId() == mainFragment.getId()){
            super.onBackPressed();
        }
        else{
            onFragmentChanged(0);
        }
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
