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
 * File:                org.anon.utilities.services.ServiceLocator
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service locator used to register and use services
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import java.util.Map;
import java.util.HashMap;

public class ServiceLocator
{
    public abstract static class Service
    {
        public Service()
        {
            SERVICES.put(this.getClass().getName(), this);
        }
    }

    private static Map<String, Service> SERVICES = new HashMap<String, Service>();

    private static final String EXCEPTSVC = "org.anon.utilities.services.ExceptionService";
    private static final String LOGGERSVC = "org.anon.utilities.services.LoggerService";
    private static final String REFLECTSVC = "org.anon.utilities.services.ReflectionService";
    private static final String IOSVC = "org.anon.utilities.services.IOService";
    private static final String ASSERTSVC = "org.anon.utilities.services.AssertionService";
    private static final String TYPESVC = "org.anon.utilities.services.TypeService";
    private static final String PERFSVC = "org.anon.utilities.services.PerformanceService";
    private static final String JARSVC = "org.anon.utilities.services.JarService";

    public static final boolean DEVELOPMENT_BUILD = true;

    static
    {
        new ExceptionService();
        new LoggerService();
        new ReflectionService();
        new IOService();
        new AssertionService();
        new TypeService();
        new PerformanceService();
        new JarService();
    }

    private ServiceLocator()
    {
    }

    public static ExceptionService except()
    {
        return (ExceptionService)SERVICES.get(EXCEPTSVC);
    }

    public static LoggerService logger()
    {
        return (LoggerService)SERVICES.get(LOGGERSVC);
    }

    public static ReflectionService reflect()
    {
        return (ReflectionService)SERVICES.get(REFLECTSVC);
    }

    public static IOService io()
    {
        return (IOService)SERVICES.get(IOSVC);
    }

    public static AssertionService assertion()
    {
        return (AssertionService)SERVICES.get(ASSERTSVC);
    }

    public static TypeService type()
    {
        return (TypeService)SERVICES.get(TYPESVC);
    }

    public static PerformanceService perf()
    {
        return (PerformanceService)SERVICES.get(PERFSVC);
    }

    public static JarService jar()
    {
        return (JarService)SERVICES.get(JARSVC);
    }
}

