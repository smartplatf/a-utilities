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
 * File:                org.anon.utilities.services.IOService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service that provides io functions
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class IOService extends ServiceLocator.Service
{
    public IOService()
    {
        super();
    }

    public StringBuffer readStream(InputStream stream)
        throws CtxException
    {
        try
        {
            StringBuffer buff = new StringBuffer();
            byte[] bytes = new byte[1024];
            int read = stream.read(bytes, 0, bytes.length);
            while (read > 0)
            {
                String str = new String(bytes, 0, read);
                buff.append(str);
                read = stream.read(bytes, 0, bytes.length);
            }

            return buff;
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("readStream", e.getMessage()));
        }

        return null;
    }


    public byte[] readBytes(InputStream stream)
        throws CtxException
    {
        try
        {
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int read = stream.read(bytes, 0, bytes.length);
            while (read > 0)
            {
                buff.write(bytes, 0, read);
                read = stream.read(bytes, 0, bytes.length);
            }

            return buff.toByteArray();
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("readBytes", e.getMessage()));
        }

        return null;
    }
}

