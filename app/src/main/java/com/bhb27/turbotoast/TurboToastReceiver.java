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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.bhb27.turbotoast.R;
import com.bhb27.turbotoast.Tools;
import com.bhb27.turbotoast.root.RootUtils;

public class TurboToastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

	// Force write default pref if the app was never open, if it was Main will set pre to false
	if (Tools.getBoolean("pre", true, context)) {
		Tools.saveBoolean("TurboToast", true, context);
		Tools.saveBoolean("Charge", true, context);
		Tools.saveBoolean("Root", false, context);
		Tools.saveBoolean("pre", false, context);
	}

        String action = intent.getAction();
        boolean RootEnable = Tools.getBoolean("Root", true, context);
        boolean TurboToast = Tools.getBoolean("TurboToast", true, context);
        boolean Charge = Tools.getBoolean("Charge", true, context);
        if (!TurboToast && !Charge) return;
        else if (RootEnable && !RootUtils.rootAccess()) {
            Tools.DoAToast((context.getResources().getString(R.string.no_root_access)), context);
            return;
        }
        // Android is sending undesirable DISCONNECTED at boot with make a toast even if there is no action on the POWER
        Long time = SystemClock.elapsedRealtime();

        // turbotoast
        if (Intent.ACTION_POWER_CONNECTED.equals(action) && TurboToast)
            DoTurboToast(RootEnable, context);

        // charge toast 150000 = 150 seconds
        if (Intent.ACTION_POWER_DISCONNECTED.equals(action) && Charge && (time > 150000) && (Tools.getChargeCapacity(RootEnable) != null))
            Tools.DoAToast((context.getResources().getString(R.string.charge) + " " + Tools.getChargeCapacity(RootEnable) + "%"), context);
    }

    public void DoTurboToast(boolean root, Context context) {
        // in average the toast display in 2s add a litle more time just to make shore
        for (int i = 0; i < 50; i++) {
            String chargetype = Tools.getChargingType(root);
            if (chargetype == null)
               chargetype = "";
            if (chargetype.equals("Turbo")) {
                Tools.DoAToast((context.getResources().getString(R.string.chargerconnected_turbo_toast)), context);
                break;
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
