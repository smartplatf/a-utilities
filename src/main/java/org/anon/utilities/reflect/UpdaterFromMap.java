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
 * File:                org.anon.utilities.reflect.UpdaterFromMap
 * Author:              vjaasti
 * Revision:            1.0
 * Date:                May 15, 2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * <Purpose>
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.Map;
import java.util.Set;

import org.anon.utilities.exception.CtxException;

public class UpdaterFromMap extends CreatorFromMap {

	private Map _values;

    public UpdaterFromMap(Map values)
    {
    	super(values);
        _values = values;
    }
	

    @Override
    public Object visit(DataContext ctx)
            throws CtxException
    {
        //ctx.setCustom(_values);
        Object ret = handleFirst(ctx);
    	if((ctx.field() != null) && (ctx.getCustom() instanceof Map) && (((Map)ctx.getCustom()).get(ctx.field().getName()) != null )) //dont do anything if field is not in map
    	{
    	     
            
            if((ctx.field() != null))
            {
    	        if (ret != null) return ret;
    		if (ctx instanceof ListItemContext)
    		    return handleListItem((ListItemContext)ctx);
    		else if (ctx instanceof MapItemContext)
    		    return handleMapItem((MapItemContext)ctx);

    		return handleDefault(ctx);
            }
    	}
    		
    	else if(ctx.field() != null) //if not in update map 
    	{
    			return null; //return null if not in update Map
    	}
    		
    		
    		return ctx.traversingObject();
        }
}
