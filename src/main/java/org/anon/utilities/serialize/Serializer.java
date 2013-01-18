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
 * File:                org.anon.utilities.serialize.Serializer
 * Author:              rsankar
 * Revision:            1.0
 * Date:                04-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A serializer abstraction for all serialization
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.anon.utilities.exception.CtxException;

public interface Serializer
{
    public void serialize(Object obj, OutputStream str)
        throws IOException;
    public Object deserialize(InputStream istr, LoaderResolver resolve)
        throws IOException;
    public void close()
        throws IOException;
}

