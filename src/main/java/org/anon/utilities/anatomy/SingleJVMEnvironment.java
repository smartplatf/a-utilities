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
 * File:                org.anon.utilities.anatomy.SingleJVMEnvironment
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A single jvm run
 *
 * ************************************************************
 * */

package org.anon.utilities.anatomy;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ConcurrentHashMap;

import org.anon.utilities.lock.LockScheme;
import org.anon.utilities.lock.DefaultLock;
import org.anon.utilities.atomic.AtomicCounter;
import org.anon.utilities.atomic.DefaultCounter;
import org.anon.utilities.cthreads.CThreadFactory;
import org.anon.utilities.cthreads.CThreadExecutor;
import org.anon.utilities.jitq.DataListener;
import org.anon.utilities.jitq.JITProcessQueue;
import org.anon.utilities.jitq.BasicJITProcessQueue;
import org.anon.utilities.exception.CtxException;

public class SingleJVMEnvironment implements JVMEnvironment
{
    public SingleJVMEnvironment()
    {
    }

    public ExecutorService executorFor(String type, String name)
        throws CtxException
    {
        return new CThreadExecutor(new CThreadFactory(type, name));
    }

    public JITProcessQueue jitQueueFor(String group, String name, Object associated, DataListener listener)
        throws CtxException
    {
        return new BasicJITProcessQueue(group, associated, listener);
    }

    public <K, T> Map<K, T> mapFor(String name)
        throws CtxException
    {
        return new ConcurrentHashMap<K, T>();
    }

    public LockScheme createLock(String name)
        throws CtxException
    {
        return new DefaultLock();
    }

    public AtomicCounter createAtomicCounter(String name, int initial)
        throws CtxException
    {
        return new DefaultCounter(initial);
    }

    public boolean isDistributed()
    {
        return false;
    }
}

