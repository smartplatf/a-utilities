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
 * File:                org.anon.utilities.serialize.srdr.SerialStreamReader
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A reader of the serialized stream
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.srdr;

import java.util.List;
import java.util.ArrayList;
import java.io.ObjectStreamConstants;

public class SerialStreamReader extends BytesStreamReader implements ObjectStreamConstants
{
    //private BytesStreamReader _reader;
    private short _version;
    private short _magic;

    public SerialStreamReader(byte[] bytes)
    {
        super(bytes);
        //_reader = new BytesStreamReader(bytes);
        _magic = nextShort();
        _version = nextShort();
    }

    String stringBlock()
    {
        String ret = "";
        byte what = nextByte();
        if (what == TC_STRING)
        {
            ret = nextString();
        }
        else if (what == TC_REFERENCE)
        {
            //references an int, what to do about this?
            nextInt();
        }
        else if( what == TC_NULL)
        {
        	System.out.println("StringBlock NULL REF:");
        }
        return ret;
    }

    int blockInt()
    {
        int ret = -1;
        byte what = nextByte();
        if (what == TC_BLOCKDATA)
        {
            byte len = nextByte();
            ret = nextInt();
        }
        return ret;
    }

    long blockLong()
    {
        long ret = -1;
        byte what = nextByte();
        if (what == TC_BLOCKDATA)
        {
            byte len = nextByte();
            ret = nextLong();
        }
        return ret;
    }

    Object nextPrimitive(char type)
    {
        return nextPrimitiveFor(type);
    }

    List<String> nextBlockDesc()
    {
        List<String> ret = new ArrayList<String>();
        byte desc = nextByte();
        switch (desc)
        {
        case TC_CLASSDESC:
            {
                String clsname = stringBlock();
                if (clsname.length() > 0)
                {
                    ret.add(clsname);
                    byte what = peekByte();
                    if (what == TC_STRING)
                    {
                        stringBlock();
                    }
                    else if (what == TC_REFERENCE)
                    {
                        what = nextByte();
                        nextInt();
                    }
                }
                byte end = nextByte();
                List<String> parent = nextBlockDesc();
                ret.addAll(parent);
            }
            break;
        case TC_REFERENCE:
            nextInt();
            break;
        case TC_NULL:
            break;
        }

        return ret;
    }

    List<String> nextClassDescriptor()
    {
        List<String> readCls = new ArrayList<String>();
        byte read = nextByte();
        //ignore endblocks?? no idea why this is occuring??
        //occurs in BigDecimal
        while (read == TC_ENDBLOCKDATA)
            read = nextByte();
        switch (read)
        {
        case TC_OBJECT:
        case TC_ARRAY:
            {
                List<String> one = nextBlockDesc();
                readCls.addAll(one);
            }
            break;
        case TC_REFERENCE:
            nextInt();
            break;
        case TC_CLASS:
            break;
        }

        return readCls;
    }
}

