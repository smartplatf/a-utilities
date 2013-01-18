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
 * File:                org.anon.utilities.gconcurrent.Gateway
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A gateway that allows or denies execution of this runtime node
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.atomic.AtomicCounter;
import org.anon.utilities.exception.CtxException;

public class Gateway implements java.io.Serializable
{
    private AtomicCounter _blocks;

    public Gateway(String name, int blocks)
        throws CtxException
    {
        String rel = loader().relatedName();
        _blocks = anatomy().jvmEnv().createAtomicCounter(rel + "-" + name, blocks);
    }

    public boolean releaseBlock()
        throws CtxException
    {
        int blocks = _blocks.decrementAndGet();
        return (blocks <= 0); //true if not blocked, else false
    }

    public boolean isBlocked()
        throws CtxException
    {
        int blocks = _blocks.get();
        return (blocks > 0);
    }
}

