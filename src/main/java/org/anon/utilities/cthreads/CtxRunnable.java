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
 * File:                org.anon.utilities.cthreads.CtxRunnable
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A runnable with context in it
 *
 * ************************************************************
 * */

package org.anon.utilities.cthreads;

import java.util.Map;

import org.anon.utilities.exception.CtxException;

public interface CtxRunnable extends Runnable
{
    public RuntimeContext startRuntimeContext(String action)
        throws CtxException;

    public CThreadContext runContext()
        throws CtxException;

    public Map<String, Object> contextLocals()
        throws CtxException;

    public boolean hasCompleted()
        throws CtxException;

    public void recordException(Throwable t)
        throws CtxException;

    public CtxRunnable endWith()
        throws CtxException;
}

