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
 * File:                org.anon.utilities.reflect.MapType
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A handler for map type of field
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.Map;
import java.util.List;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class MapType extends RepeatableType
{
    public MapType()
    {
    }

    @Override
    protected RepeatableType createme()
    {
        return new MapType();
    }

    @Override
    public boolean handles(Class check)
        throws CtxException
    {
        return (type().isAssignable(check, Map.class));
    }

    @Override
    protected Object traverse(Traversal traverse, DataContext pctx, TVisitor visit, boolean mod, List<ObjectTraversal.myTraverser> at, 
            Class cls, Object primary, Object ... cotraverse)
        throws CtxException
    {
        Map map = (Map)primary;
        int len = 0;
        if (cotraverse != null)
            len = cotraverse.length;
        Map[] comap = new Map[len];
        for (int i = 0; i < len; i++)
            comap[i] = (Map)cotraverse[i];

        //only will traverse the elements in primary, the keys in cotraverse is not traversed??
        int cnt = 0;
        for (Object key : map.keySet())
        {
            Object val = map.get(key);
            DataContext kctx = pctx.createContext(key);
            kctx.setType("Key-" + cnt);
            kctx.setCustom(pctx.getCustom());
            kctx.setParentPath(pctx.fieldpath());
            traverse(traverse, kctx, visit, mod, at);
            Object[] covals = new Object[len];
            for (int i = 0; i < len; i++)
                covals[i] = comap[i].get(key);
            DataContext ctx = pctx.createContext(val, covals);
            ctx.setType("Value-" + cnt);
            ctx.setCustom(pctx.getCustom());
            ctx.setParentPath(pctx.fieldpath());
            //assumption is that the mod is the same as o, so shd be fine.
            //Does not traverse the cotraverse
            traverse(traverse, ctx, visit, mod, at);
            cnt++;
        }
        return map;
    }
}

