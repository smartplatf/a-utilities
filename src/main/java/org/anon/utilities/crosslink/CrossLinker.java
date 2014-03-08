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
 * File:                org.anon.utilities.crosslink.CrossLinker
 * Author:              rsankar
 * Revision:            1.0
 * Date:                03-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A linker that allows to link across classloaders
 *
 * ************************************************************
 * */

package org.anon.utilities.crosslink;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.anon.utilities.exception.CtxException;
import static org.anon.utilities.services.ServiceLocator.*;

public abstract class CrossLinker 
{
    private Object _link;           //linked object in a different classloader
    private Class _clazz;           //class object of the link
    private String _name;           //name of the object
    private ClassLoader _loader;    //loader this object

    protected CrossLinker(Object link)
    {
        _link = link;
        if (_link != null)
        {
            _clazz = _link.getClass();
            _loader = _clazz.getClassLoader();
            _name = _clazz.getName();
        }
    }

    protected CrossLinker(ClassLoader loader)
        throws CtxException
    {
        //compute name as the current class name without the CrossLink
        String clsname = getClassName();
        init(loader, clsname);
    }

    CrossLinker(String name)
        throws CtxException
    {
        ClassLoader ldr = this.getClass().getClassLoader();
        init(ldr, name);
    }

    CrossLinker(ClassLoader loader, String name)
        throws CtxException
    {
        //compute name as the current class name without the CrossLink
        init(loader, name);
    }

    private String getClassName()
    {
        Package pkgcls = this.getClass().getPackage();
        String pkg = "";
        if (pkgcls != null)
        {
            pkg = pkgcls.getName();
        }
        else
        {
            pkg = this.getClass().getName();
            pkg = pkg.substring(0, pkg.lastIndexOf("."));
        }
        String clsname = this.getClass().getSimpleName();
        clsname = clsname.substring("CrossLink".length());
        clsname = pkg + "." + clsname;

        return clsname;
    }

    public boolean isAssignable()
        throws CtxException
    {
        try
        {
            String clsname = getClassName();
            Class cls = _loader.loadClass(clsname);
            return cls.isAssignableFrom(_clazz);
        }
        catch (Exception e)
        {
            except().rt(this, e, new CtxException.Context("isAssignable", e.getMessage()));
        }

        return false;
    }

    protected void init(ClassLoader loader, String name)
        throws CtxException
    {
        try
        {
            _name = name;
            _loader = loader;
            _clazz = _loader.loadClass(_name);
        }
        catch (Exception e)
        {
            except().rt(this, e, new CtxException.Context("init", e.getMessage()));
        }
    }

    protected Class[] parmTypes(String method, Object ... params)
    {
        Class[] p = new Class[0];
        if (params != null)
        {
            p = new Class[params.length];
            for (int i = 0; i < params.length; i++)
                p[i] = params[i].getClass();
        }

        return p;
    }

    protected Object linkMethod(String method, Object ... params)
        throws CtxException
    {
        try
        {
            Object ret = null;
            Class[] p = parmTypes(method, params);
            Method mthd = reflect().getAnyMethod(_clazz, method, p);
            if (mthd != null)
            {
                mthd.setAccessible(true);
                ret = mthd.invoke(_link, params);
            }
            else
                except().te("Not a valid method " + method + ":" + _clazz + ":" + p, new CtxException.Context("linkMethod", "Method:" + method));

            return ret;
        }
        catch (InvocationTargetException ie)
        {
            String msg = ie.getMessage();
            Throwable e = ie;
            if (ie.getCause() != null)
            {
                msg = ie.getCause().getMessage();
                e = ie.getCause();
            }
            except().rt(this, e, new CtxException.Context("Error: ", msg));

        }
        catch (Exception e)
        {
            except().rt(this, e, new CtxException.Context("linkMethod", e.getMessage()));
        }
        return null;
    }

    public Object link() { return _link; }

    public Class linkType() { return _clazz; }

    public Object create(Object ... params)
        throws CtxException
    {
        try
        {
            Class[] p = parmTypes("<init>", params);
            Constructor cons = _clazz.getDeclaredConstructor(p);
            cons.setAccessible(true);
            _link = cons.newInstance(params);
            return _link;
        }
        catch (Exception e)
        {
            except().rt(this, e, new CtxException.Context("create", e.getMessage()));
        }
        return null;
    }
}

