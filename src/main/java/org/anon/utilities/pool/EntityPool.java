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
 * File:                org.anon.utilities.pool.EntityPool
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A default implementation of the entity pool
 *
 * ************************************************************
 * */

package org.anon.utilities.pool;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicInteger;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.logger.Logger;
import org.anon.utilities.exception.CtxException;

public class EntityPool implements Pool
{
    private AtomicReference<PoolEntity> _first = new AtomicReference<PoolEntity>();

    private EntityCreator _creator;
    private List<Constraint> _constraints;
    private AtomicInteger _createdSz;
    private AtomicInteger _freeSz;
    private Logger _logger;

    public EntityPool(Class<? extends PoolEntity> cls)
    {
        this(new DefaultCreator(cls));
    }

    public EntityPool(EntityCreator creator)
    {
        _creator = creator;
        _constraints = new ArrayList<Constraint>();
        _createdSz = new AtomicInteger(0);
        _freeSz = new AtomicInteger(0);
        _logger = logger().rlog(this);
    }

    protected EntityPool(Class<? extends PoolEntity> cls, int presetcreated)
    {
        this(cls);
        _createdSz.set(presetcreated);
    }

    void addConstraint(Constraint cons)
    {
        _constraints.add(cons);
    }

    private int create()
        throws CtxException
    {
        int pass = 0;
        for (Constraint cons : _constraints)
        {
            int size = cons.increase(this);
            if (size <= 0) 
                return size;

            if (size > pass)
                pass = size;
        }

        if (pass <= 0)
            pass = INCREASE_BY;

        for (int i = 0; i < pass; i++)
        {
            PoolEntity obj = _creator.newEntity();
            _createdSz.incrementAndGet();
            unlockone(obj);
        }

        return pass;
    }

    private boolean canUnlock()
    {
        boolean add = true;
        for (Constraint cons : _constraints)
        {
            add = add && cons.canUnlock(this);
            if (!add) break;
        }

        return add;
    }

    public PoolEntity lockone()
        throws CtxException
    {
        return lockone(true);
    }

    public PoolEntity lockone(boolean increase)
        throws CtxException
    {
        int tries = 0;
        int retry = 0;
        while (true) 
        {
            PoolEntity locked = _first.get();
            if ((locked == null) && (retry < RETRY_BEFORE_RECREATE))
            {
                retry++;
                continue;
            }
            else if ((locked == null) && (increase))
            {
                int increased = create();
                tries++;
                _logger.trace("Increased by: " + increased + ":" + tries);
                if ((increased <= 0) && (tries > RETRY_LIMIT))
                {
                    _logger.trace("Retrying after: "+ tries);
                    break;
                }
            }
            else if ((locked == null) && (!increase))
            {
                _logger.trace("Not increasing : Returning null");
                break;
            }
            else if (_first.compareAndSet(locked, locked.nextEntity())) 
            {
                locked.setNextEntity(null);
                _freeSz.decrementAndGet();
                return locked;
            }
        }

        return null;
    }

    public void unlockone(Object obj)
        throws CtxException
    {
        PoolEntity released = (PoolEntity)obj;
        while (true) 
        {
            if (!canUnlock())
            {
                _logger.trace("Not adding. Ignoring the unlock.");
                break;
            }

            PoolEntity next = _first.get();
            released.setNextEntity(next);
            if (_first.compareAndSet(next, released)) 
            {
                _freeSz.incrementAndGet();
                //_logger.trace("Set the free size to: " + _freeSz.get() + ":" + released);
                break;
            }
        }
    }

    public int size() { return _createdSz.get(); }

    public int free() { return _freeSz.get(); }

    public void adjust()
        throws CtxException
    {
        int releaseSz = 0;
        for (Constraint cons : _constraints)
        {
            int size = cons.adjust(this);
            if (size < releaseSz)
                releaseSz = size;
        }

        for (int i = 0; i < releaseSz; i++)
        {
            Object obj = lockone(false);
            _createdSz.decrementAndGet();
            if (obj == null)
                break;
        }
    }
}

