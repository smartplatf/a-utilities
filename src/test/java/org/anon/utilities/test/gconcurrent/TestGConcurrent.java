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
 * File:                org.anon.utilities.test.gconcurrent.TestGConcurrent
 * Author:              rsankar
 * Revision:            1.0
 * Date:                18-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of test cases for graph concurrency
 *
 * ************************************************************
 * */

package org.anon.utilities.test.gconcurrent;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import static org.junit.Assert.*;

import org.anon.utilities.gconcurrent.Graph;
import org.anon.utilities.gconcurrent.GraphNode;
import org.anon.utilities.gconcurrent.NodeDetails;
import org.anon.utilities.gconcurrent.DefaultNodeDetails;
import org.anon.utilities.cthreads.CThreadExecutor;
import org.anon.utilities.cthreads.CThreadFactory;

public class TestGConcurrent
{
    private GraphNode createNode(String nm, String cls, String method)
        throws Exception
    {
        Class clazz = Class.forName(cls);
        Method mthd = clazz.getDeclaredMethod(method);
        NodeDetails det = new DefaultNodeDetails(clazz, mthd, null);
        return new GraphNode(nm, clazz, mthd, det);
    }

    @Test
    public void testTestGConcurrentAllParallel()
        throws Exception
    {
        Graph g = new Graph();
        GraphNode nde1 = createNode("transition1", "org.anon.utilities.test.gconcurrent.MyRunner", "transition1");
        g.addGraphNode(nde1);
        GraphNode nde2 = createNode("transition2", "org.anon.utilities.test.gconcurrent.MyRunner", "transition2");
        //g.addGraphNode(nde2);
        GraphNode nde11 = createNode("transition11", "org.anon.utilities.test.gconcurrent.MyRunner", "transition11");
        g.addGraphNode(nde11);
        ExecutorService svc = Executors.newCachedThreadPool();
        System.out.println("Graph is: " + g);
        TestContext ctx = new TestContext(g, svc);
        ctx.executeGraph();
        System.out.println("Scheduled.");
        Thread.currentThread().sleep(1000);
    }

    @Test
    public void testTestSequenceWithParallel()
        throws Exception
    {
        Graph g = new Graph();
        GraphNode nde1 = createNode("transition1", "org.anon.utilities.test.gconcurrent.MyRunner", "transition1");
        g.addGraphNode(nde1);
        GraphNode nde2 = createNode("transition2", "org.anon.utilities.test.gconcurrent.MyRunner", "transition2");
        g.addGraphNode(nde2);
        GraphNode nde3 = createNode("transition3", "org.anon.utilities.test.gconcurrent.MyRunner", "transition3");
        g.addGraphNode(nde3);
        GraphNode nde11 = createNode("transition11", "org.anon.utilities.test.gconcurrent.MyRunner", "transition11");
        g.addGraphNode(nde11);
        System.out.println("Adding dependency between: " + nde1 + ":" + nde2);
        g.addDependency(nde1, nde2);
        g.addDependency(nde2, nde3);
        ExecutorService svc = Executors.newCachedThreadPool();
        System.out.println("Sequential Graph is: " + g);
        TestContext ctx = new TestContext(g, svc);
        ctx.executeGraph();
        System.out.println("Sequential Scheduled.");
        Thread.currentThread().sleep(1000);
    }

    @Test
    public void testWithCThreads()
        throws Exception
    {
        Graph g = new Graph();
        GraphNode nde1 = createNode("transition1", "org.anon.utilities.test.gconcurrent.MyRunner", "transition1");
        g.addGraphNode(nde1);
        GraphNode nde2 = createNode("transition2", "org.anon.utilities.test.gconcurrent.MyRunner", "transition2");
        g.addGraphNode(nde2);
        GraphNode nde3 = createNode("transition3", "org.anon.utilities.test.gconcurrent.MyRunner", "transition3");
        g.addGraphNode(nde3);
        GraphNode nde11 = createNode("transition11", "org.anon.utilities.test.gconcurrent.MyRunner", "transition11");
        g.addGraphNode(nde11);
        System.out.println("Adding dependency between: " + nde1 + ":" + nde2);
        g.addDependency(nde1, nde2);
        g.addDependency(nde3, nde2);
        ExecutorService svc = new CThreadExecutor(new CThreadFactory("Test", "testWithCThread"));
        System.out.println("Sequential Graph is: " + g);
        TestContext ctx = new TestContext(g, svc);
        ctx.executeGraph();
        System.out.println("Sequential Scheduled.");
        Thread.currentThread().sleep(1000);
    }
}

