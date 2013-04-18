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
 * File:                org.anon.utilities.memcache.LimitedMemCache
 * Author:              rsankar
 * Revision:            1.0
 * Date:                27-03-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A memory based cache that is limited by parameters
 *
 * ************************************************************
 * */

package org.anon.utilities.memcache;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.exception.CtxException;

public interface LimitedMemCache<K, V> extends Repeatable
{
    public V get(K key)
        throws CtxException;
    public void put(K key, V value)
        throws CtxException;
    public void invalidate(K key)
        throws CtxException;
    public void setupCreator(ElementCreator<K, V> creator)
        throws CtxException;
    public void setupRemovalListener(ElementRemovalListener<K, V> remover)
        throws CtxException;
    public void initialize(int limit)
        throws CtxException;
    public void cleanUp()
        throws CtxException;
}

