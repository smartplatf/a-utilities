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
 * File:                org.anon.utilities.test.serial.TestSerialize
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test case for serializing
 *
 * ************************************************************
 * */

package org.anon.utilities.test.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;
import static org.junit.Assert.*;

import org.anon.utilities.logger.Logger;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

public class TestSerialize
{
    @Test
    public void testTestSerialize()
        throws Exception
    {
        ListTestObject stst = new ListTestObject(true);
        Logger log = logger().glog("TestSerialize");
        perf().startHere("Serialize");
        for (int i = 0; i < 1000; i++)
        {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            serial().serialize(str, stst);
            byte[] serialized = str.toByteArray();
            str.close();
            ByteArrayInputStream istr = new ByteArrayInputStream(serialized);
            stst = (ListTestObject)serial().deserialize(istr);
        }
        perf().dumpHere(log);
        //System.out.println(stst);
    }

    @Test
    public void testCompareObjects()
        throws Exception
    {
        SimpleTestObject sobj1 = new SimpleTestObject(0);
        SimpleTestObject sobj2 = new SimpleTestObject(0);
        SimpleTestObject sobj3 = new SimpleTestObject(sobj1);
        Logger log = logger().glog("TestSerializeCompare");
        perf().startHere("Compare");
        for (int i = 0; i < 1000; i++)
        {
            boolean scompare = serial().same(sobj1, sobj1);
        }
        perf().checkpointHere("Same reference compare");
        for (int i = 0; i < 1000; i++)
        {
            boolean dcompare = serial().same(sobj1, sobj2);
        }
        perf().checkpointHere("Different vals");
        for (int i = 0; i < 1000; i++)
        {
            boolean same = serial().same(sobj1, sobj3);
        }
        perf().dumpHere(log);
    }
}

