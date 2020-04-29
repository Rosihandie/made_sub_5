package com.rosihandie.moviecatalogue_sub_5.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rosihandie.moviecatalogue_sub_5.R;
import com.rosihandie.moviecatalogue_sub_5.notif.ReceiverDaily;
import com.rosihandie.moviecatalogue_sub_5.notif.ReceiverRelease;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private Switch switchReminder;
    private Switch switchRelease;
    private ReceiverDaily dayliReceiver;
    private ReceiverRelease releaseReceiver;
    private SettingPreference settingPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_settings);

        switchReminder = findViewById(R.id.switch_daily_remind);
        switchRelease = findViewById(R.id.switch_release_remind);
        Button button = findViewById(R.id.btn_chg_lang);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Setting");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        dayliReceiver = new ReceiverDaily();
        releaseReceiver = new ReceiverRelease();

        settingPreference = new SettingPreference(this);
        setSwitchRelease();
        setSwitchReminder();

        // Switch Reminder OnClick
        switchReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchReminder.isChecked()) {
                    dayliReceiver.setDailyAlarm(getApplicationContext());
                    settingPreference.setDailyReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_daily_remind), Toast.LENGTH_SHORT).show();
                } else {
                    dayliReceiver.cancelAlarm(getApplicationContext());
                    settingPreference.setDailyReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_daily_remind), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Switch Release OnClick
        switchRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchRelease.isChecked()) {
                    releaseReceiver.setReleaseAlarm(getApplicationContext());
                    settingPreference.setReleaseReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_release_remind), Toast.LENGTH_SHORT).show();
                } else {
                    releaseReceiver.cancelAlarm(getApplicationContext());
                    settingPreference.setReleaseReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_release_remind), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button Change Language OnClick
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(Intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSwitchReminder() {
        if (settingPreference.getDailyReminder()) switchReminder.setChecked(true);
        else switchReminder.setChecked(false);
    }

    private void setSwitchRelease() {
        if (settingPreference.getReleaseReminder()) switchRelease.setChecked(true);
        else switchRelease.setChecked(false);
    }
}
