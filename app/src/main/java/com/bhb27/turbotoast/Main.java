/*
 * Copyright (C) 2016 Felipe de Leon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bhb27.turbotoast;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;

import java.io.IOException;

import android.widget.Toast;

import com.bhb27.turbotoast.Tools;
import com.bhb27.turbotoast.Constants;
import com.bhb27.turbotoast.AboutActivity;
import com.bhb27.turbotoast.FaqActivity;
import com.bhb27.turbotoast.root.RootUtils;

public class Main extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(Constants.PREF_NAME);
        addPreferencesFromResource(R.xml.preferences);

        // check on init if Root is enable if yes try to start Root
        String settingsTAG = "pref";
        SharedPreferences prefs = getSharedPreferences(settingsTAG, 0);
        boolean RootTag = prefs.getBoolean("Root", false);
        if (RootTag == true) {
            if (RootUtils.rooted() && RootUtils.rootAccess()) {
                Toast.makeText(Main.this, getString(R.string.root_guaranteed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Main.this, getString(R.string.no_root_access), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Main.this, getString(R.string.root_disable), Toast.LENGTH_LONG).show();
        }

        getPreferenceManager().findPreference("teste").setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String settingsTAG = "pref";
                SharedPreferences prefs = getSharedPreferences(settingsTAG, 0);
                boolean RootTag = prefs.getBoolean("Root", false);
                if (RootTag == true) {
                    if (RootUtils.rooted() && RootUtils.rootAccess()) {
                        Toast.makeText(Main.this, getString(R.string.device_model) + " " + Build.MODEL + "\n" + getString(R.string.test_a_toast) + "                " + Tools.getChargingType(), Toast.LENGTH_LONG).show();
                        return true;
                    } else {
                        Toast.makeText(Main.this, getString(R.string.no_root_access), Toast.LENGTH_LONG).show();
                        return true;
                    }
                } else {
                    Toast.makeText(Main.this, getString(R.string.device_model) + " " + Build.MODEL + "\n" + getString(R.string.test_a_toast) + "                " + Tools.getChargingTypeN(), Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        });
        getPreferenceManager().findPreference("about").setOnPreferenceClickListener(new OnPreferenceClickListener() {
            Intent myIntent = new Intent(getApplicationContext(), AboutActivity.class);
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(myIntent);
                return true;
            }
        });
        getPreferenceManager().findPreference("faq").setOnPreferenceClickListener(new OnPreferenceClickListener() {
            Intent myIntent = new Intent(getApplicationContext(), FaqActivity.class);
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(myIntent);
                return true;
            }
        });
    }
}
