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
 * File:                org.anon.utilities.reflect.MapItemContext
 * Author:              rsankar
 * Revision:            1.0
 * Date:                30-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A context for map related objects
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.anon.utilities.exception.CtxException;

public class MapItemContext extends DataContext
{
    private Field _mapField;
    private Object _key;
    private Type _parameterizedType;
    private boolean _visitKey;

    public MapItemContext(Boolean b, Class cls, Type t, Field fld, Object k, boolean vkey)
        throws CtxException
    {
        super(b, cls);
        _mapField = fld;
        _parameterizedType = t;
        _key = k;
        _visitKey = vkey;
    }

    public Field mapField() { return _mapField; }
    public Object key() { return _key; }
    public Type getGenericType() { return _parameterizedType; }
    public boolean isVisitKey() { return _visitKey; }
}

