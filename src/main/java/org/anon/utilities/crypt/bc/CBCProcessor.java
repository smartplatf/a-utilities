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
 * File:                org.anon.utilities.crypt.bc.CBCProcessor
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-06-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A processor that implements cbc mode
 *
 * ************************************************************
 * */

package org.anon.utilities.crypt.bc;

import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;
import org.anon.utilities.exception.CtxException;

public class CBCProcessor extends AESBaseProcessor
{
    public CBCProcessor()
    {
        super();
    }

    @Override
    protected PaddedBufferedBlockCipher create()
    {
        BlockCipherPadding pad = new PKCS7Padding();
        return new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), pad);
    }

    public Repeatable repeatMe(RepeaterVariants parms)
        throws CtxException
    {
        return new CBCProcessor();
    }
}

