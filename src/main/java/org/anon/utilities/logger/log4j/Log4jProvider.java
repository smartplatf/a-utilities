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
 * File:                org.anon.utilities.logger.log4j.Log4jProvider
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A provider based on log4j logging
 *
 * ************************************************************
 * */

package org.anon.utilities.logger.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.PropertyConfigurator;
import java.net.URL;

import org.anon.utilities.logger.LogProvider;
import org.anon.utilities.logger.Logger.logdetails;

public class Log4jProvider implements LogProvider
{
    public static void init()
        throws Exception
    {
        try
        {
            //Loads the correct configuration file.
            //assumes that the log4j.xml file is present in the classpath
            //initialize only once. Will try to locate it using the class of this
            //class
            ClassLoader loader = Log4jProvider.class.getClassLoader();
            URL resource = loader.getResource("log4j.xml");
            if (resource != null)
            {
            	System.out.println("Loaded log4j.properties:"+resource.getPath());
                DOMConfigurator.configure(resource);
            }
            else
            {
                //check for log4j.properties file
                resource = loader.getResource("log4j.properties");
                if (resource != null)
                {
                	System.out.println("Loaded log4j.properties:"+resource.getPath());
                    PropertyConfigurator.configure(resource);
                }
                else
                {
                    throw new Exception("No Configuration.");
                    //System.out.println("No Log4j Configuration file. No logs will be generated.");
                }
            }
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            System.out.println("The logger configuration is not present." + e.getMessage());
            throw e;
        }
    }

    private Logger _logger;                 //reference to the log4j logger used for logging

    public Log4jProvider(String grp, String module)
    {
        _logger = Logger.getLogger(module);
    }

    public boolean isInfoEnabled() 
    { 
        return _logger.isInfoEnabled(); 
    }

    public void info(logdetails det)
    {
        _logger.info(det);
    }

    public boolean isWarnEnabled()
    {
        return _logger.isEnabledFor(Priority.WARN);
    }

    public void warn(logdetails det)
    {
        _logger.warn(det);
    }

    public boolean isDebugEnabled()
    {
        return _logger.isDebugEnabled();
    }

    public void debug(logdetails det)
    {
        _logger.debug(det);
    }

    public boolean isTraceEnabled()
    {
        return _logger.isTraceEnabled();
    }

    public void trace(logdetails det)
    {
        _logger.trace(det);
    }

    public void error(logdetails det)
    {
        _logger.error(det);
    }

    public void fatal(logdetails det)
    {
        _logger.fatal(det);
    }

    public void fatal(logdetails det, Throwable t)
    {
        _logger.fatal(det, t);
    }

    public void fatal(Throwable t)
    {
        _logger.fatal(t);
    }

    void addAppender(Appender append) 
    { 
        if (append != null)
            _logger.addAppender(append);
    }
}

