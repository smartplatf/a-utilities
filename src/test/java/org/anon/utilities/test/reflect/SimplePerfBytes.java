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
 * File:                org.anon.utilities.test.reflect.SimplePerfBytes
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.reflect.CVisitor;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.exception.CtxException;

public class SimplePerfBytes implements CVisitor
{
    static interface Serializer
    {
        public int writeBytes(Object value, byte[] buffer, int position);
        public Object readBytes(byte[] buffer, int position, Class cls);
        public int currentPosition();
    }

    static class StringSerializer implements Serializer
    {
        int readPos = 0;
        public int writeBytes(Object value, byte[] buffer, int position)
        {
            String str = "";
            if (value instanceof UUID)
                str = ((UUID)value).toString();
            else
                str = value.toString();

            byte[] bytes = str.getBytes();
            int len = bytes.length;
            int loc = position;
            buffer[loc++] = (byte)(len >> 24);
            buffer[loc++] = (byte)(len >> 16);
            buffer[loc++] = (byte)(len >> 8);
            buffer[loc++] = (byte)(len);
            for (int i = 0; i < len; i++)
                buffer[loc++] = bytes[i];
            return loc;
        }

        public int currentPosition() { return readPos; }

        public Object readBytes(byte[] buffer, int position, Class cls)
        {
            int loc = position;
            int len = buffer[loc++] << 24 | (buffer[loc++] & 0xFF) << 16 | (buffer[loc++] & 0xFF) << 8 | (buffer[loc++] & 0xFF);
            byte[] bytes = new byte[len];
            for (int i = 0; i < len; i++)
                bytes[i] = buffer[loc++];

            Object ret = new String(bytes);

            if (cls.isAssignableFrom(UUID.class))
                ret = UUID.fromString(ret.toString());

            readPos = loc;
            return ret;
        }
    }

    static class IntSerializer implements Serializer
    {
        int readPos = 0;
        public int writeBytes(Object value, byte[] buffer, int position)
        {
            Integer ivalue = (Integer)value;
            int put = (ivalue.intValue());
            int loc = position;
            buffer[loc++] = (byte)(put >> 24);
            buffer[loc++] = (byte)(put >> 16);
            buffer[loc++] = (byte)(put >> 8);
            buffer[loc++] = (byte)(put);
            return loc;
        }

        public int currentPosition() { return readPos; }
        public Object readBytes(byte[] buffer, int position, Class cls)
        {
            int loc = position;
            int val = buffer[loc++] << 24 | (buffer[loc++] & 0xFF) << 16 | (buffer[loc++] & 0xFF) << 8 | (buffer[loc++] & 0xFF);
            readPos = loc;
            return new Integer(val);
        }
    }

    static class LongSerializer implements Serializer
    {
        int readPos = 0;
        public int writeBytes(Object value, byte[] buffer, int position)
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

            int loc = position;
            buffer[loc++] = (byte)(val >> 56);
            buffer[loc++] = (byte)(val >> 48);
            buffer[loc++] = (byte)(val >> 40);
            buffer[loc++] = (byte)(val >> 32);
            buffer[loc++] = (byte)(val >> 24);
            buffer[loc++] = (byte)(val >> 16);
            buffer[loc++] = (byte)(val >>  8);
            buffer[loc++] = (byte)(val >>  0);
            return loc;
        }

        public int currentPosition() { return readPos; }
        public Object readBytes(byte[] buffer, int position, Class cls)
        {
            int loc = position;
            long val =  ((long)(buffer[loc++] & 0xff) << 56) + 
                        ((long)(buffer[loc++] & 0xFF) << 48) +
                        ((long)(buffer[loc++] & 0xFF) << 40) +
                        ((long)(buffer[loc++] & 0xFF) << 32) +
                        ((long)(buffer[loc++] & 0xFF) << 24) +
                        ((long)(buffer[loc++] & 0xFF) << 16) + 
                        ((long)(buffer[loc++] & 0xFF) << 8) +
                        ((long)(buffer[loc++] & 0xFF));

            readPos = loc;
            Object ret = new Long(val);
            if (cls.isAssignableFrom(Date.class))
                ret = new Date(val);

            return ret;
        }
    }

    static class DoubleSerializer implements Serializer
    {
        int readPos = 0;
        public int writeBytes(Object value, byte[] buffer, int position)
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
            long val = Double.doubleToRawLongBits(putval);
            int loc = position;
            buffer[loc++] = (byte)(val >> 56);
            buffer[loc++] = (byte)(val >> 48);
            buffer[loc++] = (byte)(val >> 40);
            buffer[loc++] = (byte)(val >> 32);
            buffer[loc++] = (byte)(val >> 24);
            buffer[loc++] = (byte)(val >> 16);
            buffer[loc++] = (byte)(val >>  8);
            buffer[loc++] = (byte)(val >>  0);
            return loc;
        }

        public int currentPosition() { return readPos; }
        public Object readBytes(byte[] buffer, int position, Class cls)
        {
            int loc = position;
            long lbits =  ((long)(buffer[loc++] & 0xff) << 56) + 
                        ((long)(buffer[loc++] & 0xFF) << 48) +
                        ((long)(buffer[loc++] & 0xFF) << 40) +
                        ((long)(buffer[loc++] & 0xFF) << 32) +
                        ((long)(buffer[loc++] & 0xFF) << 24) +
                        ((long)(buffer[loc++] & 0xFF) << 16) + 
                        ((long)(buffer[loc++] & 0xFF) << 8) +
                        ((long)(buffer[loc++] & 0xFF));

            readPos = loc;
            double val = Double.longBitsToDouble(lbits);
            Object ret = new Double(val);
            if (cls.isAssignableFrom(BigDecimal.class))
                ret = new BigDecimal(val);
            return ret;
        }
    }

    static class FloatSerializer implements Serializer
    {
        int readPos = 0;
        public int writeBytes(Object value, byte[] buffer, int position)
        {
            Float ivalue = (Float)value;
            int put = Float.floatToRawIntBits(ivalue.floatValue());
            int loc = position;
            buffer[loc++] = (byte)(put >> 24);
            buffer[loc++] = (byte)(put >> 16);
            buffer[loc++] = (byte)(put >> 8);
            buffer[loc++] = (byte)(put);
            return loc;
        }

        public int currentPosition() { return readPos; }
        public Object readBytes(byte[] buffer, int position, Class cls)
        {
            int loc = position;
            int fbits = buffer[loc++] << 24 | (buffer[loc++] & 0xFF) << 16 | (buffer[loc++] & 0xFF) << 8 | (buffer[loc++] & 0xFF);
            readPos = loc;
            float val = Float.intBitsToFloat(fbits);
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

    private byte[] _buffer;
    private int _length;
    private int _currPosition;
    private boolean _serialize = true;

    public SimplePerfBytes()
    {
        //for now, need to see how to do this
        _buffer = new byte[1024];
        _length = 0;
        //_buffer.putInt(_length);
        _serialize = true;
        _currPosition = 4;//starts the first with the length
    }

    public SimplePerfBytes(byte[] buffer)
    {
        _buffer = buffer;
        Serializer s = _serializer.get(Integer.class);
        _length = ((Integer)s.readBytes(_buffer, 0, Integer.TYPE)).intValue();
        _currPosition = s.currentPosition();
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
                _currPosition = serial.writeBytes(ret, _buffer, _currPosition);
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
                ret = serial.readBytes(_buffer, _currPosition, ctx.fieldType());
                _currPosition = serial.currentPosition();
                //perf().checkpointHere("read: " + ctx.field().getName());
            }
            else if (_currPosition < _length)
                ret = _buffer;
        }
        else if (_currPosition < _length)
        {
            ret = _buffer; //so we can create for now?
        }

        return ret;
    }

    public byte[] serialized() 
    { 
        int len = _currPosition;
        Serializer s = _serializer.get(Integer.class);
        s.writeBytes(len, _buffer, 0);
        return _buffer;
    }

    public Object visit(DataContext ctx)
        throws CtxException
    {
        if (_serialize)
            return serialize(ctx);
        else
        {
            return deserialize(ctx);
            /*Object ret = null;
            if (ctx.field() != null)
                ret = ctx.fieldVal();
            else
                ret = ctx.traversingObject();

            return ret;*/
        }
    }
}

