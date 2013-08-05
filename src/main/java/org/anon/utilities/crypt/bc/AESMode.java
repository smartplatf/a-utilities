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
 * File:                org.anon.utilities.crypt.bc.AESMode
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-06-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * modes of encoding
 *
 * ************************************************************
 * */

package org.anon.utilities.crypt.bc;

import org.anon.utilities.crypt.ModeProcessor;
import org.anon.utilities.exception.CtxException;

public enum AESMode
{
    cbc(new CBCProcessor());

    private ModeProcessor _processor;

    private AESMode(ModeProcessor proc)
    {
        _processor = proc;
    }

    public ModeProcessor processor()
        throws CtxException
    {
        return (ModeProcessor)_processor.repeatMe(null);
    }
}

