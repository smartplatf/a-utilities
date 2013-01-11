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
 * File:                org.anon.utilities.services.TypeService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service for types
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import org.apache.commons.lang.ClassUtils;

public class TypeService extends ServiceLocator.Service
{
    TypeService()
    {
        super();
    }


    private static final Set<Class> WRAPPER_TYPES = new HashSet(Arrays.asList(
                    Boolean.class, Character.class, Byte.class, Short.class, 
                    Integer.class, Long.class, Float.class, Double.class, Void.class, String.class, BigDecimal.class));
    public boolean checkPrimitive(Class clazz) 
    {
        return (WRAPPER_TYPES.contains(clazz) || clazz.isPrimitive());
    }

    private static final Map<Class, Class> WRAPPER_MAP = new HashMap<Class, Class>();
    private static final Map<String, Class> CLASS_MAP = new HashMap<String, Class>();
    static
    {
        WRAPPER_MAP.put(Boolean.TYPE, Boolean.class);
        WRAPPER_MAP.put(Character.TYPE, Character.class);
        WRAPPER_MAP.put(Byte.TYPE, Byte.class);
        WRAPPER_MAP.put(Short.TYPE, Short.class);
        WRAPPER_MAP.put(Integer.TYPE, Integer.class);
        WRAPPER_MAP.put(Long.TYPE, Long.class);
        WRAPPER_MAP.put(Float.TYPE, Float.class);
        WRAPPER_MAP.put(Double.TYPE, Double.class);

        CLASS_MAP.put(Boolean.TYPE.getName(), Boolean.TYPE);
        CLASS_MAP.put(Character.TYPE.getName(), Character.TYPE);
        CLASS_MAP.put(Byte.TYPE.getName(), Byte.TYPE);
        CLASS_MAP.put(Short.TYPE.getName(), Short.TYPE);
        CLASS_MAP.put(Integer.TYPE.getName(), Integer.TYPE);
        CLASS_MAP.put(Long.TYPE.getName(), Long.TYPE);
        CLASS_MAP.put(Float.TYPE.getName(), Float.TYPE);
        CLASS_MAP.put(Double.TYPE.getName(), Double.TYPE);
        CLASS_MAP.put(boolean[].class.getName(), boolean[].class);
        CLASS_MAP.put(char[].class.getName(), char[].class);
        CLASS_MAP.put(byte[].class.getName(), byte[].class);
        CLASS_MAP.put(short[].class.getName(), short[].class);
        CLASS_MAP.put(int[].class.getName(), int[].class);
        CLASS_MAP.put(long[].class.getName(), long[].class);
        CLASS_MAP.put(float[].class.getName(), float[].class);
        CLASS_MAP.put(double[].class.getName(), double[].class);
    }

    public boolean checkStandard(Class clazz)
    {
        return (clazz.getName().startsWith("java") ||
                clazz.getName().startsWith("sun")); //standard implementations
    }

    public boolean isAssignable(Class cls, Class tocls)
    {
        return ClassUtils.isAssignable(cls, tocls, true);
    }

    public String getPackage(Class cls)
    {
        Package pcls = cls.getPackage();
        String pkg = "";
        if (pcls != null)
        {
            pkg = pcls.getName();
        }
        else
        {
            pkg = cls.getName();
            pkg = pkg.substring(0, pkg.lastIndexOf("."));
        }

        return pkg;
    }

    public boolean isWrapperType(Class cls)
    {
        return WRAPPER_TYPES.contains(cls);
    }

    public Class wrapperFor(Class cls)
    {
        if (WRAPPER_MAP.containsKey(cls))
            return WRAPPER_MAP.get(cls);

        return cls;
    }

    public Class classForPrimitive(String name)
    {
        if (CLASS_MAP.containsKey(name))
            return CLASS_MAP.get(name);

        return null;
    }
}

