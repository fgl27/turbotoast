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

import android.app.Activity;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FaqActivity extends Activity {
    // in order of appearance
    TextView faq, faq_charger, faq_charger_summary, faq_devices, faq_devices_summary, faq_no_toast, faq_no_toast_summary, faq_do_a_toast, faq_do_a_toast_summary, xda, xda_summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_fragment);

        View frameLayout = findViewById(R.id.faqLayout);

        faq = (TextView) findViewById(R.id.faq);
        faq.setText(getString(R.string.faq));

        faq_charger = (TextView) findViewById(R.id.faq_charger);
        faq_charger.setText(getString(R.string.faq_charger));

        faq_charger_summary = (TextView) findViewById(R.id.faq_charger_summary);
        faq_charger_summary.setText(getString(R.string.faq_charger_summary));

        faq_devices = (TextView) findViewById(R.id.faq_devices);
        faq_devices.setText(getString(R.string.faq_devices));

        faq_devices_summary = (TextView) findViewById(R.id.faq_devices_summary);
        faq_devices_summary.setText(getString(R.string.faq_devices_summary));

        faq_no_toast = (TextView) findViewById(R.id.faq_no_toast);
        faq_no_toast.setText(getString(R.string.faq_no_toast));

        faq_no_toast_summary = (TextView) findViewById(R.id.faq_no_toast_summary);
        faq_no_toast_summary.setText(getString(R.string.faq_no_toast_summary));

        faq_do_a_toast = (TextView) findViewById(R.id.faq_do_a_toast);
        faq_do_a_toast.setText(getString(R.string.faq_do_a_toast));

        faq_do_a_toast_summary = (TextView) findViewById(R.id.faq_do_a_toast_summary);
        faq_do_a_toast_summary.setText(getString(R.string.faq_do_a_toast_summary));

        xda = (TextView) findViewById(R.id.xda);
        xda.setText(getString(R.string.xda_summary));

        xda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/moto-maxx/themes-apps/app-turbo-toast-t3427981")));
                } catch (ActivityNotFoundException ex) {
                    DoAToast(getString(R.string.no_browser));
                }
            }
        });
    }

    // simple toast function to center the message
    public void DoAToast(String message) {
        Toast toast = Toast.makeText(FaqActivity.this, message, Toast.LENGTH_SHORT);
        TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
        if (view != null) view.setGravity(Gravity.CENTER);
        toast.show();
    }
}
