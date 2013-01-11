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
 * File:                org.anon.utilities.test.reflect.SimpleOT
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * a traversal of simple object
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.anon.utilities.reflect.TVisitor;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.exception.CtxException;

public class SimpleOT implements TVisitor
{
    private Map<String, List<Object>> _traversed;

    public SimpleOT()
    {
        _traversed = new HashMap<String, List<Object>>();
    }

    public Object visit(DataContext ctx)
        throws CtxException
    {
        Object ret = null;
        List<Object> lst = null;
        if ((ctx.field() != null) && (ctx.before()))
        {
            lst = _traversed.get(ctx.field().getName());
            if (lst == null)
                lst = new ArrayList<Object>();
            lst.add(ctx.fieldVal());
            _traversed.put(ctx.field().getName(), lst);
            ret = ctx.fieldVal();
        }
        else if ((ctx.field() == null) && (ctx.before()))
        {
            lst = _traversed.get(ctx.traversingClazz().getName());
            if (lst == null)
                lst = new ArrayList<Object>();
            lst.add(ctx.traversingObject());

            _traversed.put(ctx.traversingClazz().getName(), lst);
            ret = ctx.traversingObject();
        }

        return ret;
    }

    public Map<String, List<Object>> getTraversed()
    {
        return _traversed;
    }
}

