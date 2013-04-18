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
 * File:                org.anon.utilities.test.reflect.ListCreate
 * Author:              rsankar
 * Revision:            1.0
 * Date:                29-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A creation for list involved objects
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
import org.anon.utilities.reflect.CVisitor;
import org.anon.utilities.exception.CtxException;

public class ListCreate implements CVisitor
{
    private Map _values;

    public Map getValues() { return _values; }

    public ListCreate()
    {
        _values = new HashMap();
        List simple = new ArrayList();
        Map val = createSimple();
        for (int i = 0; i < 10; i++)
            simple.add(val);
        _values.put("_simpleObjs", simple);
        List complex = new ArrayList();
        Map cval = createComplex();
        for (int i = 0; i < 5; i++)
            complex.add(cval);
        _values.put("_complexObjs", complex);
        List lcomplex = new ArrayList();
        lcomplex.add(complex);
        _values.put("_listObjs", lcomplex);
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

    public void assertObject(ListTestObject obj)
    {
        assertTrue(obj._simpleObjs != null);
        System.out.println("Simple objects: " + obj._simpleObjs.size());
        assertTrue(obj._simpleObjs.size() == 10);
        List simple = (List)_values.get("_simpleObjs");
        for (int i = 0; i < 10; i++)
            assertSimple(obj._simpleObjs.get(i), (Map)simple.get(i));
        assertTrue(obj._complexObjs != null);
        assertTrue(obj._complexObjs.size() == 5);
        List complex = (List)_values.get("_complexObjs");
        for (int i = 0; i < 5; i++)
            assertComplex(obj._complexObjs.get(i), (Map)complex.get(i));
        assertTrue(obj._listObjs != null);
        assertTrue(obj._listObjs.size() == 1);
        assertTrue(obj._listObjs.get(0) != null);
        assertTrue(obj._listObjs.get(0).size() == 5);
        List lcomplex = (List)_values.get("_listObjs");
        lcomplex = (List)lcomplex.get(0);
        for (int i = 0; i < 5; i++)
            assertComplex(obj._listObjs.get(0).get(i), (Map)lcomplex.get(i));
    }

    public int collectionSize(DataContext ctx)
    {
        int ret = 0;
        if (ctx.getCustom() instanceof Map)
        {
            Map check = (Map)ctx.getCustom();
            Field fld = ctx.field();
            if ((fld != null) && (check.containsKey(fld.getName())))
            {
                List lst = (List)check.get(fld.getName());
                ret = lst.size();
            }
        }
        else if ((ctx.getCustom() instanceof Collection) && (ctx instanceof ListItemContext))
        {
            //this is a listitemcontext
            Collection coll = (Collection)ctx.getCustom();
            ret = coll.size();
        }

        return ret;
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

    public Object visit(DataContext ctx)
        throws CtxException
    {
        if ((ctx.field() == null) && (!(ctx instanceof ListItemContext)))
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

    public Set keySet(DataContext ctx)
    {
        return null;
    }
}

