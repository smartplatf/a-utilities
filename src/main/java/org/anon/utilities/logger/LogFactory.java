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
 * File:                org.anon.utilities.logger.LogFactory
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A factory to create multiple loggers
 *
 * ************************************************************
 * */

package org.anon.utilities.logger;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

import org.anon.utilities.logger.log4j.Log4jProvider;
import org.anon.utilities.logger.log4j.Log4jAppender;

public class LogFactory
{
    public enum providers 
    {
        nulllogger(NullProvider.class, NullAppender.class),
        log4j(Log4jProvider.class, Log4jAppender.class);

        private Class<? extends LogProvider> _provider;
        private Class<? extends LogAppender> _appender;

        private providers(Class<? extends LogProvider> cls, Class<? extends LogAppender> acls)
        {
            _provider = cls;
            _appender = acls;
        }

        public Class getProviderClass()
        {
            return _provider;
        }

        public Class getAppenderClass()
        {
            return _appender;
        }
    }

    private static providers CURRENT_PROVIDER = providers.nulllogger;

    public static providers initializeProvider(String provider)
    {
        providers thisprov = providers.valueOf(provider);

        try
        {
            Class cls = thisprov.getProviderClass();
            Method mthd = cls.getDeclaredMethod("init");
            if (mthd != null)
            {
                mthd.invoke(null);
            }
        }
        catch (Exception e)
        {
            //default to a nullprovider
            thisprov = providers.nulllogger;
        }

        CURRENT_PROVIDER = thisprov;
        return thisprov;
    }

    public static LogProvider providerFor(String grp, String module)
    {
        LogProvider ret = null;
        try
        {
            Class cls = CURRENT_PROVIDER.getProviderClass();
            Constructor cons = cls.getDeclaredConstructor(String.class, String.class);
            ret = (LogProvider)cons.newInstance(grp, module);
            Appenders.addAppenders(grp, ret);
        }
        catch (Exception e)
        {
            ret = new NullProvider(grp, module);
        }

        return ret;
    }

    public static LogAppender appenderFor(String name, String grp)
    {
        LogAppender ret = null;

        try
        {
            Class cls = CURRENT_PROVIDER.getProviderClass();
            Constructor cons = cls.getDeclaredConstructor(String.class, String.class);
            ret = (LogAppender)cons.newInstance(name, grp);
        }
        catch (Exception e)
        {
            ret  = null;
        }

        return ret;
    }
}

