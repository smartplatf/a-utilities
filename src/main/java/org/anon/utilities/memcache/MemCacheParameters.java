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
 * File:                org.anon.utilities.memcache.MemCacheParameters
 * Author:              rsankar
 * Revision:            1.0
 * Date:                27-03-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of parameters based on which mem cache is created
 *
 * ************************************************************
 * */

package org.anon.utilities.memcache;

import org.anon.utilities.utils.RepeaterVariants;

public class MemCacheParameters<K, T> implements RepeaterVariants
{
    private int _limit;
    private ElementCreator<K, T> _creator;
    private ElementRemovalListener<K, T> _listener;

    public MemCacheParameters(int limit, ElementCreator<K, T> create, ElementRemovalListener<K, T> list)
    {
        _limit = limit;
        _creator = create;
        _listener = list;
    }

    public MemCacheParameters(int limit, ElementCreator<K, T> create)
    {
        this(limit, create, null);
    }

    public MemCacheParameters(int limit)
    {
        this(limit, null, null);
    }

    public int limit() { return _limit; }
    public ElementCreator<K, T> creator() { return _creator; }
    public ElementRemovalListener<K, T> listener() { return _listener; }
}

