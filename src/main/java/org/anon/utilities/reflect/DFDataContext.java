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
 * File:                org.anon.utilities.reflect.DFDataContext
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A data context for dirty field traversal
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.lang.reflect.Field;

import org.anon.utilities.serialize.srdr.DirtyField;
import org.anon.utilities.exception.CtxException;

public class DFDataContext extends DataContext
{
    private Map<String, DirtyField> _dirtyFields;

    public DFDataContext(List<DirtyField> dflds, Object primary, Object ... traversing)
        throws CtxException
    {
        super(primary, traversing);
        setupDirtyFields(dflds);
    }

    public DFDataContext(DataContext pctx, String parPath, Field fld, Object primary, Object ... traversing)
        throws CtxException
    {
        super(pctx, parPath, fld, primary, traversing);
        if (pctx instanceof DFDataContext)
        {
            DFDataContext dpctx = (DFDataContext)pctx;
            DirtyField dfld = dpctx._dirtyFields.get(fld.getName());
            if (dfld != null)
                setupDirtyFields(dfld.getSubFields());
        }
    }

    private void setupDirtyFields(List<DirtyField> dflds)
    {
        _dirtyFields = new HashMap<String, DirtyField>();
        if (dflds != null)
        {
            for (DirtyField d : dflds)
            {
                _dirtyFields.put(d.getFieldName(), d);
            }
        }
    }

    @Override
    public boolean shouldTraverse(Field fld)
    {
        if (_dirtyFields.containsKey(fld.getName()))
            return true;

        return false;
    }

    @Override
    public DataContext createContext(DataContext pctx, String parPath, Field fld, Object primary, Object ... cotraverse)
        throws CtxException
    {
        DFDataContext ctx = new DFDataContext(pctx, parPath, fld, primary, cotraverse);
        return ctx;
    }
}

