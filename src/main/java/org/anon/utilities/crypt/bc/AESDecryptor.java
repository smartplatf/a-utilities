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
 * File:                org.anon.utilities.crypt.bc.AESDecryptor
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-06-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A decryptor that uses AES decoding
 *
 * ************************************************************
 * */

package org.anon.utilities.crypt.bc;

import java.io.InputStream;
import java.io.OutputStream;

import org.anon.utilities.crypt.Decryptor;
import org.anon.utilities.crypt.ModeProcessor;
import org.anon.utilities.exception.CtxException;

public class AESDecryptor implements Decryptor
{
    private ModeProcessor _processor;

    public AESDecryptor(AESMode mode, byte[] key)
        throws CtxException
    {
        _processor = mode.processor();
        _processor.initDecrypt(key);
    }

    public void decrypt(InputStream in, OutputStream out)
        throws CtxException
    {
        _processor.process(in, out);
    }
}

