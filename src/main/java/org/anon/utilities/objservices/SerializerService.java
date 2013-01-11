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
 * File:                org.anon.utilities.objservices.SerializerService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service to serialize
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import java.util.List;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.serialize.Serializer;
import org.anon.utilities.serialize.AObjectInputStream;
import org.anon.utilities.serialize.AObjectOutputStream;
import org.anon.utilities.serialize.SerializerFactory;
import org.anon.utilities.serialize.srdr.DirtyField;
import org.anon.utilities.serialize.srdr.SerialStreamReader;
import org.anon.utilities.serialize.srdr.StreamComparator;
import org.anon.utilities.exception.CtxException;

public class SerializerService extends ObjectServiceLocator.ObjectService
{
    private SerializerFactory.serializers _currentSerializer;

    public SerializerService()
    {
        super();
        String ser = System.getenv("SERIALIZER");
        if (ser == null)
            ser = "std";
        _currentSerializer = SerializerFactory.serializers.valueOf(ser);
    }

    public void serialize(OutputStream str, Object obj)
        throws CtxException
    {
        if (obj == null)
            return;

        try
        {
            Serializer serialize = _currentSerializer.serializerFor();
            ObjectOutputStream ostr = null;
            if (serialize == null)
                ostr = new AObjectOutputStream(str);
            else
                ostr = new AObjectOutputStream(str, serialize);
            ostr.writeObject(obj);
            ostr.close();
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("SerializerService.serialize", "Exception"));
        }
    }

    public Object deserialize(InputStream str)
        throws CtxException
    {
        Object ret = null;
        try
        {
            Serializer serialize = _currentSerializer.serializerFor();
            ObjectInputStream istr = null;
            if (serialize == null)
                istr = new AObjectInputStream(str);
            else
                istr = new AObjectInputStream(str, serialize);
            ret = istr.readObject();
            istr.close();
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("SerializerService.deserialize", "Exception"));
        }
        return ret;
    }

    public boolean same(Object obj, Object fromobj)
        throws CtxException
    {
        //This is a slow process if we want to iterate thru fields that have changed
        //use this to check that there is a change and iterate thru dirtyfields
        if (obj == fromobj)
            return true; //same references

        boolean ret = ((obj == null) || (fromobj == null)) ? (obj == fromobj) : (obj.getClass().equals(fromobj.getClass()));
        if ((obj != null) && (fromobj != null) && ret)
        {
            if ((type().checkPrimitive(obj.getClass())) || (type().checkStandard(obj.getClass())))
                return obj.equals(fromobj);

            try
            {
                ByteArrayOutputStream ostr1 = new ByteArrayOutputStream();
                serialize(ostr1, obj);
                byte[] bytes = ostr1.toByteArray();
                ostr1.close();
                ostr1 = new ByteArrayOutputStream();
                serialize(ostr1, fromobj);
                byte[] frombytes = ostr1.toByteArray();
                ostr1.close();
                ret = (bytes.length == frombytes.length);
                for (int i = 0; ret && (i < frombytes.length); i++)
                    ret = ret && (bytes[i] == frombytes[i]);
            }
            catch (Exception e)
            {
                except().rt(e, new CtxException.Context("SerializerService.differs", "Exception"));
            }
        }

        return ret;
    }

    public List<DirtyField> dirtyFields(Object obj, Object original)
        throws CtxException
    {
        if ((obj == null) || (original == null))
            return null;

        try
        {
            ByteArrayOutputStream ostr1 = new ByteArrayOutputStream();
            ObjectOutputStream ostr = new AObjectOutputStream(ostr1);
            ostr.writeObject(obj);
            ostr.close();
            byte[] mod = ostr1.toByteArray();
            ostr1.close();
            ostr1 = new ByteArrayOutputStream();
            ostr = new AObjectOutputStream(ostr1);
            ostr.writeObject(original);
            ostr.close();
            byte[] orig = ostr1.toByteArray();
            ostr1.close();
            /* This is just for debugging
            java.io.FileOutputStream fos = new java.io.FileOutputStream("serial-obj.dat");
            fos.write(mod);
            fos.close();
            fos = new java.io.FileOutputStream("serial-orig.dat");
            fos.write(orig);
            fos.close();
            */
            SerialStreamReader ordr = new SerialStreamReader(mod);
            SerialStreamReader frdr = new SerialStreamReader(orig);
            StreamComparator comp = new StreamComparator(ordr, frdr);
            return comp.dirtyFields();
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("SerializerService.serialize", "Exception"));
        }

        return null;
    }
}

