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
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.Locale;

import com.bhb27.turbotoast.R;
import com.bhb27.turbotoast.Tools;
import com.bhb27.turbotoast.root.RootUtils;

@TargetApi(21)
public class TurboToastJobService extends JobService {
    private static final String TAG = TurboToastJobService.class.getSimpleName();
    boolean isWorking = false;
    boolean jobCancelled = false;

    // Called by the Android system when it's time to run the job
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job started!");
        isWorking = true;
        DoTurboToast(jobParameters, this);

        return isWorking;
    }


    // Called if the job was cancelled before being finished
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job cancelled before being completed.");
        jobCancelled = true;
        jobFinished(jobParameters, isWorking);
        return isWorking;
    }

    public void DoTurboToast(JobParameters jobParameters, Context context) {
        boolean root = Tools.getBoolean("Root", false, this);
        // in average the toast display in 2s add a litle more time just to make shore
        for (int i = 0; i < 10; i++) {
            // If the job has been cancelled, stop working; the job will be rescheduled.
            if (jobCancelled)
                return;

            String chargetype = Tools.getChargingType(root);
            RootUtils.closeSU();

            if (chargetype != null && chargetype.toLowerCase(Locale.US).equals("turbo")) {
                Tools.DoAToast((context.getResources().getString(R.string.chargerconnected_turbo_toast)), context);
                break;
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        RootUtils.closeSU();

        Log.d(TAG, "Job finished!");
        isWorking = false;
        jobFinished(jobParameters, isWorking);
    }
}
