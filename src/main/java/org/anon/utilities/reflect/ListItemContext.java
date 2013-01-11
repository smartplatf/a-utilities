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
 * File:                org.anon.utilities.reflect.ListItemContext
 * Author:              rsankar
 * Revision:            1.0
 * Date:                29-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A data context indicating list item
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.anon.utilities.exception.CtxException;

public class ListItemContext extends DataContext
{
    private Field _listField;
    private int _count;
    private Type _parameterizedType;

    public ListItemContext(Boolean b, Class cls, Type type, Field fld, int cnt)
        throws CtxException
    {
        super(b, cls);
        _listField = fld;
        _count = cnt;
        _parameterizedType = type;
    }

    public int getCount() { return _count; }
    public Field listField() { return _listField; }
    public Type getGenericType() { return _parameterizedType; }
}

