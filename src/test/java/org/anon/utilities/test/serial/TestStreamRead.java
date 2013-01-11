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
 *
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.anon.utilities.test.serial.TestStreamRead
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test case to read the streams and compare
 *
 * ************************************************************
 * */

package org.anon.utilities.test.serial;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import org.anon.utilities.objservices.SerializerService;
import org.anon.utilities.serialize.srdr.DirtyField;
import org.anon.utilities.logger.Logger;

public class TestStreamRead
{
    @Test
    public void testTestStreamRead()
        throws Exception
    {
        SimpleTestObject obj = new SimpleTestObject(0);
        SimpleTestObject obj2 = new SimpleTestObject(0);
        ComplexTestObject cobj = new ComplexTestObject();
        ComplexTestObject cobj2 = new ComplexTestObject();
        Logger log = logger().glog("TestStreamRead");
        SerializerService svc = serial();
        List<DirtyField> dirty = null;

        for (int i = 0; i < 1000; i++)
            dirty = svc.dirtyFields(obj2, obj);

        perf().startHere("dirty fields:");
        for (int i = 0; i < 1000; i++)
            dirty = svc.dirtyFields(obj2, obj);
        perf().dumpHere(log);
        perf().startHere("complex");
        for (int i = 0; i < 1000; i++)
            dirty = svc.dirtyFields(cobj2, cobj);
        perf().dumpHere(log);
        System.out.println(dirty);
    }

    @Test
    public void testListStreamRead()
        throws Exception
    {
        Logger log = logger().glog("TestStreamRead");
        SimpleListTest lst = new SimpleListTest();
        SimpleListTest lst2 = new SimpleListTest();
        List<DirtyField> dirty = null;
        perf().startHere("List traverse :");
        for (int i = 0; i < 1000; i++)
            dirty = serial().dirtyFields(lst2, lst);
        perf().dumpHere(log);
        //System.out.println(dirty);
    }
}

