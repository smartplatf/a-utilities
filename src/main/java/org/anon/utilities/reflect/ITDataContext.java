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
 * File:                org.anon.utilities.reflect.ITDataContext
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A data context that ignores transient fields
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.anon.utilities.exception.CtxException;

public class ITDataContext extends DataContext
{
    public ITDataContext(Object primary, Object ... traversing)
        throws CtxException
    {
        super(primary, traversing);
    }

    public ITDataContext(DataContext pctx, String parPath, Field fld, Object primary, Object ... traversing)
        throws CtxException
    {
        super(pctx, parPath, fld, primary, traversing);
    }

    @Override
    public boolean shouldTraverse(Field fld) 
    { 
        int mod = fld.getModifiers();
        boolean ret = (!fld.isSynthetic());
        ret = ret && (!Modifier.isTransient(mod) && !Modifier.isStatic(mod));
        return ret; 
    }

    @Override
    public DataContext createContext(DataContext pctx, String parPath, Field fld, Object primary, Object ... cotraverse)
        throws CtxException
    {
        DataContext ctx = new ITDataContext(pctx, parPath, fld, primary, cotraverse);
        return ctx;
    }

    @Override
    public DataContext createContext(Object primary, Object ... cotraverse)
        throws CtxException
    {
        return new ITDataContext(primary, cotraverse);
    }
}

