/*
 * Copyright (C) 2016 Felipe de Leon fglfgl27@gmail.com
 *
 * This file is part of Kernel Adiutor.
 *
 * Kernel Adiutor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kernel Adiutor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kernel Adiutor.  If not, see <http://www.gnu.org/licenses/>.
 *
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
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.File;

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
                DoAToast(getString(R.string.root_guaranteed));
            } else {
                DoAToast(getString(R.string.no_root_access));
            }
        } else {
            DoAToast(getString(R.string.root_disable));
        }

        getPreferenceManager().findPreference("teste").setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String settingsTAG = "pref";
                SharedPreferences prefs = getSharedPreferences(settingsTAG, 0);
                boolean RootTag = prefs.getBoolean("Root", false);
                if (RootTag == true) {
                    if (RootUtils.rooted() && RootUtils.rootAccess()) {
                        DoAToast(getString(R.string.device_model) + " " + Build.MODEL + "\n" + getString(R.string.test_a_toast) + Tools.getChargingType());
                        return true;
                    } else {
                        DoAToast(getString(R.string.no_root_access));
                        return true;
                    }
                } else {
                    DoAToast(getString(R.string.device_model) + " " + Build.MODEL + "\n" + getString(R.string.test_a_toast) + Tools.getChargingTypeN());
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

    // simple toast function to center the message
    public void DoAToast(String message) {
        Toast toast = Toast.makeText(Main.this, message, Toast.LENGTH_SHORT);
        TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
        if (view != null) view.setGravity(Gravity.CENTER);
        toast.show();
    }
}
