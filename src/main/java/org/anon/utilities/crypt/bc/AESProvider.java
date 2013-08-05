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
 * File:                org.anon.utilities.crypt.bc.AESProvider
 * Author:              rsankar
 * Revision:            1.0
 * Date:                31-05-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A provider for aes implementation
 *
 * ************************************************************
 * */

package org.anon.utilities.crypt.bc;

import java.security.Security;

import org.anon.utilities.crypt.Cryptor;
import org.anon.utilities.crypt.Encryptor;
import org.anon.utilities.crypt.Decryptor;
import org.anon.utilities.crypt.KeyGenerator;
import org.anon.utilities.exception.CtxException;

public class AESProvider implements Cryptor
{
    private KeyGenerator _kGenerator;
    private AESMode _mode;

    public AESProvider(AESMode mode, KeyGenerator generator)
    {
        _mode = mode;
        _kGenerator = generator;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public Encryptor encryptor(String password)
        throws CtxException
    {
        byte[] key = _kGenerator.keyFor(password);
        return new AESEncryptor(_mode, key);
    }

    public Decryptor decryptor(String password)
        throws CtxException
    {
        byte[] key = _kGenerator.keyFor(password);
        return new AESDecryptor(_mode, key);
    }

    public static AESProvider getInstance(KeyGenerator generator)
    {
        return new AESProvider(AESMode.cbc, generator);
    }
}

