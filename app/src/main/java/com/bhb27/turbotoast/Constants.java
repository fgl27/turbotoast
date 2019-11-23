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

public interface Constants {

    String TAG = "Turbotoast";
    String PREF_NAME = "pref";

    // Battery values works for quark and maybe other Moto devices
    String BATTERY_PARAMETERS = "/sys/class/power_supply/battery";
    // Battery charging mode or rate type
    String BATTERY_CHARGING_TYPE = BATTERY_PARAMETERS + "/charge_rate";
    // Battery %
    String BATTERY_CAPACITY = BATTERY_PARAMETERS + "/capacity";

    //links
    String email_link = "mailto:fglfgl27@gmail.com";
    String xda_link = "http://forum.xda-developers.com/moto-maxx/themes-apps/app-turbo-toast-t3427981";
    String git_link = "https://github.com/fgl27/turbotoast";
}
