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

import java.util.Locale;

import com.bhb27.turbotoast.R;
import com.bhb27.turbotoast.Tools;
import com.bhb27.turbotoast.root.RootUtils;

public class TurboToastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        boolean RootEnable = Tools.getBoolean("Root", false, context);
        boolean TurboToast = Tools.getBoolean("TurboToast", false, context);
        boolean Charge = Tools.getBoolean("Charge", false, context);
        boolean Run = Tools.getBoolean("Run", false, context);

        if ((!TurboToast && !Charge) || !Run) return;
        else if (RootEnable && !RootUtils.rootAccess()) {
            Tools.DoAToast((context.getResources().getString(R.string.no_root_access)), context);
            RootUtils.closeSU();
            return;
        }
        // Android is sending undesirable DISCONNECTED at boot with make a toast even if there is no action on the POWER
        Long time = SystemClock.elapsedRealtime();

        if (Intent.ACTION_POWER_CONNECTED.equals(action) && TurboToast)
            DoTurboToast(RootEnable, context);
        else if (Intent.ACTION_POWER_DISCONNECTED.equals(action) && Charge && (time > 150000) &&
            (Tools.getChargeCapacity(RootEnable) != null)) {
            Tools.DoAToast((context.getResources().getString(R.string.charge) + " " + Tools.getChargeCapacity(RootEnable) + "%"), context);
            RootUtils.closeSU();
        }
    }

    public void DoTurboToast(boolean root, Context context) {
        // in average the toast display in 2s add a litle more time just to make shore
        for (int i = 0; i < 10; i++) {
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
    }
}
