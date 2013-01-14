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
 * File:                org.anon.utilities.utils.VMSingleton
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A singleton in which only one version of the object exists within a JVM irrespective of classloaders
 *
 * ************************************************************
 * */

package org.anon.utilities.utils;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.crosslink.CrossLinkAny;
import org.anon.utilities.exception.CtxException;

public abstract class VMSingleton
{
    //private static VMSingleton SINGLE_INSTANCE;

    protected VMSingleton()
    {
    }

    /* cannot do it here. else for all classes this becomes a single instance
    protected static void setSingleInstance(Object obj)
    {
        if (SINGLE_INSTANCE == null)
            SINGLE_INSTANCE = (VMSingleton)obj;
    }

    protected static Object getSingleInstance()
    {
        return SINGLE_INSTANCE;
    }
    */

    protected static Object getInstance(String cls, String creatorcls, String createmthd, Object ... parms)
        throws CtxException
    {
        int len = 0;
        if (parms != null)
            len = parms.length;
        Class[] prmcls = new Class[len];
        for (int i = 0; i < len; i++)
        {
            if (parms[i] != null)
                prmcls[i] = parms[i].getClass();
        }
        return getInstance(cls, creatorcls, createmthd, prmcls, parms);
    }

    protected static Object getInstance(String cls, String creatorcls, String createmthd, Class[] prmcls, Object[] parms)
        throws CtxException
    {
        ClassLoader syscl = VMSingleton.class.getClassLoader().getSystemClassLoader();
        return getInstance(cls, creatorcls, createmthd, prmcls, parms, syscl);
    }

    protected static Object getInstance(String cls, String creatorcls, String createmthd, Class[] prmcls, Object[] parms, ClassLoader syscl)
        throws CtxException
    {
        Object ret = null;
        try
        {
            CrossLinkAny myclscl = new CrossLinkAny(cls, syscl);
            ret = myclscl.invoke("getSingleInstance");
            if (ret == null)
            {
                CrossLinkAny creatorcl = new CrossLinkAny(creatorcls, syscl);
                ret = creatorcl.invoke(createmthd, prmcls, parms);
                myclscl.invoke("setSingleInstance", new Class[] { Object.class }, new Object[] { ret });
            }
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("getInstance", "Class: " + cls + " creator: " + creatorcls + " Method " + createmthd));
        }

        return ret;
    }

    protected static Object getInstance(String cls)
        throws CtxException
    {
        return getInstance(cls, "org.anon.utilities.utils.DefaultVMSCreator", "createVMS", new Class[] { String.class }, new Object[] { cls });
    }
}

