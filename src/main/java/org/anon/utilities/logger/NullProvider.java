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
 * File:                org.anon.utilities.logger.NullProvider
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A null provider if the original provider is not present
 *
 * ************************************************************
 * */

package org.anon.utilities.logger;

public class NullProvider implements LogProvider
{
    public NullProvider(String grp, String module)
    {
    }

    public boolean isInfoEnabled()
    {
        return true;
    }

    public void info(Logger.logdetails msg)
    {
        System.out.println(msg);
    }

    public boolean isWarnEnabled()
    {
        return true;
    }

    public void warn(Logger.logdetails msg)
    {
        System.out.println(msg);
    }

    public boolean isDebugEnabled()
    {
        return true;
    }

    public void debug(Logger.logdetails msg)
    {
        System.out.println(msg);
    }

    public boolean isTraceEnabled()
    {
        return true;
    }

    public void trace(Logger.logdetails msg)
    {
        System.out.println(msg);
    }

    public void error(Logger.logdetails msg)
    {
        System.out.println(msg);
    }

    public void fatal(Logger.logdetails msg)
    {
        System.out.println(msg);
    }

    public void fatal(Throwable t)
    {
        t.printStackTrace();
    }

    public void fatal(Logger.logdetails msg, Throwable t)
    {
        System.out.println(msg);
        t.printStackTrace();
    }
}

