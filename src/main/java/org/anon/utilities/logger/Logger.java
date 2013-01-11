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
 * File:                org.anon.utilities.logger.Logger
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A logger for the projects in anon
 *
 * ************************************************************
 * */

package org.anon.utilities.logger;

public class Logger
{
    public static final boolean DEVELOPMENT_MODE = true;

    public class logdetails
    {
        //This stores the message that needs to logged
        private String _message;

        logdetails(String msg)
        {
            Thread thrd = Thread.currentThread();
            //ClassLoader loader = this.getClass().getClassLoader();
            String append = thrd.getClass().getSimpleName() + ":" + _loader.toString();
            if (DEVELOPMENT_MODE)
            {
                append = append + ":" + getCalledMethod(Thread.currentThread());
            }

            _message = append + ":" + msg;
        }

        private String getCalledMethod(Thread thrd)
        {
            StackTraceElement[] trace = thrd.getStackTrace();
            StackTraceElement use = trace[4];
            return use.toString();
        }

        public String toString() { return _message; }
    }


    private ClassLoader _loader;        //The classloader for which this log has been generated
    private LogProvider _provider;      //The provider is used to actually log

    static
    {
        //The default is the log4j logging
        String provider = System.getenv("LOG_PROVIDER");
        if (provider == null)
            provider = "log4j";
        LogFactory.initializeProvider(provider);
    }

    public Logger(String grp, String module, ClassLoader loader)
    {
        _provider = LogFactory.providerFor(grp, module);
        _loader = loader;
    }

    public boolean isInfoEnabled()
    {
        return _provider.isInfoEnabled();
    }

    public void info(String msg)
    {
        logdetails det = new logdetails(msg);  
        _provider.info(det);
    }

    public boolean isWarnEnabled()
    {
        return _provider.isWarnEnabled();
    }

    public void warn(String msg)
    {
        logdetails det = new logdetails(msg);
        _provider.warn(det);
    }

    public boolean isDebugEnabled()
    {
        return _provider.isDebugEnabled();
    }

    public void debug(String msg)
    {
        logdetails det = new logdetails(msg);
        _provider.debug(det);
    }

    public boolean isTraceEnabled()
    {
        return _provider.isTraceEnabled();
    }

    public void trace(String msg)
    {
        logdetails det = new logdetails(msg);
        _provider.trace(det);
    }

    public void error(String msg)
    {
        logdetails det = new logdetails(msg);
        _provider.error(det);
    }

    public void fatal(String msg, Throwable t)
    {
        logdetails det = new logdetails(msg);
        _provider.fatal(det, t);
    }

    public void fatal(Throwable t)
    {
        _provider.fatal(t);
    }

    public void fatal(String msg)
    {
        logdetails det = new logdetails(msg);
        _provider.fatal(det);
    }
}

