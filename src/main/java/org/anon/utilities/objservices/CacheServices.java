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
 * File:                org.anon.utilities.objservices.CacheServices
 * Author:              rsankar
 * Revision:            1.0
 * Date:                27-03-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of services for cache related operation
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import org.anon.utilities.memcache.ElementCreator;
import org.anon.utilities.memcache.LimitedMemCache;
import org.anon.utilities.memcache.MemCacheParameters;
import org.anon.utilities.memcache.guava.GuavaMemCache;
import org.anon.utilities.exception.CtxException;

public class CacheServices extends ObjectServiceLocator.ObjectService
{
    public CacheServices()
    {
        super();
    }

    public <K, V> LimitedMemCache<K, V> create(MemCacheParameters<K, V> parms)
        throws CtxException
    {
        //For now the assumption is the only implementation
        LimitedMemCache<K, V> cache = new GuavaMemCache<K, V>();
        if (parms.creator() != null)
            cache.setupCreator(parms.creator());
        if (parms.listener() != null)
            cache.setupRemovalListener(parms.listener());

        cache.initialize(parms.limit());
        return cache;
    }

    public <K, T> LimitedMemCache<K, T> create(int limit)
        throws CtxException
    {
        MemCacheParameters<K, T> parms = new MemCacheParameters<K, T>(limit);
        return create(parms);
    }

    public <K, T> LimitedMemCache<K, T> create(int limit, ElementCreator<K, T> create)
        throws CtxException
    {
        MemCacheParameters<K, T> parms = new MemCacheParameters<K, T>(limit, create);
        return create(parms);
    }
}

