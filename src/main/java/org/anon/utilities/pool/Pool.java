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
 * File:                org.anon.utilities.pool.Pool
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * Pool
 *
 * ************************************************************
 * */

package org.anon.utilities.pool;

import org.anon.utilities.exception.CtxException;

public interface Pool
{
    public static final int INCREASE_BY = 5;
    public static final int MAX = 20;
    public static final int MIN = 10;
    public static final int RETRY_LIMIT = 10;
    public static final int RETRY_BEFORE_RECREATE = 50;

    public int free();                  
    public int size();

    public PoolEntity lockone()
        throws CtxException;

    public PoolEntity lockone(boolean increase)
        throws CtxException;

    public void unlockone(Object object)
        throws CtxException;

    public void adjust()
        throws CtxException;
}

