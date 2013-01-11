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
 * File:                org.anon.utilities.test.reflect.SimplePerf
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * a traversal of simple object
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.reflect.CVisitor;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.exception.CtxException;

public class SimplePerf implements CVisitor
{
    static interface Serializer
    {
        public void writeBytes(Object value, ByteBuffer buffer);
        public Object readBytes(ByteBuffer buffer, Class cls);
    }

    static class StringSerializer implements Serializer
    {
        public void writeBytes(Object value, ByteBuffer buffer)
        {
            String str = "";
            if (value instanceof UUID)
                str = ((UUID)value).toString();
            else
                str = value.toString();

            byte[] bytes = str.getBytes();
            int len = bytes.length;
            buffer.putInt(len);
            for (int i = 0; i < len; i++)
                buffer.put(bytes[i]);
        }

        public Object readBytes(ByteBuffer buffer, Class cls)
        {
            int len = buffer.getInt();
            byte[] bytes = new byte[len];
            for (int i = 0; i < len; i++)
                bytes[i] = buffer.get();

            Object ret = new String(bytes);

            if (cls.isAssignableFrom(UUID.class))
                ret = UUID.fromString(ret.toString());

            return ret;
        }
    }

    static class IntSerializer implements Serializer
    {
        public void writeBytes(Object value, ByteBuffer buffer)
        {
            Integer ivalue = (Integer)value;
            buffer.putInt(ivalue.intValue());
        }

        public Object readBytes(ByteBuffer buffer, Class cls)
        {
            int val = buffer.getInt();
            return new Integer(val);
        }
    }

    static class LongSerializer implements Serializer
    {
        public void writeBytes(Object value, ByteBuffer buffer)
        {
            long val = 0;
            if (value instanceof Date)
            {
                Date dt = (Date)value;
                val = dt.getTime();
            }
            else
            {
                Long ivalue = (Long)value;
                val = ivalue.longValue();
            }
            buffer.putLong(val);
        }

        public Object readBytes(ByteBuffer buffer, Class cls)
        {
            long val = buffer.getLong();
            Object ret = new Long(val);
            if (cls.isAssignableFrom(Date.class))
                ret = new Date(val);

            return ret;
        }
    }

    static class DoubleSerializer implements Serializer
    {
        public void writeBytes(Object value, ByteBuffer buffer)
        {
            double putval = 0;
            if (value instanceof BigDecimal)
            {
                BigDecimal dec = (BigDecimal)value;
                putval = dec.doubleValue();
            }
            else
            {
                Double ivalue = (Double)value;
                putval = ivalue.doubleValue();
            }
            buffer.putDouble(putval);
        }

        public Object readBytes(ByteBuffer buffer, Class cls)
        {
            double val = buffer.getDouble();
            Object ret = new Double(val);
            if (cls.isAssignableFrom(BigDecimal.class))
                ret = new BigDecimal(val);
            return ret;
        }
    }

    static class FloatSerializer implements Serializer
    {
        public void writeBytes(Object value, ByteBuffer buffer)
        {
            Float ivalue = (Float)value;
            buffer.putFloat(ivalue.floatValue());
        }

        public Object readBytes(ByteBuffer buffer, Class cls)
        {
            float val = buffer.getFloat();
            return new Float(val);
        }
    }

    private static Map<Class, Serializer> _serializer = new HashMap<Class, Serializer>();

    static
    {
        _serializer.put(Integer.class, new IntSerializer());
        _serializer.put(Integer.TYPE, new IntSerializer());
        _serializer.put(Float.class, new FloatSerializer());
        _serializer.put(Float.TYPE, new FloatSerializer());
        _serializer.put(Double.class, new DoubleSerializer());
        _serializer.put(Double.TYPE, new DoubleSerializer());
        _serializer.put(BigDecimal.class, new DoubleSerializer());
        _serializer.put(UUID.class, new StringSerializer());
        _serializer.put(String.class, new StringSerializer());
        _serializer.put(Long.class, new LongSerializer());
        _serializer.put(Long.TYPE, new LongSerializer());
        _serializer.put(Date.class, new LongSerializer());
    }

    private ByteBuffer _buffer;
    private int _length;
    private boolean _serialize = true;

    public SimplePerf()
    {
        //for now, need to see how to do this
        _buffer = ByteBuffer.allocate(1024);
        _length = 0;
        _buffer.putInt(_length);
        _serialize = true;
    }

    public SimplePerf(ByteBuffer buffer)
    {
        _buffer = buffer;
        _length = _buffer.getInt();
        _serialize = false;
    }

    public int collectionSize(DataContext ctx)
    {
        return 0;
    }

    public Set keySet(DataContext ctx)
    {
        return null;
    }

    public Object serialize(DataContext ctx)
        throws CtxException
    {
        Object ret = null;
        if (ctx.field() != null)
        {
            ret = ctx.fieldVal();
            Serializer serial = _serializer.get(ctx.fieldType());
            //have to handle null etc
            if (serial != null)
            {
                serial.writeBytes(ret, _buffer);
                perf().checkpointHere("fld:" + ctx.field().getName());
            }

        }
        else
        {
            ret = ctx.traversingObject();
        }

        return ret;
    }

    public Object deserialize(DataContext ctx)
        throws CtxException
    {
        Object ret = null;
        if (ctx.field() != null)
        {
            ret = ctx.fieldVal();
            Serializer serial = _serializer.get(ctx.fieldType());
            //have to handle null etc
            if (serial != null)
            {
                ret = serial.readBytes(_buffer, ctx.fieldType());
            }
            else if (_buffer.position() < _length)
                ret = _buffer;
        }
        else if (_buffer.position() < _length)
        {
            ret = _buffer; //so we can create for now?
        }

        return ret;
    }

    public ByteBuffer serialized() 
    { 
        int len = _buffer.position(); 
        _buffer.rewind();
        _buffer.putInt(len);
        _buffer.rewind();
        return _buffer;
    }

    public Object visit(DataContext ctx)
        throws CtxException
    {
        /*if (_serialize)
            return serialize(ctx);
        else
            return deserialize(ctx);*/
        Object ret = null;
        if (ctx.field() != null)
            ret = ctx.fieldVal();
        else
            ret = ctx.traversingObject();

        return ret;
    }
}

