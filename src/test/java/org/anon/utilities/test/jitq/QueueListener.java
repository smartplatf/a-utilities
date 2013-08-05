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
 * File:                org.anon.utilities.test.jitq.QueueListener
 * Author:              rsankar
 * Revision:            1.0
 * Date:                15-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A simple listener for jitqs
 *
 * ************************************************************
 * */

package org.anon.utilities.test.jitq;

import java.util.Map;
import java.util.UUID;
import java.util.HashMap;


import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.cthreads.RuntimeContext;
import org.anon.utilities.jitq.JITProcessQueue;
import org.anon.utilities.jitq.DataListener;
import org.anon.utilities.exception.CtxException;

public class QueueListener implements DataListener
{
    private JITProcessQueue _queue;

    public QueueListener()
    {
    }

    void setQueue(JITProcessQueue q) { _queue = q; }

    public void onMessage(Object data)
        throws CtxException
    {
        System.out.println(data);
        jitq().doneProcessingMessage(_queue);
    }


    public RuntimeContext startRuntimeContext(String action, JITProcessQueue queue)
        throws CtxException
    {
        return null;
    }

    public UUID getID(Object associated)
    {
        TestData d = (TestData)associated;
        return d.id();
    }

    public String getName(Object associated)
    {
        TestData d = (TestData)associated;
        return d.name();
    }

    public Map<String, Object> locals(Object associated, Object data)
        throws CtxException
    {
        return new HashMap<String, Object>();
    }
}

