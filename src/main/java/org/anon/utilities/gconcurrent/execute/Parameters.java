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
 * File:                org.anon.utilities.gconcurrent.execute.Parameters
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of probes based on which the graph is executed
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent.execute;

import java.util.List;

import org.anon.utilities.exception.CtxException;

public class Parameters
{
    private List<PDescriptor> _descriptor;
    private ProbeParms _parms;
    private ParamType[] _types;

    public Parameters(List<PDescriptor> pdesc, ProbeParms parms)
    {
        _descriptor = pdesc;
        _parms = parms;
        _types = ParamType.types();
    }

    public Object parameterFor(Class cls, int i)
        throws CtxException
    {
        PDescriptor pdesc = null;
        if ((_descriptor != null) && (i < _descriptor.size()))
            pdesc = _descriptor.get(i);
        Object obj = null;
        if (pdesc == null)
            obj = findParameter(cls);
        else
            obj = findExplicitParam(cls, pdesc);

        return obj;
    }

    private Object findExplicitParam(Class cls, PDescriptor desc)
        throws CtxException
    {
        ParamType type = desc.ptype();
        Object ret = null;
        if (type.isExplicit())
            ret = type.myProbe().valueFor(cls, _parms, desc);
        else
            ret = type.myProbe().valueFor(_parms, desc);
        return ret;
    }

    private Object findParameter(Class cls)
        throws CtxException
    {
        Object ret = null;
        for (int i = 0; (ret == null) && (i < _types.length); i++)
        {
            if (!_types[i].isExplicit())
            {
                PProbe probe = _types[i].myProbe();
                ret = probe.valueFor(cls, _parms); //assumes that implicit also handles when desc is present
            }
        }
        return ret;
    }

    public void release(Object[] parms)
        throws CtxException
    {
        for (int i = 0; i < _types.length; i++)
            _types[i].myProbe().releaseValues(parms);
    }
}

