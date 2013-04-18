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
 * File:                org.anon.utilities.objservices.ObjectServiceLocator
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service that is available for the object, this will vary with the classloader of the object
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import java.util.Map;
import java.util.HashMap;

import org.anon.utilities.codecontrol.CodeControl;

public class ObjectServiceLocator
{
    public abstract static class ObjectService
    {
        public ObjectService()
        {
            OBJECTSERVICES.put(this.getClass().getName(), this);
        }
    }

    private static Map<String, ObjectService> OBJECTSERVICES = new HashMap<String, ObjectService>();

    private static final String FSMSVC = "org.anon.utilities.objservices.FSMService";
    private static final String POOLSVC = "org.anon.utilities.objservices.PoolService";
    private static final String VALSVC = "org.anon.utilities.objservices.ValueService";
    private static final String CONVERTSVC = "org.anon.utilities.objservices.ConvertService";
    private static final String CONFSVC = "org.anon.utilities.objservices.ConfigService";
    private static final String EXECUTESVC = "org.anon.utilities.objservices.ExecutorService";
    private static final String SERIALSVC = "org.anon.utilities.objservices.SerializerService";
    private static final String THREADSVC = "org.anon.utilities.objservices.ThreadService";
    private static final String ANATOMYSVC = "org.anon.utilities.objservices.AnatomyService";
    private static final String LOADERSVC = "org.anon.utilities.objservices.LoaderService";
    private static final String JITQSVC = "org.anon.utilities.objservices.JITQueueService";
    private static final String CODECTRL = "org.anon.utilities.codecontrol.CodeControl";
    private static final String CACHESVC = "org.anon.utilities.objservices.CacheServices";

    static
    {
        new FSMService();
        new PoolService();
        new ValueService();
        new ConvertService();
        new ConfigService();
        new SerializerService();
        new ThreadService();
        new AnatomyService();
        new LoaderService();
        new JITQueueService();
        new ExecutorService();
        new CodeControl();
        new CacheServices();
    }

    private ObjectServiceLocator()
    {
    }

    public static PoolService pool()
    {
        return (PoolService)OBJECTSERVICES.get(POOLSVC);
    }

    public static FSMService fsm()
    {
        return (FSMService)OBJECTSERVICES.get(FSMSVC);
    }

    public static ValueService value()
    {
        return (ValueService)OBJECTSERVICES.get(VALSVC);
    }

    public static ConvertService convert()
    {
        return (ConvertService)OBJECTSERVICES.get(CONVERTSVC);
    }

    public static ConfigService config()
    {
        return (ConfigService)OBJECTSERVICES.get(CONFSVC);
    }

    public static ExecutorService execute()
    {
        return (ExecutorService)OBJECTSERVICES.get(EXECUTESVC);
    }

    public static SerializerService serial()
    {
        return (SerializerService)OBJECTSERVICES.get(SERIALSVC);
    }

    public static ThreadService threads()
    {
        return (ThreadService)OBJECTSERVICES.get(THREADSVC);
    }

    public static AnatomyService anatomy()
    {
        return (AnatomyService)OBJECTSERVICES.get(ANATOMYSVC);
    }

    public static LoaderService loader()
    {
        return (LoaderService)OBJECTSERVICES.get(LOADERSVC);
    }

    public static JITQueueService jitq()
    {
        return (JITQueueService)OBJECTSERVICES.get(JITQSVC);
    }

    public static CodeControl control()
    {
        return (CodeControl)OBJECTSERVICES.get(CODECTRL);
    }

    public static CacheServices cache()
    {
        return (CacheServices)OBJECTSERVICES.get(CACHESVC);
    }
}

