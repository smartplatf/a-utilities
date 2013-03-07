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
 * File:                org.anon.utilities.gconcurrent.execute.ConstantProbe
 * Author:              rsankar
 * Revision:            1.0
 * Date:                17-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A probe that works on constant values
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent.execute;


import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.exception.CtxException;

public class ConstantProbe implements PProbe
{
    public ConstantProbe()
    {
    }

    public Object valueFor(Class cls, ProbeParms parms, PDescriptor desc)
        throws CtxException
    {
        assertion().assertNotNull(desc, "Constants are explicit parameters and has to be specified.");
        if (cls != null)
            return convert().stringToClass(desc.attribute(), cls);

        return desc.attribute();
    }

    public Object valueFor(Class cls, ProbeParms parms)
        throws CtxException
    {
        return valueFor(parms, null);
    }

    public Object valueFor(ProbeParms parms, PDescriptor desc)
        throws CtxException
    {
        return valueFor(null, parms, desc);
    }

    public void releaseValues(Object[] val)
        throws CtxException
    {
    }
}

