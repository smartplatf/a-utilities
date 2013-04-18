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
 *
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.anon.utilities.test.cache.TestCache
 * Author:              rsankar
 * Revision:            1.0
 * Date:                27-03-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test for cache
 *
 * ************************************************************
 * */

package org.anon.utilities.test.cache;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.memcache.LimitedMemCache;
import org.anon.utilities.memcache.ElementCreator;
import org.anon.utilities.memcache.MemCacheParameters;
import org.anon.utilities.memcache.ElementRemovalListener;
import org.anon.utilities.exception.CtxException;

public class TestCache
{
    class CCacheElement implements ElementCreator<Integer, String>
    {
        public String createValue(Integer key)
            throws CtxException
        {
            System.out.println("Created: " + key);
            return key + "Testing";
        }
    }

    class CCacheRemove implements ElementRemovalListener<Integer, String>
    {
        public void removed(Integer key, String value)
            throws CtxException
        {
            System.out.println("Removed: " + key + ":" + value);
        }
    }

    @Test
    public void testTestCache()
        throws Exception
    {
        LimitedMemCache<Integer, String> cache = cache().create(10, new CCacheElement());
        for (int i = 0; i < 50; i++)
            cache.get(i);

        System.out.println("Starting with removal listener.");
        MemCacheParameters<Integer, String> parms = new MemCacheParameters(10, new CCacheElement(), new CCacheRemove());
        cache = cache().create(parms);
        for (int i = 0; i < 50; i++)
            cache.get(i);
    }
}

