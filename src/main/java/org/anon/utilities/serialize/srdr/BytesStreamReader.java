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
 * File:                org.anon.utilities.serialize.srdr.BytesStreamReader
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A reader that reads bytes according to primitive format
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.srdr;

public class BytesStreamReader extends BytesReader
{
    private byte[] _read;
    private int _currInd;

    public BytesStreamReader(byte[] serialized)
    {
        _read = serialized;
        _currInd = 0;
    }

    final byte peekByte()
    {
        return _read[_currInd];
    }

    final byte nextByte()
    {
        byte ret = _read[_currInd++];
        return ret;
    }

    final boolean nextBoolean()
    {
        boolean ret = readBoolean(_read, _currInd);
        _currInd += 1;
        return ret;
    }

    final char nextChar()
    {
        char ret = readChar(_read, _currInd);
        _currInd += 2;
        return ret;
    }

    final int nextInt()
    {
        int ret = readInt(_read, _currInd);
        _currInd += 4;
        return ret;
    }

    final float nextFloat()
    {
        float ret = readFloat(_read, _currInd);
        _currInd += 4;
        return ret;
    }

    final long nextLong()
    {
        long ret = readLong(_read, _currInd);
        _currInd += 8;
        return ret;
    }

    final double nextDouble()
    {
        double ret = readDouble(_read, _currInd);
        _currInd += 8;
        return ret;
    }

    final short nextShort()
    {
        short ret = readShort(_read, _currInd);
        _currInd += 2;
        return ret;
    }

    final String nextString()
    {
        int len = nextShort();
        byte[] rbytes = new byte[len];
        System.arraycopy(_read, _currInd, rbytes, 0, len);
        _currInd += len;
        return new String(rbytes);
    }

    final Object nextPrimitiveFor(char type)
    {
        Object ret = null;
        switch (type)
        {
        case 'B':
            ret = new Byte(_read[_currInd++]);
            break;
        case 'S':
            ret = new Short(nextShort());
            break;
        case 'C':
            ret = new Character(nextChar());
            break;
        case 'D':
            ret = new Double(nextDouble());
            break;
        case 'J':
            ret = new Long(nextLong());
            break;
        case 'F':
            ret = new Float(nextFloat());
            break;
        case 'I':
            ret = new Integer(nextInt());
            break;
        case 'Z':
            ret = new Boolean(nextBoolean());
            break;
        }

        return ret;
    }

    final static boolean isPrimitive(String clsname)
    {
        boolean ret = (clsname.length() == 1);
        char type = clsname.charAt(0);
        switch (type)
        {
        case 'B':
        case 'S':
        case 'C':
        case 'D':
        case 'J':
        case 'F':
        case 'I':
        case 'Z':
            ret = ret && true;
            break;
        default:
            ret = false;
        }

        return ret;
    }
}

