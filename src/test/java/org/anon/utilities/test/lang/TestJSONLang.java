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
 * File:                org.anon.utilities.test.lang.TestJSONLang
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test case to test json conversion
 *
 * ************************************************************
 * */

package org.anon.utilities.test.lang;

import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import static org.anon.utilities.objservices.ConvertService.*;

import org.anon.utilities.lang.json.JSONCreator;
import org.anon.utilities.lang.json.ObjectCreator;
import org.anon.utilities.reflect.ObjectTraversal;
import org.anon.utilities.reflect.ClassTraversal;
import org.anon.utilities.exception.CtxException;
import org.anon.utilities.logger.Logger;

public class TestJSONLang
{

    @Test
    public void testTestJSONLang()
        throws Exception
    {
        SimpleTestObject obj = new SimpleTestObject();
        JSONCreator traverse = new JSONCreator();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestJSONLang");
        perf().startHere("testJSONLang");
        traversal.traverse();
        perf().dumpHere(log);
        System.out.println("Created json: " + traverse.json());
        ObjectCreator create = new ObjectCreator(traverse.json());
        ClassTraversal ct = new ClassTraversal(SimpleTestObject.class, create);
        Object ret = ct.traverse();
        System.out.println("Created object back " + ret);

        ByteArrayOutputStream bostr = new ByteArrayOutputStream();
        convert().writeObject(obj, bostr, translator.json);
        byte[] bytes = bostr.toByteArray();
        System.out.println("Converted: " + new String(bytes));

        ByteArrayInputStream bistr = new ByteArrayInputStream(bytes);
        SimpleTestObject o = convert().readObject(bistr, SimpleTestObject.class, translator.json);
        System.out.println("Converted back: " + o);
    }

    @Test
    public void testComplexJSONLang()
        throws Exception
    {
        ComplexTestObject obj = new ComplexTestObject();
        JSONCreator traverse = new JSONCreator();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, true, false, null);
        Logger log = logger().glog("TestJSONLangComplex");
        perf().startHere("testJSONLangComplex");
        traversal.traverse();
        perf().dumpHere(log);
        System.out.println("Created json: " + traverse.json());
        ObjectCreator create = new ObjectCreator(traverse.json());
        ClassTraversal ct = new ClassTraversal(ComplexTestObject.class, create);
        Object ret = ct.traverse();
        System.out.println("Created object back " + ret);

        ByteArrayOutputStream bostr = new ByteArrayOutputStream();
        convert().writeObject(obj, bostr, translator.json);
        byte[] bytes = bostr.toByteArray();
        System.out.println("Converted: " + new String(bytes));

        ByteArrayInputStream bistr = new ByteArrayInputStream(bytes);
        ComplexTestObject o = convert().readObject(bistr, ComplexTestObject.class, translator.json);
        System.out.println("Converted back: " + o);
    }

    @Test
    public void testListJSONLang()
        throws Exception
    {
        ListTestObject obj = new ListTestObject(1);
        JSONCreator traverse = new JSONCreator();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, true, false, null);
        Logger log = logger().glog("TestJSONLangList");
        perf().startHere("testJSONLangList");
        traversal.traverse();
        perf().dumpHere(log);
        System.out.println("Created json: " + traverse.json());
        ObjectCreator create = new ObjectCreator(traverse.json());
        ClassTraversal ct = new ClassTraversal(ListTestObject.class, create);
        Object ret = ct.traverse();
        System.out.println("Created object back " + ret);
    }

    @Test
    public void testMapJSONLang()
        throws Exception
    {
        MapTestObject obj = new MapTestObject(2);
        JSONCreator traverse = new JSONCreator();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, true, false, null);
        Logger log = logger().glog("TestJSONLangMap");
        perf().startHere("testJSONLangMap");
        traversal.traverse();
        perf().dumpHere(log);
        System.out.println("Created json: " + traverse.json());
        /*
         * ObjectCreator create = new ObjectCreator(traverse.json());
        ClassTraversal ct = new ClassTraversal(MapTestObject.class, create);
        Object ret = ct.traverse();
        System.out.println("Created object back " + ret);*/
    }

    @Test
    public void testMapObjectJSONLang()
        throws Exception
    {
        String json = "{'ReviewObject':'Not present', 'review':'Reviewed','rating':1}";
        ByteArrayInputStream istr = new ByteArrayInputStream(json.getBytes());
        Map got = convert().readObject(istr, Map.class, translator.json);
        assertTrue(got != null);
        assertTrue(got.containsKey("ReviewObject"));
        assertTrue(got.containsKey("review"));
        assertTrue(got.containsKey("rating"));
        assertTrue(got.get("ReviewObject").equals("Not present"));
        assertTrue(got.get("review").equals("Reviewed"));
        assertTrue(got.get("rating").equals(1));
        System.out.println("JSON got is: " + got);
    }
}

