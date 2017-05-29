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
import android.content.Context;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhb27.turbotoast.Tools;
import com.bhb27.turbotoast.Constants;

public class FaqActivity extends Activity {
    // in order of appearance
    TextView xda;
    ImageView ic_xda;
    private Context FaqContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_fragment);
        FaqContext = this;

        LinearLayout layout = (LinearLayout) findViewById(R.id.faqLayout);
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setFillAfter(true);
        animation.setDuration(500);
        layout.startAnimation(animation);

        //Link String
        final String xda_link = Constants.xda_link;

        xda = (TextView) findViewById(R.id.xda);
        xda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(xda_link)));
                } catch (ActivityNotFoundException ex) {
                    Tools.DoAToast(getString(R.string.no_browser), FaqContext);
                }
            }
        });

        ic_xda = (ImageView) findViewById(R.id.ic_xda);
        ic_xda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(xda_link)));
                } catch (ActivityNotFoundException ex) {
                    Tools.DoAToast(getString(R.string.no_browser), FaqContext);
                }
            }
        });
    }
}
