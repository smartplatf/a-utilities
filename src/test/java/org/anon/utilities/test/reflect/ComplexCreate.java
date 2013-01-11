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
 * File:                org.anon.utilities.test.reflect.ComplexCreate
 * Author:              rsankar
 * Revision:            1.0
 * Date:                29-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A create for the complex object
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

import static org.junit.Assert.*;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.reflect.CVisitor;
import org.anon.utilities.exception.CtxException;

public class ComplexCreate implements CVisitor
{
    private Map _values;

    public ComplexCreate()
    {
        _values = new HashMap();
        _values.put("_double", new Double(10.9));
        _values.put("_dt", new Date());
        _values.put("_simple", createSimple());
        _values.put("_simpleDuplicate", createSimple());
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

    public void assertSimple(SimpleTestObject obj, Map values)
    {
        assertTrue(obj._integer == 10);
        assertTrue(obj._string.equals("test"));
        assertTrue(obj._decimal.equals(new BigDecimal(20)));
        assertTrue(obj._uuid.equals(values.get("_uuid")));
        assertTrue(obj._float == 10.0);
    }

    public void assertObject(ComplexTestObject obj)
    {
        assertTrue(obj._double == 10.9);
        assertTrue(obj._dt.equals(_values.get("_dt")));
        assertSimple(obj._simple, (Map)_values.get("_simple"));
        assertSimple(obj._simpleDuplicate, (Map)_values.get("_simpleDuplicate"));
    }

    public Object visit(DataContext ctx)
        throws CtxException
    {
        if (ctx.field() == null)
        {
            ctx.setCustom(_values); //this is the first one?
            return _values;
        }
        Map checkIn = (Map)ctx.getCustom();
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
        }

        return null;
    }

    public int collectionSize(DataContext ctx)
    {
        return 0;
    }

    public Set keySet(DataContext ctx)
    {
        return null;
    }

}

