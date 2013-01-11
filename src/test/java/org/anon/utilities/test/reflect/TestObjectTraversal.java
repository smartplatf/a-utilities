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
 * File:                org.anon.utilities.test.reflect.TestObjectTraversal
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * TestObjectTraversal
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.util.Map;
import java.util.List;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.io.*;

import org.junit.Test;
import static org.junit.Assert.*;
import com.esotericsoftware.kryo.*;
import com.esotericsoftware.kryo.io.*;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringSerializer;
import org.objenesis.strategy.SerializingInstantiatorStrategy;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.reflect.DirtyFieldTraversal;
import org.anon.utilities.reflect.ObjectTraversal;
import org.anon.utilities.reflect.ClassTraversal;
import org.anon.utilities.logger.Logger;

public class TestObjectTraversal
{
    @Test
    public void testSimpleObjectTraversal()
        throws Exception
    {
        SimpleTestObject obj = new SimpleTestObject();
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testSimpleObjectTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_integer"));
        assertTrue(traversed.containsKey("_string"));
        assertTrue(traversed.containsKey("_decimal"));
        assertTrue(traversed.containsKey("_uuid"));
        assertTrue(traversed.containsKey("_float"));
        List<Object> ints = traversed.get("_integer");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_string");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_decimal");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_uuid");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_float");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        System.out.println(traversed);
    }

    @Test
    public void testComplexObjectTraversal()
        throws Exception
    {
        ComplexTestObject obj = new ComplexTestObject();
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testComplexObjectTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_integer"));
        assertTrue(traversed.containsKey("_string"));
        assertTrue(traversed.containsKey("_decimal"));
        assertTrue(traversed.containsKey("_uuid"));
        assertTrue(traversed.containsKey("_float"));
        assertTrue(traversed.containsKey("_double"));
        assertTrue(traversed.containsKey("_simple"));
        assertTrue(traversed.containsKey("_dt"));
        assertFalse(traversed.containsKey("_simpleDuplicate"));
        List<Object> ints = traversed.get("_integer");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_string");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_decimal");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_uuid");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_float");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_double");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_dt");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_simple");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        System.out.println(traversed);
    }

    @Test
    public void testListObjectTraversal()
        throws Exception
    {
        ListTestObject obj = new ListTestObject();
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testListObjectTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_integer"));
        assertTrue(traversed.containsKey("_string"));
        assertTrue(traversed.containsKey("_decimal"));
        assertTrue(traversed.containsKey("_uuid"));
        assertTrue(traversed.containsKey("_float"));
        assertTrue(traversed.containsKey("_simpleObjs"));
        assertTrue(traversed.containsKey("_complexObjs"));
        assertTrue(traversed.containsKey("_listObjs"));
        List<Object> ints = traversed.get("_integer");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_string");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_decimal");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_uuid");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_float");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_simpleObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        //System.out.println(traversed);
    }

    @Test
    public void testComplexListObjectTraversal()
        throws Exception
    {
        ListTestObject obj = new ListTestObject(1);
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testComplexListObjectTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_integer"));
        assertTrue(traversed.containsKey("_string"));
        assertTrue(traversed.containsKey("_decimal"));
        assertTrue(traversed.containsKey("_uuid"));
        assertTrue(traversed.containsKey("_float"));
        assertTrue(traversed.containsKey("_simpleObjs"));
        assertTrue(traversed.containsKey("_complexObjs"));
        assertTrue(traversed.containsKey("_listObjs"));
        assertTrue(traversed.containsKey("_double"));
        assertTrue(traversed.containsKey("_dt"));
        List<Object> ints = traversed.get("_integer");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_string");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_decimal");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_uuid");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_float");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_simpleObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_double");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_dt");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_simple");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_complexObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        //System.out.println(traversed);
    }

    @Test
    public void testListListObjectTraversal()
        throws Exception
    {
        ListTestObject obj = new ListTestObject(2);
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testListListObjectTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_integer"));
        assertTrue(traversed.containsKey("_string"));
        assertTrue(traversed.containsKey("_decimal"));
        assertTrue(traversed.containsKey("_uuid"));
        assertTrue(traversed.containsKey("_float"));
        assertTrue(traversed.containsKey("_simpleObjs"));
        assertTrue(traversed.containsKey("_complexObjs"));
        assertTrue(traversed.containsKey("_listObjs"));
        assertTrue(traversed.containsKey("_double"));
        assertTrue(traversed.containsKey("_dt"));
        List<Object> ints = traversed.get("_integer");
        assertNotNull(ints);
        assertTrue(ints.size() == 300);
        ints = traversed.get("_string");
        assertNotNull(ints);
        assertTrue(ints.size() == 300);
        ints = traversed.get("_decimal");
        assertNotNull(ints);
        assertTrue(ints.size() == 300);
        ints = traversed.get("_uuid");
        assertNotNull(ints);
        assertTrue(ints.size() == 300);
        ints = traversed.get("_float");
        assertNotNull(ints);
        assertTrue(ints.size() == 300);
        ints = traversed.get("_simpleObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_double");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_dt");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_simple");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_complexObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_listObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        //System.out.println(traversed);
    }

    @Test
    public void testListSameListObjectTraversal()
        throws Exception
    {
        ListTestObject obj = new ListTestObject(3);
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testListSameListObjectTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_integer"));
        assertTrue(traversed.containsKey("_string"));
        assertTrue(traversed.containsKey("_decimal"));
        assertTrue(traversed.containsKey("_uuid"));
        assertTrue(traversed.containsKey("_float"));
        assertTrue(traversed.containsKey("_simpleObjs"));
        assertTrue(traversed.containsKey("_complexObjs"));
        assertTrue(traversed.containsKey("_listObjs"));
        assertTrue(traversed.containsKey("_double"));
        assertTrue(traversed.containsKey("_dt"));
        List<Object> ints = traversed.get("_integer");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_string");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_decimal");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_uuid");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_float");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        ints = traversed.get("_simpleObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_double");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_dt");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_simple");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        ints = traversed.get("_complexObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        ints = traversed.get("_listObjs");
        assertNotNull(ints);
        assertTrue(ints.size() == 1);
        //System.out.println(traversed);
    }

    @Test
    public void testMapTraversal()
        throws Exception
    {
        MapTestObject obj = new MapTestObject();
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testMapTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_simpleMap"));
        assertTrue(traversed.containsKey("_complexMap"));
        assertTrue(traversed.containsKey("_simpleObject"));
        List ints = traversed.get("java.lang.String");
        assertNotNull(ints);
        assertTrue(ints.size() == 200);
        //System.out.println(traversed);
    }

    @Test
    public void testObjectMapTraversal()
        throws Exception
    {
        MapTestObject obj = new MapTestObject(1);
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testObjectMapTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_simpleMap"));
        assertTrue(traversed.containsKey("_complexMap"));
        assertTrue(traversed.containsKey("_simpleObject"));
        List ints = traversed.get("java.lang.String");
        assertNotNull(ints);
        assertTrue(ints.size() == 300);
        ints = traversed.get("_integer");
        assertNotNull(ints);
        assertTrue(ints.size() == 100);
        //System.out.println(traversed);
    }

    @Test
    public void testComplexObjectMapTraversal()
        throws Exception
    {
        MapTestObject obj = new MapTestObject(2);
        SimpleOT traverse = new SimpleOT();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        Logger log = logger().glog("TestObjectTraversal");
        perf().startHere("testComplexObjectMapTraversal");
        traversal.traverse();
        perf().dumpHere(log);
        Map<String, List<Object>> traversed = traverse.getTraversed();
        assertTrue(traversed.containsKey("_simpleMap"));
        assertTrue(traversed.containsKey("_complexMap"));
        assertTrue(traversed.containsKey("_simpleObject"));
        List ints = traversed.get("java.lang.String");
        assertNotNull(ints);
        assertTrue(ints.size() == 300);
        ints = traversed.get("_integer");
        assertNotNull(ints);
        assertTrue(ints.size() == 300);
        //System.out.println(traversed);
    }

    @Test
    public void testClassTraversal()
        throws Exception
    {
        SimpleCreate create = new SimpleCreate();
        ClassTraversal ctraversal = new ClassTraversal(SimpleTestObject.class, create);
        Object ret = ctraversal.traverse();
        System.out.println(ret);
        assertTrue(ret != null);
        assertTrue(ret instanceof SimpleTestObject);
        create.assertObject((SimpleTestObject)ret);
        System.out.println("Starting complex test.");
        ComplexCreate ccreate = new ComplexCreate();
        ctraversal = new ClassTraversal(ComplexTestObject.class, ccreate);
        ret = ctraversal.traverse();
        assertTrue(ret != null);
        assertTrue(ret instanceof ComplexTestObject);
        ccreate.assertObject((ComplexTestObject)ret);
        System.out.println(ret);
        System.out.println("Starting List test.");
        ListCreate lcreate = new ListCreate();
        ctraversal = new ClassTraversal(ListTestObject.class, lcreate);
        ret = ctraversal.traverse();
        assertTrue(ret != null);
        assertTrue(ret instanceof ListTestObject);
        lcreate.assertObject((ListTestObject)ret);
        System.out.println(ret);
        System.out.println("Starting Map test.");
        MapCreate mcreate = new MapCreate();
        ctraversal = new ClassTraversal(MapTestObject.class, mcreate);
        ret = ctraversal.traverse();
        assertTrue(ret != null);
        assertTrue(ret instanceof MapTestObject);
        mcreate.assertObject((MapTestObject)ret);
        System.out.println(ret);
    }

    @Test
    public void testClassCreation()
        throws Exception
    {
        MapCreate map = new MapCreate();
        Map values = map.getValues();
        Object ret = convert().mapToObject(MapTestObject.class, values);
        assertTrue(ret != null);
        assertTrue(ret instanceof MapTestObject);
        map.assertObject((MapTestObject)ret);
        System.out.println(ret);
        System.out.println("Starting List test... ");
        ListCreate lcreate = new ListCreate();
        values = lcreate.getValues();
        ret = convert().mapToObject(ListTestObject.class, values);
        assertTrue(ret != null);
        assertTrue(ret instanceof ListTestObject);
        lcreate.assertObject((ListTestObject)ret);
        System.out.println(ret);
    }

    @Test
    public void testchangeTraversal()
        throws Exception
    {
        ComplexTestObject obj1 = new ComplexTestObject();
        ComplexTestObject obj2 = new ComplexTestObject();
        Logger log = logger().glog("TestchangeTraversal");
        perf().startHere("testSimpleChangeTraversal");
        for (int i = 0; i < 1000; i++)
        {
            SimpleOT visit = new SimpleOT();
            ChangedVisitor cvisit = new ChangedVisitor(visit);
            ObjectTraversal traversal = new ObjectTraversal(cvisit, obj1, false, null, obj2);
            traversal.traverse();
        }
        perf().dumpHere(log);
        //Map<String, List<Object>> traversed = visit.getTraversed();
        //System.out.println(traversed);
    }

    @Test
    public void testdirtyFieldTraversal()
        throws Exception
    {
        ComplexTestObject o1 = new ComplexTestObject();
        ComplexTestObject o2 = new ComplexTestObject();
        Logger log = logger().glog("TestchangeTraversal");
        perf().startHere("testDirtyFieldTraversal");
        for (int i = 0; i < 1000; i++)
        {
            SimpleOT visit = new SimpleOT();
            DirtyFieldTraversal dtraverse = new DirtyFieldTraversal(visit, o1, o2, false);
            dtraverse.traverse();
        }
        perf().dumpHere(log);
        //Map<String, List<Object>> traversed = visit.getTraversed();
        //System.out.println(traversed);
    }

    //@Test
    public void testOTPerf()
        throws Exception
    {
        //SimpleTestObject obj = new SimpleTestObject();
        ComplexTestObject obj = new ComplexTestObject();
        //ListTestObject obj = new ListTestObject();
        Logger log = logger().glog("TestObjectTraversal");
        /*SimplePerfBytes traverse = new SimplePerfBytes();
        ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
        traversal.traverse();
        byte[] buffer = traverse.serialized();
        traverse = new SimplePerfBytes(buffer);
        ClassTraversal ctraversal = new ClassTraversal(ComplexTestObject.class, traverse);
        Object ret = ctraversal.traverse();*/
        Kryo kryo = new Kryo();
        kryo.register(ComplexTestObject.class);
        //kryo.register(ComplexTestObject.class);
        //kryo.register(SimpleTestObject.class);
        kryo.register(UUID.class, new UUIDSerializer());
        //kryo.setInstantiatorStrategy(new SerializingInstantiatorStrategy());
        perf().startHere("testSimpleObjectTraversal");
        //for (int i = 0; i < 100000; i++)
        {
            ByteArrayOutputStream ostr = new ByteArrayOutputStream();
            ObjectOutputStream str = new ObjectOutputStream(ostr);
            str.writeObject(obj);
            str.close();
            byte[] bytes = ostr.toByteArray();
            ostr.close();
            ByteArrayInputStream istr = new ByteArrayInputStream(bytes);
            ObjectInputStream oistr = new ObjectInputStream(istr);
            Object ret = oistr.readObject();
        }
        perf().checkpointHere("testStdSerialization");
        for (int i = 0; i < 100000; i++)
        {
            SimplePerf traverse = new SimplePerf();
            ObjectTraversal traversal = new ObjectTraversal(traverse, obj, false, null);
            traversal.traverse();
            //buffer = traverse.serialized();
            //traverse = new SimplePerf(buffer);
            //ctraversal = new ClassTraversal(ComplexTestObject.class, traverse);
            //ret = ctraversal.traverse();
        }
        perf().checkpointHere("testSimplePerfSerialization");
        //for (int i = 0; i < 100000; i++)
        {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            //testing if I can write the class bytes
            /*String name = obj.getClass().getName();
            int len = name.length();
            str.write((byte)(len >> 24));
            str.write((byte)(len >> 16));
            str.write((byte)(len >> 8));
            str.write((byte)(len));
            str.write(name.getBytes());*/
            Output kbuffer = new Output(str);
            kryo.writeObject(kbuffer, obj);
            kbuffer.close();
            byte[] serialized = str.toByteArray();
            str.close();
            ByteArrayInputStream istr = new ByteArrayInputStream(serialized);
            /*len = istr.read() << 24 | (istr.read() & 0xFF) << 16 | (istr.read() & 0xFF) << 8 | (istr.read() & 0xFF);
            byte[] bytes = new byte[len];
            istr.read(bytes, 0, len);
            name = new String(bytes);
            Class cls = this.getClass().getClassLoader().loadClass(name);*/
            Input ibuffer = new Input(istr);
            Object obj1 = kryo.readObject(ibuffer, ComplexTestObject.class);
        }
        perf().dumpHere(log);
    }

    private class UUIDSerializer extends Serializer<UUID>
    {
        public UUIDSerializer() 
        {
        }

        @Override
        public void write(Kryo paramKryo, Output paramOutput, UUID paramT)
        {
            UUID id = (UUID) paramT;
            StringSerializer s = new StringSerializer();
            s.write(paramKryo, paramOutput, id.toString());
        }

        @Override
        public UUID read(Kryo paramKryo, Input paramInput, Class<UUID> paramClass)
        {
            StringSerializer ser = new StringSerializer();
            String s = ser.read(paramKryo, paramInput, String.class);
            UUID id = UUID.fromString(s);
            return id;
        }
    }
}

