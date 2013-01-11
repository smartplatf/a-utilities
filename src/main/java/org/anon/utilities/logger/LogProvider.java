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
 * File:                org.anon.utilities.logger.LogProvider
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * LogProvider
 *
 * ************************************************************
 * */

package org.anon.utilities.logger;

public interface LogProvider
{
    public boolean isInfoEnabled();
    public void info(Logger.logdetails msg);

    public boolean isWarnEnabled();
    public void warn(Logger.logdetails msg);

    public boolean isDebugEnabled();
    public void debug(Logger.logdetails msg);

    public boolean isTraceEnabled();
    public void trace(Logger.logdetails msg);

    public void error(Logger.logdetails msg);

    public void fatal(Logger.logdetails msg, Throwable t);
    public void fatal(Logger.logdetails msg);
    public void fatal(Throwable t);
    
}


