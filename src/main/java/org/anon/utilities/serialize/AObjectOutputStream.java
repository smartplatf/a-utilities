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
 * File:                org.anon.utilities.serialize.AObjectOutputStream
 * Author:              rsankar
 * Revision:            1.0
 * Date:                04-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A serializer in which serialization technique can be varied
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import org.anon.utilities.loader.RelatedLoader;

public class AObjectOutputStream extends ObjectOutputStream implements Constants
{
    private Serializer _serializer;
    private OutputStream _stream;

    public AObjectOutputStream(OutputStream str, Serializer s)
        throws IOException
    {
        super();
        _serializer = s;
        _stream = str;
    }

    public AObjectOutputStream(OutputStream str)
        throws IOException
    {
        //default java serialization
        super(str);
    }

    @Override
    protected void writeClassDescriptor(ObjectStreamClass desc)
        throws IOException
    {
        Class cls = desc.forClass();
        String name = cls.getName();
        writeObject(name);
        String ldrname = SYSTEM_LDR;
        if (cls.getClassLoader() instanceof RelatedLoader)
            ldrname = cls.getClassLoader().toString();
        writeObject(ldrname);
    }

    @Override
    protected void writeObjectOverride(Object obj)
        throws IOException
    {
        _serializer.serialize(obj, _stream);
    }

    @Override
    public void close()
        throws IOException
    {
        if (_serializer != null)
            _serializer.close();
        else
            super.close();
    }
}

