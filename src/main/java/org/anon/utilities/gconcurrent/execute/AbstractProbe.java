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
 * File:                org.anon.utilities.gconcurrent.execute.AbstractProbe
 * Author:              rsankar
 * Revision:            1.0
 * Date:                17-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An abstract probe implementation
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent.execute;

import java.util.List;
import java.lang.reflect.Field;

import static org.anon.utilities.services.ServiceLocator.*;

import org.anon.utilities.exception.CtxException;

public abstract class AbstractProbe implements PProbe
{
    public AbstractProbe()
    {
    }

    public Object valueFor(Class cls, ProbeParms parms, PDescriptor desc)
        throws CtxException
    {
        Object ret = valueFor(parms, desc);
        if ((ret != null) && type().isAssignable(ret.getClass(), cls))
            return ret;

        return null;
    }

    public Object valueFor(Class cls, ProbeParms parms)
        throws CtxException
    {
        Object ret = null;
        List<Object> possible = parms.possibleParms();
        for (Object obj : possible)
        {
            if ((obj != null) && type().isAssignable(obj.getClass(), cls))
                ret = obj;
        }

        return ret;
    }

    protected abstract Object valueFor(ProbeParms parms)
        throws CtxException;

    public Object valueFor(ProbeParms parms, PDescriptor desc)
        throws CtxException
    {
        Object val = valueFor(parms);
        assertion().assertNotNull(desc, "Cannot call this function with a null desc.");
        String attribute = desc.attribute();
        if ((attribute != null) && (val != null))
        {
            Field fld = reflect().getAnyField(val.getClass(), attribute);
            if (fld != null)
            {
                try
                {
                    fld.setAccessible(true);
                    val = fld.get(val);
                }
                catch(Exception e)
                {
                    except().rt(e, new CtxException.Context("AbstractProbe.valueFor" + attribute, "Exception"));
                }
            }
            else
                except().te(this, "Problem accessing attribute: " + attribute + ":" + val);
        }
        return val;
    }

    public void releaseValues(Object[] val)
        throws CtxException
    {
    }
}

