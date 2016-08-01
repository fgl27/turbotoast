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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.os.SystemClock;

import com.bhb27.turbotoast.R;
import com.bhb27.turbotoast.Tools;
import com.bhb27.turbotoast.root.RootUtils;

public class TurboToastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

	String action = intent.getAction();
	// Android is sending undesirable DISCONNECTED at boot with make a toast even if there is no action on the POWER
	Long time = SystemClock.elapsedRealtime();

        // turbotoast
        if ((Intent.ACTION_POWER_CONNECTED.equals(action)) && (Tools.getBoolean("TurboToast", true, context))) {
            if (Tools.getBoolean("Root", true, context)) {
                if (RootUtils.rooted() && RootUtils.rootAccess()) {
                    // in average the toast display in 2s add a litle more time just to make shore
                    for (int i = 0; i < 50; i++) {
                        if (Tools.getChargingType().equals("Turbo")) {
                            Toast.makeText(context, (context.getResources().getString(R.string.chargerconnected_turbo_toast)), Toast.LENGTH_LONG).show();
                            i = 51;
                        } else {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                } else
                    Toast.makeText(context, (context.getResources().getString(R.string.no_root_access)), Toast.LENGTH_LONG).show();
            } else {
                // in average the toast display in 2s add a litle more time just to make shore
                for (int i = 0; i < 50; i++) {
                    if (Tools.getChargingTypeN().equals("Turbo")) {
                        Toast.makeText(context, (context.getResources().getString(R.string.chargerconnected_turbo_toast)), Toast.LENGTH_LONG).show();
                        i = 51;
                    } else {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }
        // charge toast 150000 = 150 seconds
        if ((Intent.ACTION_POWER_DISCONNECTED.equals(action)) && (Tools.getBoolean("Charge", true, context)) && (time > 150000)) {
            if (Tools.getBoolean("Root", true, context)) {
                if (RootUtils.rooted() && RootUtils.rootAccess())
                    Toast.makeText(context, (context.getResources().getString(R.string.charge) + " " + Tools.getChargeCapacity() + "%"), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, (context.getResources().getString(R.string.no_root_access)), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(context, (context.getResources().getString(R.string.charge) + " " + Tools.getChargeCapacityN() + "%"), Toast.LENGTH_LONG).show();
	}
    }
}
