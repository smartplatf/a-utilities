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
 * File:                org.anon.utilities.test.jitq.TestJITQ
 * Author:              rsankar
 * Revision:            1.0
 * Date:                15-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of tests for jitq
 *
 * ************************************************************
 * */

package org.anon.utilities.test.jitq;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.jitq.JITProcessQueue;
import org.anon.utilities.jitq.BasicJITProcessQueue;

public class TestJITQ
{
    @Test
    public void testTestJITQ()
        throws Exception
    {
        TestData d = new TestData("Newdata");
        QueueListener ql = new QueueListener();
        JITProcessQueue q = new BasicJITProcessQueue("object", d, ql);
        ql.setQueue(q);
        jitq().registerProcessor("object");
        for (int i = 0; i < 10; i++)
            q.add("test-Message" + i);

        //jitq().processJITQueue(q);
        System.out.println("started processing");
        Thread.currentThread().sleep(1000);
        for (int i = 11; i < 20; i++)
            q.add("test2-Message" + i);
        //jitq().processJITQueue(q);
        System.out.println("started processing");
        Thread.currentThread().sleep(5);
    }

    @Test
    public void testContinuous()
        throws Exception
    {
        TestData d = new TestData("Newdata");
        QueueListener ql = new QueueListener();
        JITProcessQueue q = new BasicJITProcessQueue("cont", d, ql);
        ql.setQueue(q);
        jitq().registerProcessor("cont");
        for (int i = 0; i < 10; i++)
            q.add("cont-Message" + i);

        //jitq().processJITQueue(q);
        System.out.println("cont - started processing");
        for (int i = 11; i < 20; i++)
        {
            q.add("cont-Message" + i);
            //jitq().processJITQueue(q);
            System.out.println("cont - started processing");
        }
        Thread.currentThread().sleep(1000);
    }
}

