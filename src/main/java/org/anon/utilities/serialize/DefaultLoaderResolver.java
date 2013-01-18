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
 * File:                org.anon.utilities.serialize.DefaultLoaderResolver
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A default loader resolver used for serialization
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize;

import java.io.IOException;

import org.anon.utilities.loader.RelatedObject;

public class DefaultLoaderResolver implements LoaderResolver
{
    private ClassLoader _loader;

    public DefaultLoaderResolver(Serializer ser)
    {
        if (ser != null)
            _loader = ser.getClass().getClassLoader();
        else
            _loader = this.getClass().getClassLoader();
    }

    public DefaultLoaderResolver(ClassLoader ldr)
    {
        _loader = ldr;
    }

    public DefaultLoaderResolver(RelatedObject obj)
    {
        _loader = obj.getRelatedLoader();
    }

    public DefaultLoaderResolver()
    {
        _loader = this.getClass().getClassLoader();
    }

    public ClassLoader defaultLoader()
        throws IOException
    {
        return _loader;
    }

    public ClassLoader resolveLoader(String name)
        throws IOException
    {
        //always resolve to the current loader
        return _loader;
    }
}

