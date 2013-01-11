/**
 * SMART - State Machine ARchiTecture
 *
 * Copyright (C) 2012 Individual contributors as indicated by
 * the @authors tag
 *
 * This file is a part of SMART.
 *
 * SMART is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SMART is distributed in the hope that it will be useful,
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
 * File:                org.anon.utilities.serialize.kryo.KryoPoolObject
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An object of kryo which is used from pool for multiple serializers
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.kryo;

import java.util.UUID;

import com.esotericsoftware.kryo.Kryo;
import org.objenesis.strategy.SerializingInstantiatorStrategy;

import org.anon.utilities.pool.PoolEntity;
import org.anon.utilities.pool.Pool;

public class KryoPoolObject implements PoolEntity
{
    private Kryo _kryo;
    private PoolEntity _next;
    private Pool _pool;

    public KryoPoolObject()
    {
        _kryo = new Kryo();
        _kryo.setInstantiatorStrategy(new SerializingInstantiatorStrategy());
        _kryo.register(UUID.class, new UUIDSerializer());
    }

    public void registerType(Class cls)
    {
        _kryo.register(cls);
    }

    public PoolEntity nextEntity()
    {
        return _next;
    }

    public void setNextEntity(PoolEntity entity)
    {
        _next = entity;
    }

    public Pool pool()
    {
        return _pool;
    }

    public void storePool(Pool p)
    {
        _pool = p;
    }

    public Kryo getKryo()
    {
        return _kryo;
    }
}

