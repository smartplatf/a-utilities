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
 * File:                org.anon.utilities.crosslink.CrossLinkAny
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A cross linker that links for any class
 *
 * ************************************************************
 * */

package org.anon.utilities.crosslink;

import org.anon.utilities.exception.CtxException;

public class CrossLinkAny extends CrossLinker
{
    private String _method;
    private Class[] _prms;

    public CrossLinkAny(String cls, ClassLoader ldr)
        throws CtxException
    {
        super(ldr, cls);
    }

    public CrossLinkAny(String cls)
        throws CtxException
    {
        super(cls);
    }

    public Object invoke(String mthd, Object ... parms)
        throws CtxException
    {
        _method = "";
        _prms = null;
        return linkMethod(mthd, parms);
    }

    public Object invoke(String mthd, Class[] cls, Object[] parms)
        throws CtxException
    {
        _method = mthd;
        _prms = cls;
        return linkMethod(mthd, parms);
    }

    public Object create(Class[] cls, Object[] parms)
        throws CtxException
    {
        _method = "<init>";
        _prms = cls;
        return create(parms);
    }

    protected Class[] parmTypes(String mthd, Object ... params)
    {
        if ((_method != null) && (_method.length() > 0) && (_method.equals(mthd)))
        {
            return _prms;
        }

        return super.parmTypes(mthd, params);
    }
}

