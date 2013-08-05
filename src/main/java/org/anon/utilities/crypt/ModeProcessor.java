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
 * File:                org.anon.utilities.crypt.ModeProcessor
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-06-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A processor for a mode
 *
 * ************************************************************
 * */

package org.anon.utilities.crypt;

import java.io.InputStream;
import java.io.OutputStream;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.exception.CtxException;

public interface ModeProcessor extends Repeatable
{
    public void initEncrypt(byte[] key);
    public void initEncrypt(byte[] key, byte[] iv);
    public void initDecrypt(byte[] key);
    public void initDecrypt(byte[] key, byte[] iv);

    public void process(InputStream in, OutputStream out)
        throws CtxException;
}

