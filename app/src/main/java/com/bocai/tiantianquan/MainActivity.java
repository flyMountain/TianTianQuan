package com.bocai.tiantianquan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bocai.tiantianquan.tx.SpeedMeterLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final SpeedMeterLayout speedMeter = (SpeedMeterLayout) findViewById(R.id.view);
        speedMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick","onClick");
                speedMeter.startMeasuring();
            }
        });
    }
}
