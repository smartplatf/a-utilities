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
 * File:                org.anon.utilities.memcache.guava.GuavaMemCache
 * Author:              rsankar
 * Revision:            1.0
 * Date:                27-03-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A memcache using guava implementation
 *
 * ************************************************************
 * */

package org.anon.utilities.memcache.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import static org.anon.utilities.services.ServiceLocator.*;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;
import org.anon.utilities.memcache.MemCacheParameters;
import org.anon.utilities.memcache.LimitedMemCache;
import org.anon.utilities.memcache.ElementCreator;
import org.anon.utilities.memcache.ElementRemovalListener;
import org.anon.utilities.exception.CtxException;

public class GuavaMemCache<K, V> implements LimitedMemCache<K, V>
{
    public class LoaderTranslator<K, V> extends CacheLoader<K, V>
    {
        private ElementCreator<K, V> _creator;

        LoaderTranslator(ElementCreator<K, V> create)
        {
            _creator = create;
        }

        public V load(K key) 
            throws Exception
        {
            return _creator.createValue(key);
        }
    }

    public class RemovalTranslator<K, V> implements RemovalListener<K, V>
    {
        private ElementRemovalListener<K, V> _listener;

        RemovalTranslator(ElementRemovalListener<K, V> remover)
        {
            _listener = remover;
        }

        public void onRemoval(RemovalNotification<K, V> notification)
        {
            try
            {
                _listener.removed(notification.getKey(), notification.getValue());
            }
            catch (Exception e)
            {
                //TODO:
                e.printStackTrace();
            }
        }
    }

    private Cache<K, V> _cache;
    private LoadingCache<K, V> _loading;
    private CacheLoader<K, V> _creator;
    private RemovalListener<K, V> _remover;

    public GuavaMemCache()
    {
    }

    public Repeatable repeatMe(RepeaterVariants vars)
        throws CtxException
    {
        GuavaMemCache cache = new GuavaMemCache();
        MemCacheParameters parms = (MemCacheParameters)vars;
        if (parms.creator() != null)
            cache.setupCreator(parms.creator());
        if (parms.listener() != null)
            cache.setupRemovalListener(parms.listener());

        cache.initialize(parms.limit());
        return cache;
    }

    public V get(K key)
        throws CtxException
    {
        assertion().assertNotNull(_cache, "Cannot get. The cache is not yet setup");
        try
        {
            if (_loading != null)
                return _loading.get(key);
            else
                return _cache.getIfPresent(key);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Error.", "Error"));
        }

        return null;
    }

    public void put(K key, V value)
        throws CtxException
    {
        assertion().assertNotNull(_cache, "Cannot put. The cache is not yet setup");
        _cache.put(key, value);
    }

    public void invalidate(K key)
        throws CtxException
    {
        assertion().assertNotNull(_cache, "Cannot invalidate. No cache is setup yet");
        _cache.invalidate(key);
    }

    public void setupCreator(ElementCreator<K, V> creator)
        throws CtxException
    {
        _creator = new LoaderTranslator(creator);
    }

    public void setupRemovalListener(ElementRemovalListener<K, V> remover)
        throws CtxException
    {
        _remover = new RemovalTranslator(remover);
    }

    public void initialize(int limit)
        throws CtxException
    {
        CacheBuilder builder = CacheBuilder.newBuilder().maximumSize(limit);
        if (_remover != null)
            builder = builder.removalListener(_remover);

        if (_creator != null)
        {
            _loading = builder.build(_creator);
            _cache = _loading;
        }
        else
            _cache = builder.build();
    }

    public void cleanUp()
        throws CtxException
    {
        if (_cache != null)
            _cache.cleanUp();
    }
}

