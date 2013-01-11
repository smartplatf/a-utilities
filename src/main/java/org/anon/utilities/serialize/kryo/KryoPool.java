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
 * File:                org.anon.utilities.serialize.kryo.KryoPool
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A pool for kryo objects from which serializers can be got
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.kryo;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.pool.Pool;
import org.anon.utilities.exception.CtxException;
import org.anon.utilities.serialize.Serializer;
import org.anon.utilities.serialize.SerializerFactory;

public class KryoPool implements SerializerFactory
{
    private Pool _pool;

    public KryoPool()
    {
        try
        {
            _pool = (Pool)pool().createPool(KryoPoolObject.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void unlockone(KryoPoolObject obj)
        throws CtxException
    {
        _pool.unlockone(obj);
    }

    public Serializer getSerializer()
        throws CtxException
    {
        KryoSerializer ser = null;
        KryoPoolObject kpo = (KryoPoolObject)_pool.lockone();
        if (kpo != null)
        {
            ser = new KryoSerializer(this, kpo);
        }

        return ser;
    }
}

