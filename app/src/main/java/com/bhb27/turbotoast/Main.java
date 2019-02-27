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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;

import com.bhb27.turbotoast.AboutActivity;
import com.bhb27.turbotoast.Constants;
import com.bhb27.turbotoast.FaqActivity;
import com.bhb27.turbotoast.root.RootUtils;
import com.bhb27.turbotoast.Tools;

public class Main extends Activity {

    private static boolean app_is_open = true;
    private static final String TAG = Main.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesName(Constants.PREF_NAME);
            addPreferencesFromResource(R.xml.preferences);
            Tools.saveBoolean("Run", true, getActivity());
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ScheduleobService(getActivity());

            // check on init if Root is enable if yes try to start Root
            // use app_is_open bool to prevent the app from toast every time the display rotate 
            if (app_is_open) {
                if (Tools.getBoolean("Root", true, getActivity())) {
                    if (RootUtils.rootAccess()) {
                        Tools.DoAToast(getString(R.string.root_guaranteed), getActivity());
                    } else {
                        Tools.DoAToast(getString(R.string.no_root_access), getActivity());
                    }
                } else {
                    Tools.DoAToast(getString(R.string.root_disable), getActivity());
                }
                app_is_open = false;
            }
            getPreferenceManager().findPreference("teste").setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    boolean root = Tools.getBoolean("Root", true, getActivity());
                    if (root && !RootUtils.rootAccess()) {
                        Tools.DoAToast(getString(R.string.no_root_access), getActivity());
                        return true;
                    }
                    Tools.DoAToast(getString(R.string.device_model) + " " + Build.MODEL + "\n" + getString(R.string.test_a_toast) + Tools.getChargingType(root), getActivity());
                    return true;
                }
            });
            getPreferenceManager().findPreference("about").setOnPreferenceClickListener(new OnPreferenceClickListener() {
                Intent myIntent = new Intent(getActivity(), AboutActivity.class);
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(myIntent);
                    return true;
                }
            });
            getPreferenceManager().findPreference("faq").setOnPreferenceClickListener(new OnPreferenceClickListener() {
                Intent myIntent = new Intent(getActivity(), FaqActivity.class);
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(myIntent);
                    return true;
                }
            });
        }

        @Override
        public void onPause() {
            super.onPause();
            RootUtils.closeSU();
        }
    }

    @TargetApi(21)
    private static void ScheduleobService(Context context) {
        JobInfo jobInfo = new JobInfo.Builder(12, new ComponentName(context, TurboToastJobService.class))
                .setRequiresCharging(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled!");
        } else {
            Log.d(TAG, "Job not scheduled");
        }
    }

}
