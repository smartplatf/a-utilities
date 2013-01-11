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
 * File:                org.anon.utilities.test.pool.TestPoolEntity
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test entity for pooling
 *
 * ************************************************************
 * */

package org.anon.utilities.test.pool;

import org.junit.Test;
import static org.junit.Assert.*;
import org.anon.utilities.pool.PoolEntity;
import org.anon.utilities.pool.Pool;

public class TestPoolEntity implements PoolEntity
{
    private PoolEntity _next;
    private Pool _pool;
    private int _createdNumber;
    private static int created = 0;

    public TestPoolEntity()
    {
        _createdNumber = created++;
    }


    public static void reset() { created = 0; }

    @Test
    public void test()
    {
        assertTrue(1 == 1);
    }

    public void calledFromPooledObject()
    {
        try
        {
            System.out.println("Called from pooled object: " + _createdNumber + ":" + this.getClass().getClassLoader());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
}

