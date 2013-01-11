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
 * File:                org.anon.utilities.objservices.PoolService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A pool service that provides pool related functions
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import org.anon.utilities.crosslink.CrossLinkAny;
import org.anon.utilities.exception.CtxException;

public class PoolService extends ObjectServiceLocator.ObjectService
{
    public PoolService()
    {
        super();
    }

    public Object createPool(Class cls)
        throws CtxException
    {
        //This should create a pool in the same classloader as the class.
        //ClassLoader ldr = cls.getClassLoader();
        CrossLinkAny cl = new CrossLinkAny("org.anon.utilities.pool.EntityPool");
        return cl.create(cls);
    }
}

