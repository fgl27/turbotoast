/*
 * Copyright (C) Felipe de Leon <fglfgl27@gmail.com>
 *
 * This file is part of turbotoast.
 *
 * turbotoast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * turbotoast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with turbotoast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.bhb27.turbotoast.tileservice;

import android.annotation.TargetApi;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import com.bhb27.turbotoast.R;
import com.bhb27.turbotoast.Tools;
import com.bhb27.turbotoast.root.RootUtils;

@TargetApi(24)
public class QuickTileTurbo extends TileService {

    @Override
    public void onStartListening() {
        super.onStartListening();
        getQsTile().setLabel(Tools.getChargingType(Tools.getBoolean("Root", false, this)));
        getQsTile().updateTile();
    }

    @Override
    public void onStopListening() {
    }

    @Override
    public void onClick() {
        super.onClick();
        getQsTile().setLabel(Tools.getChargingType(Tools.getBoolean("Root", false, this)));
        getQsTile().updateTile();
    }

}
