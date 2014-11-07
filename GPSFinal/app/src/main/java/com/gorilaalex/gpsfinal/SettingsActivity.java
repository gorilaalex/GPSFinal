package com.gorilaalex.gpsfinal;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gorilaalex.gpsfinal.R;

public class SettingsActivity extends Activity {

    public static final String TYPE_EXTRA = "";
    public static final boolean ALERT_BOOLEAN_EXTRA = false;
    public static final float ALERT_SPEED_EXTRA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupViews();
    }

    private void setupViews() {
        findViewById(R.id.radioGroupId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton)v).isChecked();

                switch(v.getId()) {
                    case R.id.kmButton:
                        Toast.makeText(getApplicationContext(),"Km button",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.mileButton:
                        Toast.makeText(getApplicationContext(),"mile button",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

}
