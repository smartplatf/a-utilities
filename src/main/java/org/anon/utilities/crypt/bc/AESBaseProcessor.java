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
 * File:                org.anon.utilities.crypt.bc.AESBaseProcessor
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-06-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A base for all types of processors
 *
 * ************************************************************
 * */

package org.anon.utilities.crypt.bc;

import java.io.InputStream;
import java.io.OutputStream;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.crypt.ModeProcessor;
import org.anon.utilities.exception.CtxException;

import static org.anon.utilities.services.ServiceLocator.*;

public abstract class AESBaseProcessor implements ModeProcessor, Repeatable
{
    private PaddedBufferedBlockCipher _cipher;
    private byte[] _key;
    private byte[] _iv;
    private boolean _isEncrypt = true;

    public AESBaseProcessor()
    {
    }

    protected abstract PaddedBufferedBlockCipher create();

    protected CipherParameters parameters()
    {
        KeyParameter parm = new KeyParameter(_key);
        CipherParameters ret = parm;
        if (_iv != null)
            ret = new ParametersWithIV(parm, _iv);

        return ret;
    }

    protected void init(byte[] key, byte[] iv)
    {
        _key = new byte[key.length];
        System.arraycopy(key, 0, _key, 0, key.length);
        if (_iv != null)
        {
            _iv = new byte[iv.length];
            System.arraycopy(iv, 0, _iv, 0, iv.length);
        }
        _cipher = create();
        _cipher.init(_isEncrypt, parameters());
    }

    public void initEncrypt(byte[] key)
    {
        initEncrypt(key, null);
    }

    public void initEncrypt(byte[] key, byte[] iv)
    {
        _isEncrypt = true;
        init(key, iv);
    }

    public void initDecrypt(byte[] key)
    {
        initDecrypt(key, null);
    }

    public void initDecrypt(byte[] key, byte[] iv)
    {
        _isEncrypt = false;
        init(key, iv);
    }

    public void process(InputStream in, OutputStream out)
        throws CtxException
    {
        try 
        {
            int cnt = 0;
            int proc = 0;

            byte[] buf = new byte[16];
            byte[] obuf = new byte[512];

            while ((cnt = in.read(buf)) >= 0) 
            {
                proc = _cipher.processBytes(buf, 0, cnt, obuf, 0);
                out.write(obuf, 0, proc);
            }

            proc = _cipher.doFinal(obuf, 0);
            out.write(obuf, 0, proc);
            out.flush();
        }
        catch (Exception e) 
        {
            except().rt(e, new CtxException.Context("Error", e.getMessage()));
        }
    }
}

