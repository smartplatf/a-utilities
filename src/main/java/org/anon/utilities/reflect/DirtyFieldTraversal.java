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
 * File:                org.anon.utilities.reflect.DirtyFieldTraversal
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A dirty field traversal logic that traverses only modified fields
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.List;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import org.anon.utilities.serialize.srdr.DirtyField;
import org.anon.utilities.exception.CtxException;

public class DirtyFieldTraversal extends ObjectTraversal
{
    private List<DirtyField> _dirtyFields;

    public DirtyFieldTraversal(TVisitor visit, Object compare, Object original, boolean modify)
        throws CtxException
    {
        super(visit, compare, modify, null, original);
    }

    @Override
    protected void setupContext(Object primary, Object ... cotraverse)
        throws CtxException
    {
        _dirtyFields = serial().dirtyFields(primary, cotraverse[0]);
        
        /*if(_dirtyFields != null){
        	System.out.println("-------------------------------------------");
        	System.out.println("DIRTY FIELDS:"+_dirtyFields.size());
        	for(DirtyField fld : _dirtyFields)
        	{
        		System.out.println("Dfld:"+fld.getFieldName());
        	}
        	System.out.println("-------------------------------------------");
        }*/
        
        _context = new DFDataContext(_dirtyFields, primary, cotraverse);
    }

}

