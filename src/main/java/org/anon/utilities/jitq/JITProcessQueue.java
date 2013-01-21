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
 * File:                org.anon.utilities.jitq.JITProcessQueue
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A queue where processing is only kicked off when data is present, if not it does not have a thread polling
 *
 * ************************************************************
 * */

package org.anon.utilities.jitq;

import org.anon.utilities.exception.CtxException;

public interface JITProcessQueue
{
    public boolean isProcessing()
        throws CtxException;

    public boolean setProcessing()
        throws CtxException;

    public void doneProcessing()
        throws CtxException;

    public DataListener listener();

    public Object associatedTo();

    public boolean add(Object data)
        throws CtxException;

    public Object poll();

    public boolean isEmpty();

    public String belongsTo();
}

