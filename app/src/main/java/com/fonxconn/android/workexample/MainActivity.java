package com.fonxconn.android.workexample;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fonxconn.android.workexample.fragment.FactsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment();
    }

    /**
     * 添加fragment到主activity
     */
    private void setFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().
                add(R.id.fl_fragment, new FactsFragment())
                .commit();
    }


}
