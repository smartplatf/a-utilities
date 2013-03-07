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
 * File:                org.anon.utilities.utils.ApplicationSingleton
 * Author:              rsankar
 * Revision:            1.0
 * Date:                13-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A VMSingleton that is maintained in app start classloader
 *
 * ************************************************************
 * */

package org.anon.utilities.utils;

import org.anon.utilities.anatomy.Application;
import org.anon.utilities.exception.CtxException;

public abstract class ApplicationSingleton extends VMSingleton
{
    protected ApplicationSingleton()
    {
    }

    protected static Object getAppInstance(String cls, String creatorcls, String createmthd, Class[] prmcls, Object[] parms)
        throws CtxException
    {
        ClassLoader syscl = Application.getApplication().getStartLoader();
        if (syscl == null)
            syscl = ApplicationSingleton.class.getClassLoader().getSystemClassLoader();

        return getInstance(cls, creatorcls, createmthd, prmcls, parms, syscl);
    }

    protected static Object getAppInstance(String cls)
        throws CtxException
    {
        return getAppInstance(cls, "org.anon.utilities.utils.DefaultVMSCreator", "createVMS", new Class[] { String.class }, new Object[] { cls });
    }
}

