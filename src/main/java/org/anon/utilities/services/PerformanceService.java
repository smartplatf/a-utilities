/**
 * Utilities - Utilities used by anon
 *
 * Copyright (C) 2012 Individual contributors as indicated by
 * the @authors tag
 *
 * This file is a part of Utilities.
 *
 * Utilities is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Utilities is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * */
 
/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.anon.utilities.services.PerformanceService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service that allows to record performance stats
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.gauge.ThreadGauge;
import org.anon.utilities.logger.Logger;

public class PerformanceService extends ServiceLocator.Service
{
    PerformanceService()
    {
        super();
    }

    public void startHere(String tag)
    {
        if (DEVELOPMENT_BUILD)
        {
            ThreadGauge.startHere(tag);
        }
    }

    public void checkpointHere(String tag)
    {
        if (DEVELOPMENT_BUILD)
        {
            ThreadGauge.checkpointHere(tag);
        }
    }

    public void dumpHere(Logger log)
    {
        if (DEVELOPMENT_BUILD)
        {
            ThreadGauge.dumpHere(log);
        }
    }

}

