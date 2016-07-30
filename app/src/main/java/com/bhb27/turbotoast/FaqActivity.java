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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FaqActivity extends Activity {
    // in order of appearance
    TextView faq, faq_charger, faq_charger_summary, faq_devices, faq_devices_summary, faq_no_toast, faq_no_toast_summary, faq_do_a_toast, faq_do_a_toast_summary, xda, xda_summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_fragment);

        View frameLayout = findViewById(R.id.faqLayout);

        faq = (TextView) findViewById(R.id.faq);
        faq.setText(R.string.faq);

        faq_charger = (TextView) findViewById(R.id.faq_charger);
        faq_charger.setText(R.string.faq_charger);

        faq_charger_summary = (TextView) findViewById(R.id.faq_charger_summary);
        faq_charger_summary.setText(R.string.faq_charger_summary);

        faq_devices = (TextView) findViewById(R.id.faq_devices);
        faq_devices.setText(R.string.faq_devices);

        faq_devices_summary = (TextView) findViewById(R.id.faq_devices_summary);
        faq_devices_summary.setText(R.string.faq_devices_summary);

        faq_no_toast = (TextView) findViewById(R.id.faq_no_toast);
        faq_no_toast.setText(R.string.faq_no_toast);

        faq_no_toast_summary = (TextView) findViewById(R.id.faq_no_toast_summary);
        faq_no_toast_summary.setText(R.string.faq_no_toast_summary);

        faq_do_a_toast = (TextView) findViewById(R.id.faq_do_a_toast);
        faq_do_a_toast.setText(R.string.faq_do_a_toast);

        faq_do_a_toast_summary = (TextView) findViewById(R.id.faq_do_a_toast_summary);
        faq_do_a_toast_summary.setText(R.string.faq_do_a_toast_summary);


        xda = (TextView) findViewById(R.id.xda);
        xda.setText("XDA Turbo Toast Thread");

        xda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/moto-maxx/themes-apps/app-turbo-toast-t3427981")));
            }
        });
    }
}
