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
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.anon.utilities.serialize.srdr.BytesReader
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * a reader of primitive types from bytes
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.srdr;

public class BytesReader
{
    protected BytesReader()
    {
    }

    final boolean readBoolean(byte[] b, int off) 
    {
        return b[off] != 0;
    }

    final char readChar(byte[] b, int off) 
    {
        return (char) (((b[off + 1] & 0xFF) << 0) +
                       ((b[off + 0]) << 8));
    }

    final short readShort(byte[] b, int off) 
    {
        return (short) (((b[off + 1] & 0xFF) << 0) +
                        ((b[off + 0]) << 8));
    }

    final int readUnsignedShort(byte[] b, int off) 
    {
        int ch1 = readChar(b, off);
        int ch2 = readChar(b, off + 1);
        return (ch1 << 8) + (ch2 << 0);
    }

    final int readInt(byte[] b, int off) 
    {
        return ((b[off + 3] & 0xFF) << 0) +
               ((b[off + 2] & 0xFF) << 8) +
               ((b[off + 1] & 0xFF) << 16) +
               ((b[off + 0]) << 24);
    }

    final float readFloat(byte[] b, int off) 
    {
        int i = ((b[off + 3] & 0xFF) << 0) +
                ((b[off + 2] & 0xFF) << 8) +
                ((b[off + 1] & 0xFF) << 16) +
                ((b[off + 0]) << 24);
        return Float.intBitsToFloat(i);
    }

    final long readLong(byte[] b, int off) 
    {
        return ((b[off + 7] & 0xFFL) << 0) +
               ((b[off + 6] & 0xFFL) << 8) +
               ((b[off + 5] & 0xFFL) << 16) +
               ((b[off + 4] & 0xFFL) << 24) +
               ((b[off + 3] & 0xFFL) << 32) +
               ((b[off + 2] & 0xFFL) << 40) +
               ((b[off + 1] & 0xFFL) << 48) +
               (((long) b[off + 0]) << 56);
    }

    final double readDouble(byte[] b, int off) 
    {
        long j = ((b[off + 7] & 0xFFL) << 0) +
                 ((b[off + 6] & 0xFFL) << 8) +
                 ((b[off + 5] & 0xFFL) << 16) +
                 ((b[off + 4] & 0xFFL) << 24) +
                 ((b[off + 3] & 0xFFL) << 32) +
                 ((b[off + 2] & 0xFFL) << 40) +
                 ((b[off + 1] & 0xFFL) << 48) +
                 (((long) b[off + 0]) << 56);
        return Double.longBitsToDouble(j);
    }
}

