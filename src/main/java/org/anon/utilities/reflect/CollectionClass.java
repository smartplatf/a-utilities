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
 * File:                org.anon.utilities.reflect.CollectionClass
 * Author:              rsankar
 * Revision:            1.0
 * Date:                29-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A class to handle collection creation
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class CollectionClass extends CollectionType
{
    public CollectionClass()
    {
        super();
        _object = false;
    }

    @Override
    protected RepeatableType createme()
    {
        return new CollectionClass();
    }

    @Override
    protected Object traverse(Traversal traverse, DataContext pctx, TVisitor visit, boolean mod, List<ObjectTraversal.myTraverser> at, 
            Class cls, Object primary, Object ... cotraverse)
        throws CtxException
    {
        List ret = new ArrayList();
        Field fld = pctx.field();
        Type type = null;
        if (fld != null)
        {
            type = fld.getGenericType();
        }
        else if (pctx instanceof ListItemContext)
        {
            type = ((ListItemContext)pctx).getGenericType();
        }
        Type convert = null;
        Class send = null;
        if ((type != null) && (type instanceof ParameterizedType))
        {
            ParameterizedType pt = (ParameterizedType)type;
            convert = pt.getActualTypeArguments()[0];
            if (convert instanceof ParameterizedType)
            {
                send = (Class)((ParameterizedType)convert).getRawType();
            }
            else
                send = (Class)convert;
        }

        if (convert == null)
        {
            except().te(this, "Cannot traverse a class with generic lists " + type  + ":" + pctx);
        }

        int sz = ((CVisitor)visit).collectionSize(pctx);
        for (int i = 0; i < sz; i++)
        {
            ListItemContext ctx = new ListItemContext(true, send, convert, pctx.field(), i);
            ctx.setParentPath(pctx.fieldpath());
            ctx.setCustom(pctx.getCustom());
            Object obj = traverse(traverse, ctx, visit, mod, at);
            ret.add(obj);
        }

        return ret;
    }
}

