package com.ulooke.pcremotecontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;
    ControlFragment controlFragment;
    Bundle bundleMainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        controlFragment = new ControlFragment();
        bundleMainFragment = new Bundle();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("UlookEE", "onActivityResult: " + mainFragment.getView());
                bundleMainFragment.putString("codeEditText", result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
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

    public Bundle getBundleMainFragment(){
        return bundleMainFragment;
    }

}
