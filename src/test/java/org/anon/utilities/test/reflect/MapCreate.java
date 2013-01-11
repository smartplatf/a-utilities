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
 * File:                org.anon.utilities.test.reflect.MapCreate
 * Author:              rsankar
 * Revision:            1.0
 * Date:                30-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A creation for map related objects
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.lang.reflect.Field;
import static org.junit.Assert.*;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.reflect.ListItemContext;
import org.anon.utilities.reflect.MapItemContext;
import org.anon.utilities.reflect.CVisitor;
import org.anon.utilities.exception.CtxException;

public class MapCreate implements CVisitor
{
    private Map _values;

    public Map getValues() { return _values; }

    public MapCreate()
    {
        _values = new HashMap();
        Map<String, String> vals = new HashMap<String, String>();
        for (int i = 0; i < 10; i++)
            vals.put("key:" + i, "value:" + i);
        _values.put("_simpleMap", vals);
        Map simple = createSimple();
        Map msimple = new HashMap();
        for (int i = 0; i < 5; i++)
            msimple.put("key:" + i, simple);
        _values.put("_simpleObject", msimple);
        Map complex = createComplex();
        Map mcomplex = new HashMap();
        for (int i = 0; i < 5; i++)
        {
            Map msim = createSimple();
            mcomplex.put(msim, complex);
        }
        _values.put("_complexMap", mcomplex);
    }

    public Map createComplex()
    {
        Map values = new HashMap();
        values.put("_double", new Double(10.9));
        values.put("_dt", new Date());
        values.put("_simple", createSimple());
        values.put("_simpleDuplicate", createSimple());
        return values;
    }

    public Map createSimple()
    {
         Map values = new HashMap();
        values.put("_integer", new Integer(10));
        values.put("_string", "test");
        values.put("_decimal", new BigDecimal(20));
        values.put("_uuid", UUID.randomUUID());
        values.put("_float", new Float(10.0));
        return values;
    }

    public int collectionSize(DataContext ctx)
    {
        return 0;
    }

    public Set keySet(DataContext ctx)
    {
        if ((ctx.field() != null) && (_values.get(ctx.field().getName()) instanceof Map))
        {
            Map m = (Map)_values.get(ctx.field().getName());
            return m.keySet();
        }
        return null;
    }

    public Object visit(DataContext ctx)
        throws CtxException
    {
        if ((ctx.field() == null) && (!(ctx instanceof ListItemContext)) && (!(ctx instanceof MapItemContext)))
        {
            ctx.setCustom(_values); //this is the first one?
            return _values;
        }

        Map checkIn = null;
        if (ctx.getCustom() instanceof Map)
            checkIn = (Map)ctx.getCustom();
        if (ctx instanceof ListItemContext)
        {
            ListItemContext lctx = (ListItemContext)ctx;
            List vals = null;
            if (checkIn != null)
                vals = (List)checkIn.get(lctx.listField().getName());
            else if (ctx.getCustom() instanceof List)
                vals = (List)ctx.getCustom();
            if (vals != null)
            {
                Object val = vals.get(lctx.getCount());
                ctx.setCustom(val);
                return val;
            }

            return null;
        }
        else if (ctx instanceof MapItemContext)
        {
            MapItemContext mctx = (MapItemContext)ctx;
            Map vals = (Map)checkIn.get(mctx.mapField().getName());
            if (mctx.isVisitKey())
            {
                ctx.setCustom(mctx.key());
                return mctx.key();
            }

            Object val = vals.get(mctx.key());
            ctx.setCustom(val);
            return val;
        }

        if ((ctx.field() != null) && (checkIn.containsKey(ctx.field().getName())))
        {
            Object val = checkIn.get(ctx.field().getName());
            if ((val != null) && (type().isAssignable(val.getClass(), ctx.fieldType())))
                return val;

            if ((val instanceof Map))
            {
                ctx.setCustom(val);
                return val;
            }

            if (val instanceof Collection)
            {
                ctx.setCustom(val);
                return val;
            }
        }

        return null;
    }

    public void assertSimple(SimpleTestObject obj, Map values)
    {
        assertTrue(obj._integer == 10);
        assertTrue(obj._string.equals("test"));
        assertTrue(obj._decimal.equals(new BigDecimal(20)));
        assertTrue(obj._uuid.equals(values.get("_uuid")));
        assertTrue(obj._float == 10.0);
    }

    public void assertComplex(ComplexTestObject obj, Map values)
    {
        assertTrue(obj._double == 10.9);
        assertTrue(obj._dt.equals(values.get("_dt")));
        assertSimple(obj._simple, (Map)values.get("_simple"));
        assertSimple(obj._simpleDuplicate, (Map)values.get("_simpleDuplicate"));
    }

    public void assertObject(MapTestObject obj)
    {
        assertTrue(obj._simpleMap != null);
        assertTrue(obj._simpleMap.size() == 10);
        for (int i = 0; i < 10; i++)
        {
            assertTrue(obj._simpleMap.get("key:" + i) != null);
            assertTrue(obj._simpleMap.get("key:" + i).equals("value:" + i));
        }
        assertTrue(obj._simpleObject != null);
        assertTrue(obj._simpleObject.size() == 5);
        Map val = (Map)_values.get("_simpleObject");
        for (int i = 0; i < 5; i++)
        {
            assertTrue(obj._simpleObject.get("key:" + i) != null);
            assertSimple(obj._simpleObject.get("key:" + i), (Map)val.get("key:" + i));
        }
    }
}

