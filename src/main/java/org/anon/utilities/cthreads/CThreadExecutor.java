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
 * File:                org.anon.utilities.cthreads.CThreadExecutor
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An executor for context threads
 *
 * ************************************************************
 * */

package org.anon.utilities.cthreads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.logger.Logger;

public class CThreadExecutor extends ThreadPoolExecutor
{
    private transient Logger _logger;

    public CThreadExecutor(ThreadFactory fact)
    {
        super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), fact);
        _logger = logger().rlog(this);
    }

    protected void beforeExecute(Thread t, Runnable r)
    {
        super.beforeExecute(t, r);
        try
        {
            CThread ct = (CThread)t;
            if ((r != null) && (r instanceof CtxRunnable))
            {
                CtxRunnable crun = (CtxRunnable)r;
                ct.setContext(crun.runContext());
                ct.setContextLocals(crun.contextLocals());
                ct.startRuntime("execute", crun);
                ct.reflectCtx();
            }
        }
        catch (Exception e)
        {
            _logger.fatal("Error in beforeExecution", e);
        }
    }

    protected void afterExecute(Runnable r, Throwable t)
    {
        super.afterExecute(r, t);
        if ((r != null) && (r instanceof CtxRunnable))
        {
            try
            {
                CtxRunnable crun = (CtxRunnable)r;
                if (t != null)
                    crun.recordException(t);

                if (crun.hasCompleted())
                {
                    CtxRunnable end = crun.endWith();
                    if (end != null) end.run();
                }

                CThread ct = (CThread)Thread.currentThread();
                ct.resetCtx();
            }
            catch (Exception e)
            {
                _logger.fatal("Error in ending execution", e);
            }
        }
    }
}

