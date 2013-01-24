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
 * File:                org.anon.utilities.objservices.ConvertService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                30-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service that converts between types
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import java.util.Map;
import java.util.Date;
import java.util.UUID;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Collection;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.lang.reflect.Constructor;

import static org.anon.utilities.services.ServiceLocator.*;

import org.anon.utilities.lang.Translator;
import org.anon.utilities.lang.json.JSONTranslator;
import org.anon.utilities.verify.VerifiableObject;
import org.anon.utilities.reflect.CreatorFromMap;
import org.anon.utilities.reflect.ClassTraversal;
import org.anon.utilities.reflect.ObjectTraversal;
import org.anon.utilities.reflect.MapFromObject;
import org.anon.utilities.exception.CtxException;

public class ConvertService extends ObjectServiceLocator.ObjectService
{
    private static final String FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

    public static enum translator
    {
        json(new JSONTranslator());

        Translator _translator;

        private translator(Translator t)
        {
            _translator = t;
        }

        public Translator translatorFor()
        {
            return _translator;
        }
    }

    private Map<Class, Method> _strConverters;

    public ConvertService()
    {
        super();
        _strConverters = new HashMap<Class, Method>();

        try
        {
            Method mthd = UUID.class.getDeclaredMethod("fromString", String.class);
            _strConverters.put(UUID.class, mthd);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public <T> T stringToClass(String val, Class<T> cls)
        throws CtxException
    {
        try
        {
            if (_strConverters.containsKey(cls))
            {
                Method m = _strConverters.get(cls);
                Object ret = m.invoke(null, val);
                return cls.cast(ret);
            }

            if (cls.equals(Date.class))
                return cls.cast(stringToDate(val, null));

            if (cls.isPrimitive())
                cls = type().wrapperFor(cls);
            Constructor cons = cls.getDeclaredConstructor(String.class);
            assertion().assertNotNull(cons, "No conversion constructor found.");
            Object ret = cons.newInstance(val);
            return cls.cast(ret);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot createFrom: " + val, cls.getName()));
        }
        return null;
    }

    public boolean stringToBoolean(String val)
    {
        boolean ret = ((val != null) && (val.equalsIgnoreCase("yes")));
        ret = (ret || ((val != null) && (val.equalsIgnoreCase("true"))));
        return ret;
    }

    public String objectToString(Object val)
        throws CtxException
    {
        try
        {
            Class cls = val.getClass();
            if (cls.isPrimitive())
            {
                Class wrap = type().wrapperFor(cls);
                Constructor cons = wrap.getDeclaredConstructor(cls);
                assertion().assertNotNull(cons, "Cannot convert into object for type: " + cls.getName());
                val = cons.newInstance(val);
            }
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot convert: " + val, val.getClass().getName()));
        }

        return val.toString();
    }

    public Date stringToDate(String val, String fmt)
        throws CtxException
    {
        try
        {
            if (fmt == null) //use default fmt
                fmt = FORMAT;
            SimpleDateFormat format = new SimpleDateFormat(fmt);
            Date dt = format.parse(val);
            return dt;
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot convert: " + val, Date.class.getName()));
        }

        return null;
    }

    public String dateToString(Date val, String fmt)
        throws CtxException
    {
        try
        {
            if (fmt == null)
                fmt = FORMAT;
            SimpleDateFormat format =  new SimpleDateFormat(fmt);
            String ret = format.format(val);
            return ret;
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot convert: " + val, Date.class.getName()));
        }

        return "";
    }

    public String dateToTZString(Date dt, String tz)
        throws CtxException
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat();
            format.setTimeZone(TimeZone.getTimeZone(tz));
            return format.format(dt);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot convert: " + dt, tz));
        }
        return "";
    }

    public String dateToGMTString(Date val)
        throws CtxException
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat();
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            return format.format(val);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot convert: " + val, "GMT"));
        }
        return "";
    }

    public Collection objectToCollection(Object val, boolean force)
    {
        Collection ret = null;
        if (val != null)
        {
            if (val.getClass().isArray())
            {
                ret = new ArrayList();
                int len = Array.getLength(val);
                for (int i = 0; i < len; i++)
                    ret.add(Array.get(val, i));
            }
            else if (val instanceof Collection)
            {
                ret = (Collection)val;
            }
            else if (force)
            {
                ret = new ArrayList();
                ret.add(val);
            }
        }

        return ret;
    }

    public <T> T mapToObject(Class<T> clazz, Map values)
        throws CtxException
    {
        CreatorFromMap create = new CreatorFromMap(values);
        ClassTraversal traverse = new ClassTraversal(clazz, create);
        Object ret = traverse.traverse();
        return clazz.cast(ret);
    }

    public Map objectToMap(Object val)
        throws CtxException
    {
        MapFromObject map = new MapFromObject();
        ObjectTraversal traverse = new ObjectTraversal(map, val, false, null);
        traverse.traverse();
        return map.createdMap();
    }

    public <T extends VerifiableObject> T mapToVerifiedObject(Class<T> clazz, Map values)
        throws CtxException
    {
        VerifiableObject o = mapToObject(clazz, values);
        if ((o != null) && (!o.verify()))
            except().te(o, "Object cannot be verified.");
        return clazz.cast(o);
    }

    public void writeObject(Object obj, OutputStream ostr, translator t)
        throws CtxException
    {
        t.translatorFor().translate(obj, ostr);
    }

    public <T> T readObject(InputStream istr, Class<T> cls, translator t)
        throws CtxException
    {
        return t.translatorFor().translate(istr, cls);
    }

    public <T extends VerifiableObject> T readVerifiedObject(InputStream istr, Class<T> cls, translator t)
        throws CtxException
    {
        VerifiableObject o = readObject(istr, cls, t);
        if (!o.verify())
            except().te(o, "Object cannot be verified");
        return cls.cast(o);
    }
}

