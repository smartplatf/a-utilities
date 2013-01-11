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
 * File:                org.anon.utilities.reflect.MapClass
 * Author:              rsankar
 * Revision:            1.0
 * Date:                29-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A class traversal for a Map
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import org.anon.utilities.exception.CtxException;

public class MapClass extends MapType
{
    public MapClass()
    {
        super();
        _object = false;
    }

    @Override
    protected RepeatableType createme()
    {
        return new MapClass();
    }

    @Override
    protected Object traverse(Traversal traverse, DataContext pctx, TVisitor visit, boolean mod, List<ObjectTraversal.myTraverser> at, 
            Class cls, Object primary, Object ... cotraverse)
        throws CtxException
    {
        Map ret = new HashMap(); //we need to assume that the custom visitor will populate correctly?

        Field fld = pctx.field();
        Type type = null;
        if (fld != null)
        {
            type = fld.getGenericType();
        }
        else if (pctx instanceof MapItemContext)
        {
            type = ((MapItemContext)pctx).getGenericType();
        }
        Type kconvert = null;
        Class ksend = null;
        Type vconvert = null;
        Class vsend = null;
        //do key first
        if ((type != null) && (type instanceof ParameterizedType))
        {
            ParameterizedType pt = (ParameterizedType)type;
            kconvert = pt.getActualTypeArguments()[0];
            if (kconvert instanceof ParameterizedType)
                ksend = (Class)((ParameterizedType)kconvert).getRawType();
            else
                ksend = (Class)kconvert;

            vconvert = pt.getActualTypeArguments()[1];
            if (vconvert instanceof ParameterizedType)
                vsend = (Class)((ParameterizedType)vconvert).getRawType();
            else
                vsend = (Class)vconvert;
        }

        Set keys = ((CVisitor)visit).keySet(pctx);
        if (keys != null)
        {
            for (Object key : keys)
            {
                MapItemContext kctx = new MapItemContext(true, ksend, kconvert, pctx.field(), key, true);
                kctx.setParentPath(pctx.fieldpath());
                kctx.setCustom(pctx.getCustom());
                Object k = traverse(traverse, kctx, visit, mod, at);

                MapItemContext vctx = new MapItemContext(true, vsend, vconvert, pctx.field(), key, false);
                vctx.setParentPath(pctx.fieldpath());
                vctx.setCustom(pctx.getCustom());
                Object v = traverse(traverse, vctx, visit, mod, at);
                ret.put(k, v);
            }
        }

        return ret;
    }
}

