/**
 * SMART - State Machine ARchiTecture
 *
 * Copyright (C) 2012 Individual contributors as indicated by
 * the @authors tag
 *
 * This file is a part of SMART.
 *
 * SMART is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SMART is distributed in the hope that it will be useful,
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
 * File:                org.anon.utilities.anatomy.CrossLinkApplication
 * Author:              rsankar
 * Revision:            1.0
 * Date:                22-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A crosslink for application
 *
 * ************************************************************
 * */

package org.anon.utilities.anatomy;

import org.anon.utilities.crosslink.CrossLinker;
import org.anon.utilities.exception.CtxException;

public class CrossLinkApplication extends CrossLinker
{
    public CrossLinkApplication(Object app)
    {
        super(app);
    }

    public void setStartLoader(ClassLoader ldr)
        throws CtxException
    {
        linkMethod("setStartLoader", ldr);
    }

    public ClassLoader getStartLoader()
        throws CtxException
    {
        return (ClassLoader)linkMethod("getStartLoader");
    }

    public static CrossLinkApplication getApplication()
        throws CtxException
    {
        Object app = Application.getApplication();
        return new CrossLinkApplication(app);
    }

    @Override
    protected Class[] parmTypes(String mthd, Object ... params)
    {
        if ((mthd != null) && (mthd.length() > 0) && (mthd.equals("setStartLoader")))
        {
            return new Class[] { ClassLoader.class };
        }

        return super.parmTypes(mthd, params);
    }
}

