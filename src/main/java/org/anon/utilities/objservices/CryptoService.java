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
 * File:                org.anon.utilities.objservices.CryptoService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-06-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service that provides the encryption services
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.anon.utilities.crypt.Cryptor;
import org.anon.utilities.crypt.Encryptor;
import org.anon.utilities.crypt.Decryptor;
import org.anon.utilities.crypt.bc.AESProvider;
import org.anon.utilities.crypt.ROKeyGenerator;
import org.anon.utilities.exception.CtxException;

import static org.anon.utilities.services.ServiceLocator.*;

public class CryptoService extends ObjectServiceLocator.ObjectService
{
    private Cryptor _currentCryptor;

    public CryptoService()
    {
        super();
        _currentCryptor = AESProvider.getInstance(new ROKeyGenerator());
    }

    public byte[] encrypt(String password)
        throws CtxException
    {
        try
        {
            Encryptor encrypt = _currentCryptor.encryptor(password);
            InputStream in = new ByteArrayInputStream(password.getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            encrypt.encrypt(in, out);
            byte[] encrypted = out.toByteArray();
            out.close();
            in.close();
            return encrypted;
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot encrypt: " + password, e.getMessage()));
        }

        return null;
    }

    public String decrypt(byte[] bytes, String password)
        throws CtxException
    {
        try
        {
            Decryptor decrypt = _currentCryptor.decryptor(password);
            InputStream in = new ByteArrayInputStream(bytes);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            decrypt.decrypt(in, out);
            byte[] decrypted = out.toByteArray();
            out.close();
            in.close();
            return new String(decrypted);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot decrypt with: " + password, e.getMessage()));
        }

        return null;
    }
}

