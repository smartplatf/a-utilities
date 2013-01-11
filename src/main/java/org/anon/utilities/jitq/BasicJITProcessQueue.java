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
 * File:                org.anon.utilities.jitq.BasicJITProcessQueue
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A basic implementation using concurrent queue
 *
 * ************************************************************
 * */

package org.anon.utilities.jitq;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.anon.utilities.exception.CtxException;

public class BasicJITProcessQueue extends ConcurrentLinkedQueue<Object> implements JITProcessQueue
{
    private static final int NOT_PROCESSING = 0;
    private static final int PROCESSING = 1;

    private Object _associatedTo;
    private DataListener _listener;
    private AtomicInteger _processing;
    private String _belongsTo;

    public BasicJITProcessQueue(String group, Object associated, DataListener listener)
    {
        _associatedTo = associated;
        _listener = listener;
        _processing = new AtomicInteger(NOT_PROCESSING);
        _belongsTo = group;
    }

    public boolean isProcessing()
        throws CtxException
    {
        return (_processing.get() > NOT_PROCESSING);
    }

    public boolean setProcessing()
        throws CtxException
    {
        return _processing.compareAndSet(NOT_PROCESSING, PROCESSING);
    }

    public void doneProcessing()
        throws CtxException
    {
        _processing.decrementAndGet();
    }

    public DataListener listener()
    {
        return _listener;
    }

    public Object associatedTo()
    {
        return _associatedTo;
    }

    public boolean add(Object data)
    {
        return super.add(data);
    }

    public Object poll()
    {
        return super.poll();
    }

    public boolean isEmpty()
    {
        return super.isEmpty();
    }

    public String belongsTo()
    {
        return _belongsTo;
    }
}

