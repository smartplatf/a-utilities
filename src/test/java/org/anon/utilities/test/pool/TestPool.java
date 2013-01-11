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
 *
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.anon.utilities.test.pool.TestPool
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test case for pool functionality
 *
 * ************************************************************
 * */

package org.anon.utilities.test.pool;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import org.anon.utilities.pool.Pool;
import org.anon.utilities.loader.RelatedLoader;
import org.anon.utilities.test.PathHelper;

public class TestPool
{
    @Test
    public void testTestPool()
        throws Exception
    {
        try
        {
            URL[] urls = new URL[] 
                {
                    new URL(PathHelper.getProjectBuildPath()),
                    new URL(PathHelper.getProjectTestBuildPath())
                };
            RelatedLoader ldr = new RelatedLoader(urls, new String[] {});
            System.out.println("Starting Normal Test...");
            runTest(this.getClass().getClassLoader(), 0);
            System.out.println("Starting classloader test...");
            runTest(ldr, 0);
            /*
            System.out.println("Starting StrictPool Test...");
            runTest(loader, 1);
            System.out.println("Starting StrictPoolMaxSize Test...");
            runTest(loader, 2);
            System.out.println("Starting distributionPool Test...");
            runTest(loader, 3);
            */
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void runTest(ClassLoader loader, int which)
        throws Exception
    {
        Class scls = loader.loadClass("org.anon.utilities.test.pool.TestPool");
        Object po = scls.newInstance();
        Class cls = loader.loadClass("org.anon.utilities.test.pool.TestPool$TestPoolMe");
        Constructor cons = cls.getDeclaredConstructor(scls, Integer.TYPE);
        Object obj = cons.newInstance(po, which);

        Class thrdcls = loader.loadClass("java.lang.Thread");
        Method m = thrdcls.getDeclaredMethod("start");
        Method mj = thrdcls.getDeclaredMethod("join");
        cons = thrdcls.getConstructor(Runnable.class);
        Object t = cons.newInstance(obj);
        m.invoke(t);
        mj.invoke(t);
    }

    public class TestPoolMe implements Runnable
    {
        int _which;

        public TestPoolMe(int t)
        {
            _which = t;
        }


        private Pool create(Class cls)
            throws Exception
        {
            Pool pool = null;
            switch(_which)
            {
                case 0:
                    pool = (Pool)pool().createPool(cls);
                    break;
                /*case 1:
                    pool = PoolHelper.strictPoolFor(cls, 10);
                    break;
                case 2:
                    pool = PoolHelper.strictPoolFor(cls, 10, 20);
                    break;
                case 3:
                    pool = PoolHelper.distributionPool(cls, 10);
                    break;
                    */
            }

            return pool;
        }

        public void run()
        {
            try
            {
                TestPoolEntity.reset();
                Pool pool = create(TestPoolEntity.class);
                List<Thread> lst = new ArrayList<Thread>();
                for (int i = 0; i < 100; i++)
                {
                    Thread t = new Thread(new RunFromPool(pool));
                    lst.add(t);
                    t.start();
                }

                for (Thread o : lst)
                    o.join();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class RunFromPool implements Runnable
    {
        Pool _pool = null;

        public RunFromPool(Pool pool)
        {
            _pool = pool;
        }

        public void run()
        {
            try
            {
                TestPoolEntity m = (TestPoolEntity)_pool.lockone();
                assertTrue(m != null);
                m.calledFromPooledObject();
                _pool.unlockone(m);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

