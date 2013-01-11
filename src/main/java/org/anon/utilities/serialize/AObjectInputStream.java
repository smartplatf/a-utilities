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
 * File:                org.anon.utilities.serialize.AObjectInputStream
 * Author:              rsankar
 * Revision:            1.0
 * Date:                04-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A deserializer where serialization technique can be varied
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize;

import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import static org.anon.utilities.services.ServiceLocator.*;

public class AObjectInputStream extends ObjectInputStream implements Constants
{
    private InputStream _istream;
    private Serializer _serializer;
    private LoaderResolver _resolver;

    public AObjectInputStream(InputStream str, Serializer s)
        throws IOException
    {
        super();
        _serializer = s;
        _istream = str;
        _resolver = new DefaultLoaderResolver(s);
    }

    public AObjectInputStream(InputStream str)
        throws IOException
    {
        //use default serialization
        super(str);
        _resolver = new DefaultLoaderResolver();
    }

    public AObjectInputStream(InputStream str, LoaderResolver resolve)
        throws IOException
    {
        super(str);
        _resolver = resolve;
    }

    @Override
    protected ObjectStreamClass readClassDescriptor()
        throws IOException, ClassNotFoundException
    {
        String name = (String)readObject();
        String ldr = (String)readObject();
        ObjectStreamClass cls = null;
        Class clazz = type().classForPrimitive(name);
        if (clazz == null)
        {
            if (ldr.equals(SYSTEM_LDR))
                clazz = _resolver.defaultLoader().loadClass(name);
            else
                clazz = _resolver.resolveLoader(ldr).loadClass(name);
        }
        cls = ObjectStreamClass.lookup(clazz);
        return cls;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc)
        throws IOException, ClassNotFoundException
    {
        ClassLoader ldr = desc.forClass().getClassLoader();
        return Class.forName(desc.getName(), false, ldr);
    }

    @Override
    protected Object readObjectOverride()
        throws IOException, ClassNotFoundException
    {
        return _serializer.deserialize(_istream, _resolver);
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

