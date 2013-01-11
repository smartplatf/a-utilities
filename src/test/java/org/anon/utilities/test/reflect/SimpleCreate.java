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
 * File:                org.anon.utilities.test.reflect.SimpleCreate
 * Author:              rsankar
 * Revision:            1.0
 * Date:                29-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A simple creator visitor
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.reflect.CVisitor;
import org.anon.utilities.exception.CtxException;

public class SimpleCreate implements CVisitor
{
    private Map _values;

    public SimpleCreate()
    {
        _values = new HashMap();
        _values.put("_integer", new Integer(10));
        _values.put("_string", "test");
        _values.put("_decimal", new BigDecimal(20));
        _values.put("_uuid", UUID.randomUUID());
        _values.put("_float", new Float(10.0));
    }

    public int collectionSize(DataContext ctx)
    {
        return 0;
    }

    public Object visit(DataContext ctx)
        throws CtxException
    {
        if ((ctx.field() != null) && (_values.containsKey(ctx.field().getName())))
            return _values.get(ctx.field().getName());

        return _values;
    }

    public void assertObject(SimpleTestObject obj)
        throws Exception
    {
        assertTrue(obj._integer == 10);
        assertTrue(obj._string.equals("test"));
        assertTrue(obj._decimal.equals(new BigDecimal(20)));
        assertTrue(obj._uuid.equals(_values.get("_uuid")));
        assertTrue(obj._float == 10.0);
    }

    public Set keySet(DataContext ctx)
    {
        return null;
    }
}

