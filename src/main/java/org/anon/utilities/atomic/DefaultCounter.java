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
 * File:                org.anon.utilities.atomic.DefaultCounter
 * Author:              rsankar
 * Revision:            1.0
 * Date:                11-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An implementation defaulted for single jvm execution
 *
 * ************************************************************
 * */

package org.anon.utilities.atomic;

import java.util.concurrent.atomic.AtomicInteger;

import org.anon.utilities.exception.CtxException;

public class DefaultCounter implements AtomicCounter
{
    private AtomicInteger _impl;

    public DefaultCounter(int val)
    {
        _impl = new AtomicInteger(val);
    }

    public int incrementAndGet()
        throws CtxException
    {
        return _impl.incrementAndGet();
    }

    public void set(int i)
        throws CtxException
    {
        _impl.set(i);
    }

    public int get()
        throws CtxException
    {
        return _impl.get();
    }

    public int decrementAndGet()
        throws CtxException
    {
        return _impl.decrementAndGet();
    }
}

