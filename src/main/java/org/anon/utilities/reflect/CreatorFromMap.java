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
 * File:                org.anon.utilities.reflect.CreatorFromMap
 * Author:              rsankar
 * Revision:            1.0
 * Date:                30-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A visitor that creates object from maps
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.Collection;
import java.lang.reflect.Field;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class CreatorFromMap implements CVisitor
{
    private Map _values;

    public CreatorFromMap(Map values)
    {
        _values = values;
    }

    public int collectionSize(DataContext ctx)
    {
        int ret = 0;
        if (ctx.getCustom() instanceof Map)
        {
            Map check = (Map)ctx.getCustom();
            Field fld = ctx.field();
            
            /*if ((fld != null) && (check.containsKey(fld.getName())))
            {
                Object val = check.get(fld.getName());
                Collection lst = convert().objectToCollection(val, true);
                ret = lst.size();
            }*/
            if(fld != null)
            {
            	ret = convert().collectionSizeFromMap(check, fld.getName());
            }
        }
        else if (ctx instanceof ListItemContext)
        {
            //this is a listitemcontext
            Collection coll = convert().objectToCollection(ctx.getCustom(), false);
            if (coll != null)
                ret = coll.size();
        }

        return ret;
    }

    public Set keySet(DataContext ctx)
    {
        Object cust = ctx.getCustom();
        if ((ctx.field() != null) && (cust != null) && (cust instanceof Map) 
                && (((Map)cust).get(ctx.field().getName()) instanceof Map))
        {
            Map m = (Map)((Map)cust).get(ctx.field().getName());
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
            ctx.setCustom(_values);
            return _values;
        }

        return null;
    }

    protected Map getContextMap(DataContext ctx)
    {
        Map checkIn = null;
        if (ctx.getCustom() instanceof Map)
            checkIn = (Map)ctx.getCustom();
        return checkIn;
    }

    protected Object handleListItem(ListItemContext lctx)
    {
        Map checkIn = getContextMap(lctx);
        List vals = null;
        if (checkIn != null)
        {
        	//Get Map for ListItem
            vals = (List)convert().objectToCollection(checkIn.get(lctx.listField().getName()), true);
        }
        else if (lctx.getCustom() instanceof List)
        {
            vals = (List)lctx.getCustom();
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
        Map checkIn = getContextMap(mctx);
        Map vals = (Map)checkIn.get(mctx.mapField().getName());
        if (mctx.isVisitKey())
        {
            mctx.setCustom(mctx.key());
            return mctx.key();
        }

        Object val = vals.get(mctx.key());
        mctx.setCustom(val);
        return val;
    }

    protected Object handleDefault(DataContext ctx)
    {
    	 Map checkIn = getContextMap(ctx);
    	 String key = ctx.traversingClazz().getSimpleName()+"."+ctx.fieldpath();
    	  
         if ((ctx.field() != null) && (checkIn != null) && (checkIn.containsKey(ctx.field().getName())))
    	{
            Object val = checkIn.get(ctx.field().getName());
    		if ((val != null) && (type().isAssignable(val.getClass(), ctx.fieldType())))
            {
            	return val;
            }
           
            if ((val != null) && (val instanceof Map))
            {
                ctx.setCustom(val);
                return val;
            }

            if ((val != null) && ((val instanceof Collection) || (val.getClass().isArray())))
            {
                ctx.setCustom(val);
                return val;
            }
        }

        return null;
    }
}

