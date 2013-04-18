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
 * File:                org.anon.utilities.reflect.MapFromObject
 * Author:              rsankar
 * Revision:            1.0
 * Date:                22-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * a converter that converts from an object to a map
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.exception.CtxException;

public class MapFromObject implements TVisitor
{
    private Map<String, Object> _values;

    public MapFromObject()
    {
        _values = new HashMap<String, Object>();
    }

    public Object visit(DataContext data)
        throws CtxException
    {
        Object obj = data.getCustom();
        if ((obj == null) && (data.field() == null) && (data.before())) //the first time
        {
            //System.out.println("In start for: " + data.getObject() + ":");
            data.setCustom(_values);
            return data.getObject();
        }

        if ((obj instanceof Map) && (data.field() != null))
        {
            Map<String, Object> map = (Map<String, Object>)obj;
            Object put = handleValue(data);
            if (put != null) map.put(data.field().getName(), put);
            return data.fieldVal();
        }
        else if (obj instanceof Collection)
        {
            Collection coll = (Collection)obj;
            //System.out.println("In collection for: " + data.field() + ":" + data.getObject() + ":" + coll.size());
            Object o = handleValue(data);
            if (o != null) coll.add(o);
            return data.getObject();
        }

        return data.getObject();
    }

    private Object handleValue(DataContext data)
        throws CtxException
    {
        Class checkcls = data.fieldType();
        Object val = data.getObject();
        if (checkcls == null)
            checkcls = data.traversingClazz();
        if ((type().checkPrimitive(checkcls) || (type().isAssignable(checkcls, Map.class))))
        {
            return val;
        }

        if ((checkcls.equals(UUID.class)) || (checkcls.equals(Date.class)))
        {
            return convert().objectToString(val);
        }

        if ((data.before()) && (type().isAssignable(checkcls, Collection.class)))
        {
            List<Object> lst = new ArrayList<Object>();
            data.setCustom(lst);
            return lst;
        }

        if (data.before())
        {
            Map<String, Object> m = new HashMap<String, Object>();
            data.setCustom(m);
            return m;
        }

        return null;
    }

    public Map<String, Object> createdMap() { return _values; }
}

