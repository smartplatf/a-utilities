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
 * File:                org.anon.utilities.lang.json.ObjectCreator
 * Author:              rsankar
 * Revision:            1.0
 * Date:                10-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A creator of object from json
 *
 * ************************************************************
 * */

package org.anon.utilities.lang.json;

import java.util.Set;
import java.lang.reflect.Field;

import net.sf.json.JSON;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.reflect.CVisitor;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.reflect.MapItemContext;
import org.anon.utilities.reflect.ListItemContext;
import org.anon.utilities.exception.CtxException;

public class ObjectCreator implements CVisitor
{
    private JSON _json;

    public ObjectCreator(JSON json)
    {
        _json = json;
    }

    public int collectionSize(DataContext ctx)
    {
        int ret = 0;
        if (ctx.getCustom() instanceof JSONObject)
        {
            JSONObject check = (JSONObject)ctx.getCustom();
            Field fld = ctx.field();
            if ((fld != null) && (check.containsKey(fld.getName())))
            {
                Object val = check.get(fld.getName());
                if (val instanceof JSONArray)
                    ret = ((JSONArray)val).size();
            }
        }
        else if (ctx instanceof ListItemContext)
        {
            //this is a listitemcontext
            Object cust = ctx.getCustom();
            if ((cust != null) && (cust instanceof JSONArray))
                ret = ((JSONArray)cust).size();
        }

        return ret;
    }

    public Set keySet(DataContext ctx)
    {
        Object cust = ctx.getCustom();
        if ((ctx.field() != null) && (cust != null) && (cust instanceof JSONObject) 
                && (((JSONObject)cust).get(ctx.field().getName()) instanceof JSONObject))
        {
            JSONObject m = (JSONObject)((JSONObject)cust).get(ctx.field().getName());
            return m.keySet();
        }
        return null;
    }

    public Object visit(DataContext ctx)
        throws CtxException
    {
        Object ret = handleFirst(ctx);
        if (ret != null) return ret;

        if (ctx instanceof ListItemContext)
            return handleListItem((ListItemContext)ctx);
        else if (ctx instanceof MapItemContext)
            return handleMapItem((MapItemContext)ctx);

        return handleDefault(ctx);
    }

    private Object handleFirst(DataContext ctx)
    {
        if ((ctx.field() == null) && (!(ctx instanceof ListItemContext)) && (!(ctx instanceof MapItemContext)))
        {
            ctx.setCustom(_json);
            return _json;
        }

        return null;
    }

    private JSONObject getContextJSON(DataContext ctx)
    {
        JSONObject checkIn = null;
        if (ctx.getCustom() instanceof JSONObject)
            checkIn = (JSONObject)ctx.getCustom();
        return checkIn;
    }

    private Object handleListItem(ListItemContext lctx)
    {
        JSONObject checkIn = getContextJSON(lctx);
        JSONArray vals = null;
        if (checkIn != null)
        {
            Object g = checkIn.get(lctx.listField().getName());
            if (g instanceof JSONArray)
            {
                vals = (JSONArray)g;
            }
            else
            {
                vals = new JSONArray();
                vals.add(g);
            }
        }
        else if (lctx.getCustom() instanceof JSONArray)
        {
            vals = (JSONArray)lctx.getCustom();
        }

        if (vals != null)
        {
            Object val = vals.get(lctx.getCount());
            lctx.setCustom(val);
            return val;
        }

        return null;
    }

    private Object handleMapItem(MapItemContext mctx)
    {
        JSONObject checkIn = getContextJSON(mctx);
        JSONObject vals = (JSONObject)checkIn.get(mctx.mapField().getName());
        if (mctx.isVisitKey())
        {
            mctx.setCustom(mctx.key());
            return mctx.key();
        }

        Object val = vals.get(mctx.key());
        mctx.setCustom(val);
        return val;
    }

    private Object handleDefault(DataContext ctx)
        throws CtxException
    {
        JSONObject checkIn = getContextJSON(ctx);
        if ((ctx.field() != null) && (checkIn != null) && (checkIn.containsKey(ctx.field().getName())))
        {
            Object val = checkIn.get(ctx.field().getName());
            if ((val != null) && (type().isAssignable(val.getClass(), ctx.fieldType())))
                return val;

            if ((val != null) && (val instanceof JSONObject))
            {
                ctx.setCustom(val);
                return val;
            }

            if ((val != null) && ((val instanceof JSONArray) || (val.getClass().isArray())))
            {
                ctx.setCustom(val);
                return val;
            }

            if ((val != null) && (val instanceof JSONNull))
                return null;

            if (val != null)
            {
                return convert().stringToClass(val.toString(), ctx.fieldType());
            }
        }

        return null;
    }
}

