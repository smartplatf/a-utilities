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
 * File:                org.anon.utilities.lang.json.JSONCreator
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A visitor that creates JSON from an object
 *
 * ************************************************************
 * */

package org.anon.utilities.lang.json;

import java.util.UUID;
import java.util.Date;
import java.util.Collection;

import net.sf.json.JSON;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.reflect.TVisitor;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.exception.CtxException;

public class JSONCreator implements TVisitor
{
    class CustomData
    {
        JSON _current;
        JSON _parent;
    }

    private JSON _created;

    public JSONCreator()
    {
    }

    private JSON createJSONFor(Class cls)
    {
        JSON ret = null;
        if ((type().isAssignable(cls, Collection.class)) || (cls.isArray()))
            ret = new JSONArray();
        else
            ret = new JSONObject();
        return ret;
    }

    private void addTo(JSON json, Object k, Object o)
    {
        if (json instanceof JSONArray)
        {
            JSONArray a = (JSONArray)json;
            a.add(o);
        }
        else if (json instanceof JSONObject)
        {
            JSONObject jo = (JSONObject)json;
            jo.put(k, o);
        }
    }

    private CustomData customData(JSON j, DataContext data)
    {
        CustomData d = new CustomData();
        d._current = j;
        CustomData cd = (CustomData)data.getCustom();
        if (cd != null)
            d._parent = (JSON)cd._current;
        return d;
    }

    public Object visit(DataContext data)
        throws CtxException
    {
        Object ret = null;
        if (data.field() == null)
        {
            Class cls = data.traversingClazz();
            if (data.before())
            {
                if (type().checkPrimitive(cls))
                {
                    CustomData cd = (CustomData)data.getCustom();
                    if ((cd != null) && (cd._parent != null))
                        addTo(cd._parent, data.getType(), data.traversingObject());
                }
                else
                {
                    JSON jo = createJSONFor(cls);
                    data.setCustom(customData(jo, data));
                    ret = data.traversingObject();
                    if (_created == null)
                        _created = jo; //assign the first
                }
            }
            else if (data.after())
            {
                CustomData cd = (CustomData)data.getCustom();
                if ((cd != null) && (cd._parent != null))
                    addTo(cd._parent, data.getType(), cd._current);
            }
        }
        else
        {
            CustomData cd = (CustomData)data.getCustom();
            assertion().assertNotNull(cd, "There has been a problem creating json string");
            JSON json = (JSON)cd._current;
            ret = data.fieldVal();
            assertion().assertNotNull(json, "There has been a problem creating json string");
            Object val = ret;
            if ((val != null) && (data.before()))
            {
                boolean add = true;
                Object put = val;
                if (val instanceof Date)
                {
                    put = convert().dateToString((Date)val, null);
                }
                else if (val instanceof UUID)
                {
                    put = val.toString();
                }
                else if (!type().checkPrimitive(val.getClass()))
                {
                    put = createJSONFor(val.getClass());
                    data.setCustom(customData((JSON)put, data));
                    add = false;
                }

                if (add) addTo(json, data.field().getName(), put);
            }
            else if ((val == null) && data.before())
            {
                addTo(json, data.field().getName(), JSONNull.getInstance());
            }
            else if (data.after())
            {
                addTo(cd._parent, data.field().getName(), cd._current);
            }
        }

        return ret;
    }

    public JSON json() { return _created; }
}

