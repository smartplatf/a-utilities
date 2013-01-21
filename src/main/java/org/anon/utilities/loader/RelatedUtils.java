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
 * File:                org.anon.utilities.loader.RelatedUtils
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * a set of utilities to deal with related object
 *
 * ************************************************************
 * */

package org.anon.utilities.loader;

public class RelatedUtils
{
    private RelatedUtils()
    {
    }

    public static Object getRelatedObject(Object obj)
    {
        if (obj == null)
            return null;

        return getRelatedObjectForClass(obj.getClass());
    }

    public static Object getRelatedObjectForClass(Class cls)
    {
        if (cls == null)
            return null;

        Object ret = null;
        ClassLoader ldr = cls.getClassLoader();
        if (ldr instanceof RelatedLoader)
        {
            RelatedLoader rldr = (RelatedLoader)ldr;
            ret = rldr.relatedTo();
        }

        return ret;
    }

    public static CrossLinkRelatedObject getCLRelatedObject(Object obj)
    {
        Object related = getRelatedObject(obj);
        if (related != null)
            return new CrossLinkRelatedObject(obj);

        return null;
    }
}

