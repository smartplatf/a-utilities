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
 * File:                org.anon.utilities.reflect.ObjectType
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A type that handles any object traversal
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.Map;
import java.util.List;
import java.util.Collection;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.utils.RepeaterVariants;
import org.anon.utilities.exception.CtxException;

public class ObjectType extends RepeatableType
{
    private static Map<Class, Field[]> _fields = new ConcurrentHashMap<Class, Field[]>();

    public ObjectType()
    {
    }

    @Override
    protected RepeatableType createme()
    {
        return new ObjectType();
    }

    @Override
    public boolean handles(Class check)
        throws CtxException
    {
        return (!type().checkPrimitive(check) && (!type().checkStandard(check))
                && (!(type().isAssignable(check, Collection.class))) && (!(type().isAssignable(check, Map.class))));
    }

    @Override
    protected Object traverse(Traversal traverse, DataContext pctx, TVisitor visit, boolean mod, List<ObjectTraversal.myTraverser> at, 
            Class cls, Object primary, Object ... cotraverse)
        throws CtxException
    {
        try
        {
            Field[] flds = _fields.get(cls);
            if (flds == null)
            {
                flds = cls.getDeclaredFields();
                _fields.put(cls, flds);
            }
            for (int i = 0; i < flds.length; i++)
            {
                if (pctx.shouldTraverse(flds[i]))
                {
                    DataContext ctx = pctx.createContext(pctx, pctx.fieldpath(), flds[i], primary, cotraverse);
                    ctx.setCustom(pctx.getCustom());
                    traverse(traverse, ctx, visit, mod, at);
                }
            }

            if (cls.getSuperclass() != null)
                traverse(traverse, pctx, visit, mod, at, cls.getSuperclass(), primary, cotraverse);
        }
        catch (Exception e)
        {
            except().rt(this, e, new CtxException.Context("traverseSimpleObject", "Error:" + cls.getName() + ":" + primary));
        }

        return primary;
    }
}

