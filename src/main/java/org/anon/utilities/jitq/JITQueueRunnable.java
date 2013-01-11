/**
 * SMART - State Machine ARchiTecture
 *
 * Copyright (C) 2012 Individual contributors as indicated by
 * the @authors tag
 *
 * This file is a part of SMART.
 *
 * SMART is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SMART is distributed in the hope that it will be useful,
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
 * File:                org.anon.utilities.jitq.JITQueueRunnable
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A runnable that selects from the queue and runs the listener
 *
 * ************************************************************
 * */

package org.anon.utilities.jitq;

import java.util.UUID;
import java.util.Map;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.cthreads.CtxRunnable;
import org.anon.utilities.cthreads.CThreadContext;
import org.anon.utilities.cthreads.RuntimeContext;
import org.anon.utilities.exception.CtxException;
import org.anon.utilities.logger.Logger;

public class JITQueueRunnable implements CtxRunnable
{
    class JITQueueContext implements CThreadContext
    {
        public UUID id()
        {
            return _listener.getID(_associated);
        }

        public String extras()
        {
            return _listener.getName(_associated);
        }
    }

    private transient Logger _logger;
    private JITProcessQueue _queue;
    private Object _associated;
    private DataListener _listener;
    private JITQueueContext _ctx;
    private boolean _hasCompleted;

    public JITQueueRunnable(JITProcessQueue queue)
    {
        _queue = queue;
        _associated = queue.associatedTo();
        _listener = queue.listener();
        _ctx = new JITQueueContext();
        _hasCompleted = false;
        _logger = logger().rlog(this);
    }

    public RuntimeContext startRuntimeContext(String action)
        throws CtxException
    {
        return _listener.startRuntimeContext(action, _queue);
    }

    public CThreadContext runContext()
        throws CtxException
    {
        return _ctx;
    }

    public Map<String, Object> contextLocals()
        throws CtxException
    {
        return _listener.locals(_associated, null);
    }

    public boolean hasCompleted()
        throws CtxException
    {
        return _hasCompleted;
    }

    public void recordException(Throwable t)
        throws CtxException
    {
    }

    public CtxRunnable endWith()
        throws CtxException
    {
        return null;
    }

    private void doProcessing()
        throws Exception
    {
        Object data = _queue.poll();
        if (data != null)
        {
            _queue.setProcessing();
            Map<String, Object> locals = _listener.locals(_associated, data);
            if (locals != null)
                threads().setContextLocals(locals);
            _listener.onMessage(data);
        }
        else
        {
            _queue.doneProcessing();
        }
    }

    public void run()
    {
        try
        {
            doProcessing();
        }
        catch (Exception e)
        {
            _logger.fatal(e);
        }
    }
}

