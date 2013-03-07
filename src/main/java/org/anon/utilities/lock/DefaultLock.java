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
 * File:                org.anon.utilities.lock.DefaultLock
 * Author:              rsankar
 * Revision:            1.0
 * Date:                11-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A default locking scheme
 *
 * ************************************************************
 * */

package org.anon.utilities.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.anon.utilities.exception.CtxException;

public class DefaultLock implements LockScheme
{
    class OwnerAwareLock extends ReentrantReadWriteLock
    {
        public OwnerAwareLock()
        {
            super(true);
        }

        public Thread getOwnerThread()
        {
            return super.getOwner();
        }
    }

    private OwnerAwareLock _lock = new OwnerAwareLock();

    public DefaultLock()
    {
    }

    public Thread getOwnerThread()
        throws CtxException
    {
        return _lock.getOwnerThread();
    }

    public boolean writeHeldByCurrentThread()
        throws CtxException
    {
        return _lock.writeLock().isHeldByCurrentThread();
    }
    public void writeLock()
        throws CtxException
    {
        if (!writeHeldByCurrentThread())
            _lock.writeLock().lock();
    }

    public void writeUnlock()
        throws CtxException
    {
        if (writeHeldByCurrentThread())
            _lock.writeLock().unlock();
    }
}

