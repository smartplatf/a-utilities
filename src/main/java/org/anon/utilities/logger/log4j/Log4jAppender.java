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
 * File:                org.anon.utilities.logger.log4j.Log4jAppender
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An appender that does log4j append
 *
 * ************************************************************
 * */

package org.anon.utilities.logger.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.EnhancedPatternLayout;

import org.anon.utilities.logger.LogAppender;
import org.anon.utilities.logger.LogProvider;

public class Log4jAppender implements LogAppender
{
    private Appender _appender = null;

    public Log4jAppender(String name, String grp)
    {
        _appender = createAppender(name, grp);
    }

    public Appender createAppender(String name, String grp)
    {
        Appender ret = null;
        try
        {
            if (_appender == null)
            {
                RollingFileAppender logappend = new RollingFileAppender(new EnhancedPatternLayout(EnhancedPatternLayout.TTCC_CONVERSION_PATTERN),
                        name, true);
                logappend.setName(grp);
                //need to make this configurable
                logappend.setMaxBackupIndex(3);
                logappend.setMaxFileSize("10MB");
                ret = logappend;
            }
            else
                ret = _appender;

            return ret;
        }
        catch (Exception e)
        {
            //this cannot be logged since it is a logger exception
            e.printStackTrace();
        }

        return null;
    }

    public void attachToProvider(LogProvider provider)
    {
        if (provider instanceof Log4jProvider)
        {
            Log4jProvider prov = (Log4jProvider)provider;
            prov.addAppender(_appender);
        }
    }
}

