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
 * File:                org.anon.utilities.loader.RelatedLoader
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A loader related to an object
 *
 * ************************************************************
 * */

package org.anon.utilities.loader;

import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.security.ProtectionDomain;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class RelatedLoader extends URLClassLoader implements ResourceFinder, ResourceMod
{
    protected Object _relatedTo;
    protected String _name;
    protected String[] _initComps;
    protected List<ResourceMod> _modifiers;
    protected List<ResourceFinder> _resources;

    private static List<String> _forceSuperLoad = new ArrayList<String>();

    static
    {
        addForceLoadSuper("java.*");
        addForceLoadSuper("javax.*");
        addForceLoadSuper("sun.*");
        addForceLoadSuper("org.apache.log4j.*");
        addForceLoadSuper("org.anon.utilities.exception.*");
        addForceLoadSuper("org.anon.utilities.logger.*");
        addForceLoadSuper("org.anon.utilities.services.*");
        addForceLoadSuper("org.anon.utilities.perfstat.*");
        addForceLoadSuper("org.anon.utilities.loader.RelatedLoader");
    }

    class foundClass
    {
        byte[] _bytes;
        InputStream _stream;
        URL _url;
    }

    public RelatedLoader(URL[] urls, String[] comps)
        throws CtxException
    {
        this(urls, "unreferenced:ClassLoader", comps);
    }

    public RelatedLoader(URL[] urls, String name, String[] comps)
        throws CtxException
    {
        super(urls);
        _name = name;
        _modifiers = new ArrayList<ResourceMod>();
        //addResourceMod(this);
        _resources = new ArrayList<ResourceFinder>();
        addResourceFinder(this);
        initialize(comps);
    }

    public void initialize(String[] comps)
        throws CtxException
    {
        _initComps = comps;
        try
        {
            Class cls = this.loadClass("org.anon.utilities.anatomy.AModule");
            Object parent = null;
            for (int i = 0;  (comps != null) && (i < comps.length); i++)
            {
                Class comp = this.loadClass(comps[i]); //full path with package and all.
                parent = comp.getConstructor(cls).newInstance(parent);
            }
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Error in initialization of comps", "Exception"));
        }
    }

    public void setRelatedTo(Object obj)
    {
        _relatedTo = obj;
    }

    public Object relatedTo()
    {
        return _relatedTo;
    }

    protected static void addForceLoadSuper(String regexp)
    {
        _forceSuperLoad.add(regexp);
    }

    protected void addResourceFinder(ResourceFinder rfinder)
    {
        _resources.add(rfinder);
    }

    protected void addResourceMod(ResourceMod mod)
    {
        _modifiers.add(mod);
    }

    protected void addResourceMods(List<ResourceMod> mods)
    {
        _modifiers.addAll(mods);
    }

    public void addJar(String path) 
    {
        try
        {
            URL url = new URL("jar:file://"+path+"!/");
            addURL(url);
        }
        catch(MalformedURLException e) 
        {
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        Class<?> cls = findLoadedClass(name);
        if (cls != null)
            return cls;

        if (forceSuperLoad(name))
            return super.loadClass(name, resolve);

        try
        {
            cls = this.findClass(name);
            if ((cls != null) && (resolve))
            {
                resolveClass(cls);
                return cls;
            }
        }
        catch (ClassNotFoundException e)
        {
            cls = super.loadClass(name, resolve);
        }

        if (cls == null)
        {
            cls = super.loadClass(name, resolve);
        }

        return cls;
    }

    private boolean forceSuperLoad(String cls)
    {
        for (String chk : _forceSuperLoad)
        {
            if (chk.indexOf("*") >= 0)
            {
                String comparepkg = chk.substring(0, chk.indexOf("*"));
                if (cls.startsWith(comparepkg))
                    return true;
            }
            else if (cls.equals(chk))
                return true;
        }

        return false;
    }

    protected ProtectionDomain domainFor(URL url) 
        throws CtxException 
    { 
        return null; 
    }

    protected void initializeDomain(ProtectionDomain domain, Class cls) 
        throws CtxException 
    { 
    }

    public Class findClass(String className) 
        throws ClassNotFoundException 
    {
        Class ret = null;

        try
        {
            foundClass found = getClassBytes(className);
            byte[] classBytes = found._bytes;
            if (classBytes != null)
            {
                URL url = found._url;
                ProtectionDomain domain = domainFor(url);
                ret = defineClass(className, classBytes, 0, classBytes.length, domain);
                if ((domain != null) && (ret != null))
                    initializeDomain(domain, ret);

                if(ret.getPackage() == null)
                {
                    String packageName = className.substring(0, className.lastIndexOf('.'));
                    definePackage(packageName, null, null, null, null, null, null, null);
                }
            }
            else
            {
                super.findClass(className);
            }
        }
        catch(ClassFormatError cfe)
        {
            cfe.printStackTrace();
            throw new ClassNotFoundException("Class Not Found", cfe);
        }
        catch (CtxException e) 
        {
            e.printStackTrace();
            throw new ClassNotFoundException(className, e);
        }

        return ret;
    }

    public boolean modifies(String className, ClassLoader ldr)
    {
        return true;
    }

    public byte[] modify(InputStream stream, String cls, ClassLoader ldr)
        throws CtxException
    {
        return io().readBytes(stream);
    }

    protected foundClass getClassBytes(String className)
        throws CtxException
    {
        foundClass found = classFileStream(className);
        if (found != null)
        {
            InputStream istr = found._stream;
            for (int i = 0; (istr != null) && (i < _modifiers.size()); i++)
            {
                ResourceMod mod = _modifiers.get(i);
                if (mod.modifies(className, this))
                {
                    if (found._bytes != null)
                        istr = new ByteArrayInputStream(found._bytes);
                    found._bytes = mod.modify(istr, className, this);
                }
            }

            if ((found._bytes == null) && (found._stream != null))
                found._bytes = this.modify(found._stream, className, this);
        }

        return found;
    }

    protected String classFilePath(String className)
    {
        //default implementation is just the path as is
        String path = className.replaceAll("\\.", "/");
        return path + ".class";
    }

    public URL resourcePath(String name)
        throws CtxException
    {
        return super.findResource(name);
    }

    public InputStream resourceStream(String path)
        throws CtxException
    {
        return super.getResourceAsStream(path);
    }

    protected foundClass classFileStream(String className) 
    {
        foundClass ret = null;
        InputStream io = null;
        URL url = null;
        String path = classFilePath(className);
        try
        {
            ResourceFinder finder = null;
            boolean found = false;
            for (int i = 0; (!found) && (i < _resources.size()); i++)
            {
                finder = _resources.get(i);
                url = finder.resourcePath(className);
                if (url != null)
                    found = true;
            }

            if (finder != null)
                io = finder.resourceStream(path);

            if (io == null)
            {
                System.out.println("CANNOT FIND RESOURCE STREAM FOR: " + className);
            }
            else
            {
                ret = new foundClass();
                ret._url = url;
                ret._stream = io;
            }
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }

        return ret;
    }

    public String toString()
    {
        return _name;
    }
}

