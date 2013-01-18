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
 * File:                org.anon.utilities.gconcurrent.DefaultNodeDetails
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of default node values
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;

import org.anon.utilities.gconcurrent.execute.PDescriptor;
import org.anon.utilities.exception.CtxException;

public class DefaultNodeDetails implements NodeDetails
{
    private String _className;
    private String _methodName;
    private String _paramDesc;
    private List<PDescriptor> _descriptors;

    public DefaultNodeDetails(Class cls, Method mthd, String pdesc)
        throws CtxException
    {
        _className = cls.getName();
        _methodName = mthd.getName();
        _paramDesc = pdesc;
        if ((pdesc != null) && (pdesc.length() > 0))
        {
            _descriptors = PDescriptor.parseParamDesc(pdesc);
        }
        else
        {
            _descriptors = new ArrayList<PDescriptor>();
        }
    }

    public String clazzName()
    {
        return _className;
    }

    public String methodName()
    {
        return _methodName;
    }

    public String paramDesc()
    {
        return _paramDesc;
    }

    public List<PDescriptor> pdescriptors()
    {
        return _descriptors;
    }

    public PDescriptor descriptorFor(int p)
    {
        if (p < _descriptors.size())
            return _descriptors.get(p);

        return null;
    }
}

