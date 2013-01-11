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
 * File:                org.anon.utilities.gauge.ThreadGauge
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A gauge to which the current thread's perf is recorded
 *
 * ************************************************************
 * */

package org.anon.utilities.gauge;

import org.anon.utilities.logger.Logger;

public class ThreadGauge
{
    private static final ThreadLocal<PGauge> _gauge = new ThreadLocal<PGauge>();

    public static void startHere(String tag)
    {
        PGauge p = _gauge.get();
        if (p == null)
            p = new PGauge();
        p.start(tag);
        _gauge.set(p);
    }

    public static void checkpointHere(String tag)
    {
        PGauge p = _gauge.get();
        if (p != null)
            p.record(tag);
    }

    public static void dumpHere(Logger log)
    {
        PGauge p = _gauge.get();
        if (p != null)
        {
            p.stop();
            p.dump(log);
        }
    }
}

