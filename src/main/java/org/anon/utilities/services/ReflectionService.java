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
 * File:                org.anon.utilities.services.ReflectionService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * Utilities related to reflection methods
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import sun.reflect.ReflectionFactory;
import java.lang.reflect.Constructor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;


import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class ReflectionService extends ServiceLocator.Service
{
    ReflectionService()
    {
        super();
    }

    public Annotation getAnnotation(Class objClazz, Class annoncls)
    {
        Annotation annon = objClazz.getAnnotation(annoncls);
        if ((annon == null) && (objClazz.getSuperclass() != null))
        {
            annon = getAnnotation(objClazz.getSuperclass(), annoncls);
        }
        return annon;
    }

    public boolean containsAnnotation(Class objClazz, String name)
    {
        boolean ret = false;
        Annotation[] annotations = objClazz.getAnnotations();
        for (int i = 0; i < annotations.length; i++)
        {
            if (annotations[i].annotationType().getName().equals(name))
            {
                ret = true;
                break;
            }
        }

        if ((!ret) && (objClazz.getSuperclass() != null))
        {
            ret = containsAnnotation(objClazz.getSuperclass(), name);
        }

        return ret;
    }

    public Annotation getAnyAnnotation(Class objClazz, String name)
    {
        Annotation ret = null;
        Annotation[] annotations = objClazz.getAnnotations();
        for (int i = 0; i < annotations.length; i++)
        {
            if (annotations[i].annotationType().getName().equals(name))
            {
                ret = annotations[i];
                break;
            }
        }

        if ((ret == null) && (objClazz.getSuperclass() != null))
        {
            ret = getAnyAnnotation(objClazz.getSuperclass(), name);
        }

        return ret;
    }

    public Field getAnyField(Class cls, String attr)
        throws CtxException
    {
        Field fld = null;
        try
        {
            fld = cls.getDeclaredField(attr);
        }
        catch (NoSuchFieldException ne)
        {
            if (cls.getSuperclass() != null)
                fld = getAnyField(cls.getSuperclass(), attr);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Field: " + attr + ":" + cls.getName(), e.getMessage()));
        }
        return fld;
    }

    public Object getAnyFieldValue(Class cls, Object obj, String attr)
        throws CtxException
    {
        Field fld = getAnyField(cls, attr);
        assertion().assertNotNull(fld, "Cannot find field: " + attr + " in " + cls.getName());
        try
        {
            fld.setAccessible(true);
            return fld.get(obj);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Field: " + attr + ":" + cls.getName() + ":", e.getMessage()));
        }

        return null;
    }

    public void setAnyFieldValue(Class cls, Object obj, String attr, Object val)
        throws CtxException
    {
        Field fld = getAnyField(cls, attr);
        assertion().assertNotNull(fld, "Cannot find field: " + attr + " in " + cls.getName());
        try
        {
            fld.setAccessible(true);
            fld.set(obj, val);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Field: " + attr + ":" + cls.getName() + ":", e.getMessage()));
        }
    }

    public Field getAnnotatedField(Class cls, String anonCls)
        throws CtxException
    {
        Field fld = null;
        try
        {
            Field[] flds = cls.getDeclaredFields();
            for (Field f : flds)
            {
                Annotation[] annotates = f.getAnnotations();
                for (int i = 0; i < annotates.length; i++)
                {
                    if (annotates[i].annotationType().getName().equals(anonCls))
                    {
                       fld = f;
                    }
                }
            }

            if ((fld == null) && (cls.getSuperclass() != null))
                fld = getAnnotatedField(cls.getSuperclass(), anonCls);

            if(fld != null)
                fld.setAccessible(true);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Annotate: " + anonCls + ":" + cls.getName(), e.getMessage()));
        }
        return fld;
    }

    public Field getAnnotatedField(Class cls, Class annotatein)
        throws CtxException
    {
        Field fld = null;
        try
        {
            Class annotate = annotatein;
            if (!annotatein.getClassLoader().equals(cls.getClassLoader()))
            {
                annotate = cls.getClassLoader().loadClass(annotatein.getName());
            }
            Field[] flds = cls.getDeclaredFields();
            for (int i = 0; i < flds.length; i++)
            {
                if (flds[i].isAnnotationPresent(annotate))
                {
                    fld = flds[i];
                    break;
                }
            }

            if ((fld == null) && (cls.getSuperclass() != null))
                fld = getAnnotatedField(cls.getSuperclass(), annotate);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("AnnotateField: " + annotatein.getName() + ":" + cls.getName(), e.getMessage()));
        }
        return fld;
    }

    public Field[] getAnnotatedFields(Class cls, Class annotatein)
        throws CtxException
    {
        List<Field> ret = new ArrayList<Field>();
        try
        {
            Class annotate = annotatein;
            if (!annotatein.getClassLoader().equals(cls.getClassLoader()))
            {
                annotate = cls.getClassLoader().loadClass(annotatein.getName());
            }
            Field[] flds = cls.getDeclaredFields();
            for (int i = 0; i < flds.length; i++)
            {
                if (flds[i].isAnnotationPresent(annotate))
                {
                    ret.add(flds[i]);
                }
            }

            if (cls.getSuperclass() != null)
            {
                Field[] add = getAnnotatedFields(cls.getSuperclass(), annotate);
                for (int i = 0; i < add.length; i++)
                    ret.add(add[i]);
            }
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("AnnotatedFields: " + annotatein.getName() + ":" + cls.getName(), e.getMessage()));
        }
        return ret.toArray(new Field[0]);
    }

    public Method getAnyMethod(Class cls, String mthd, Class ... params)
        throws CtxException
    {
        Method ret = null;
        try
        {
            ret = cls.getDeclaredMethod(mthd, params);
        }
        catch (NoSuchMethodException e)
        {
            if (cls.getSuperclass() != null)
                ret = getAnyMethod(cls.getSuperclass(), mthd, params);
        }
        catch (Exception e1)
        {
            except().rt(e1, new CtxException.Context("Method: " + mthd + ":" + cls.getName(), e1.getMessage()));
        }

        return ret;
    }

    public <T> T silentcreate(Class<T> clazz)
        throws CtxException
    {
        return silentcreate(clazz, Object.class);
    }

    public <T> T silentcreate(Class<T> clazz, Class<? super T> parent) 
        throws CtxException
    {
        try 
        {
            ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
            Constructor objDef = parent.getDeclaredConstructor();
            Constructor intConstr = rf.newConstructorForSerialization( clazz, objDef);
            return clazz.cast(intConstr.newInstance());
        } 
        catch (Exception e) 
        {
            except().rt(e, new CtxException.Context("silentcreate: " + clazz, e.getMessage()));
        }

        return null;
    }

}

