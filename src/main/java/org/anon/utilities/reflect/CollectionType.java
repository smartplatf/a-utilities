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
 * File:                org.anon.utilities.reflect.CollectionType
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A handler for collection type of field
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.List;
import java.util.Collection;
import java.lang.reflect.Array;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class CollectionType extends RepeatableType
{
    public CollectionType()
    {
    }

    @Override
    protected RepeatableType createme()
    {
        return new CollectionType();
    }

    @Override
    public boolean handles(Class check)
        throws CtxException
    {
        return (type().isAssignable(check, Collection.class) ||
                (check.isArray()));
    }

    @Override
    protected Object traverse(Traversal traverse, DataContext pctx, TVisitor visit, boolean mod, List<ObjectTraversal.myTraverser> at, 
            Class cls, Object primary, Object ... cotraverse)
        throws CtxException
    {
        //for now just traverse the primary
        if (cls.isArray())
        {
            int len = Array.getLength(primary);
            for (int i = 0; i < len; i++)
            {
                Object o = Array.get(primary, i);
                DataContext ctx = pctx.createContext(o);
                ctx.setType("" + i);
                ctx.setCustom(pctx.getCustom());
                ctx.setParentPath(pctx.fieldpath());
                Object modified = traverse(traverse, ctx, visit, mod, at);
            }
            return primary;
        }
        else
        {
            Collection coll = (Collection)primary;
            int cnt = 0;
            for (Object o : coll)
            {
                DataContext ctx = pctx.createContext(o);
                ctx.setType("" + cnt);
                ctx.setCustom(pctx.getCustom());
                ctx.setParentPath(pctx.fieldpath()+"."+ctx.getType());
                //assumption is that the mod is the same as o, so shd be fine.
                //Does not traverse the cotraverse
                Object modified = traverse(traverse, ctx, visit, mod, at);
                cnt++;
            }
            return coll;
        }
    }
}

