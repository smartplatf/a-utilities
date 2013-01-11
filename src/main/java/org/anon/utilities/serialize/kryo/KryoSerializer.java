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
 * File:                org.anon.utilities.serialize.kryo.KryoSerializer
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A serializer using kryo serialization
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.kryo;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.Input;

import org.anon.utilities.loader.RelatedLoader;
import org.anon.utilities.serialize.Serializer;
import org.anon.utilities.serialize.LoaderResolver;
import org.anon.utilities.serialize.Constants;

public class KryoSerializer implements Serializer, Constants
{
    private KryoPoolObject _kryo;
    private KryoPool _pool;

    public KryoSerializer(KryoPool pool, KryoPoolObject obj)
    {
        _kryo = obj;
        _pool = pool;
    }

    public void serialize(Object obj, OutputStream str)
        throws IOException
    {
        if (obj == null)
            return;

        if (_kryo == null)
            throw new IOException("Cannot serialize with null Kryo");

        String name = obj.getClass().getName();
        String loader = SYSTEM_LDR;
        if (obj.getClass().getClassLoader() instanceof RelatedLoader)
            loader = obj.getClass().getClassLoader().toString();
        Kryo kryo = _kryo.getKryo();
        Output write = new Output(str);
        write.writeString(name);
        write.writeString(loader);
        kryo.writeObject(write, obj);
        write.close(); //flush it out
    }

    public Object deserialize(InputStream istr, LoaderResolver resolve)
        throws IOException
    {
        try
        {
            Input input = new Input(istr);
            String name = input.readString();
            String ldr = input.readString();
            ClassLoader cldr = null;
            if ((resolve != null) && (name != null) && (!name.equals(SYSTEM_LDR)))
                cldr = resolve.resolveLoader(ldr);
            else if (resolve != null)
                cldr = resolve.defaultLoader();
            else
                cldr = this.getClass().getClassLoader();

            Class cls = cldr.loadClass(name);
            Kryo kryo = _kryo.getKryo();
            Object ret = kryo.readObject(input, cls);
            input.close();
            return ret;
        }
        catch (Exception e)
        {
            throw new IOException(e);
        }
    }

    public void close()
        throws IOException
    {
        try
        {
            _pool.unlockone(_kryo);
        }
        catch (Exception e)
        {
            throw new IOException(e);
        }
    }
}

