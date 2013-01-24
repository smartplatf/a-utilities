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
 * File:                org.anon.utilities.logger.Appenders
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An appender functionality which adds appenders to the logger
 *
 * ************************************************************
 * */

package org.anon.utilities.logger;

import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;

public class Appenders
{
    private static Map<String, LogAppender> _appenders = new ConcurrentHashMap<String, LogAppender>();

    private Appenders()
    {
    }

    private static String getLogFile(String name)
    {
        Date dt = new Date();
        String tmpdir = System.getProperty("user.dir");
        tmpdir += "/logs/";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return tmpdir + "Utilities-" + name + "-Logs-" + format.format(dt) + ".log";
    }

    static void addAppenders(String grp, LogProvider provider)
    {
        LogAppender append = _appenders.get(grp);
        if (append == null)
        {
            String file = getLogFile(grp);
            append = LogFactory.appenderFor(file, grp);
        }
        if (append != null) append.attachToProvider(provider);
    }
}

