/*
 * Copyright (c) 2015 The CyanogenMod Project
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

import android.app.IntentService;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import java.util.Locale;
import java.util.List;
import java.util.LinkedList;

import com.bhb27.turbotoast.R;
import com.bhb27.turbotoast.Tools;
import com.bhb27.turbotoast.root.RootUtils;

public class TurboToastService extends IntentService implements ChargerStateNotifier {
    private static final String TAG = "TurboToast-Service";

    private static final String sChargerConnected = "ChargerConnected";
    private static final String sChargerDisconnected = "ChargerDisconnected";

    private final Context mContext;
    private final ChargerReceiver mChargerReceiver;
    public static boolean running = false;

    public TurboToastService(Context context) {
        super("TurboToast-Service");
        mContext = context;
        mChargerReceiver = new ChargerReceiver(context, this);
        running = true;
    }

    @Override
    protected void onHandleIntent(Intent intent) {}

    @Override
    public void ChargerConnected() {
        Log.d(TAG, sChargerConnected);
        onReceive(sChargerConnected);
    }

    @Override
    public void ChargerDisconnected() {
        Log.d(TAG, sChargerDisconnected);
        onReceive(sChargerDisconnected);
    }

    public void onReceive(String charger) {

        boolean RootEnable = Tools.getBoolean("Root", false, mContext);
        boolean TurboToast = Tools.getBoolean("TurboToast", false, mContext);
        boolean Charge = Tools.getBoolean("Charge", false, mContext);
        boolean Run = Tools.getBoolean("Run", false, mContext);

        if ((!TurboToast && !Charge) || !Run) return;
        else if (RootEnable && !RootUtils.rootAccess()) {
            Tools.DoAToast((mContext.getResources().getString(R.string.no_root_access)), mContext);
            RootUtils.closeSU();
            return;
        } else RootUtils.closeSU();
        // Android is sending undesirable DISCONNECTED at boot with make a toast even if there is no action on the POWER
        Long time = SystemClock.elapsedRealtime();

        if (charger.equals(sChargerConnected) && TurboToast)
            DoTurboToast(RootEnable, mContext);
        else if (charger.equals(sChargerDisconnected) && canChechck(mContext) && Charge && (time > 150000) &&
            (Tools.getChargeCapacity(RootEnable) != null)) {
            Tools.DoAToast((mContext.getResources().getString(R.string.charge) + " " + Tools.getChargeCapacity(RootEnable) + "%"), mContext);
            RootUtils.closeSU();
        }

        RootUtils.closeSU();
    }

    public void DoTurboToast(boolean root, Context context) {
        // in average the toast display in 2s add a litle more time just to make shore
        for (int i = 0; i < 10; i++) {
            if (canChechck(context)) {
                String chargetype = Tools.getChargingType(root);
                RootUtils.closeSU();
                if (chargetype != null && chargetype.toLowerCase(Locale.US).equals("turbo")) {
                    Tools.DoAToast((context.getResources().getString(R.string.chargerconnected_turbo_toast)), context);
                    break;
                } else DoSleep();
            } else DoSleep();
        }
        RootUtils.closeSU();
    }

    public void DoSleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @SuppressWarnings("deprecation")
    public boolean canChechck(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenAwake, isLocked;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) isScreenAwake = pm.isInteractive();
        else isScreenAwake = pm.isScreenOn();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) isLocked = km.isKeyguardLocked();
        else isLocked = km.inKeyguardRestrictedInputMode();

        return !isLocked && isScreenAwake;
    }
}
