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
 * File:                org.anon.utilities.test.gconcurrent.TestEndGraph
 * Author:              rsankar
 * Revision:            1.0
 * Date:                19-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An end for graph
 *
 * ************************************************************
 * */

package org.anon.utilities.test.gconcurrent;

import java.util.Map;
import java.util.HashMap;

import org.anon.utilities.gconcurrent.execute.GraphContext;
import org.anon.utilities.cthreads.CThreadContext;
import org.anon.utilities.cthreads.CtxRunnable;
import org.anon.utilities.cthreads.RuntimeContext;
import org.anon.utilities.exception.CtxException;

public class TestEndGraph implements CtxRunnable
{
    private CThreadContext _context;
    private CtxRunnable _parent;
    private boolean _hasCompleted;

    public TestEndGraph(CThreadContext ctx, CtxRunnable running)
    {
        _parent = running;
        _context = ctx;
        _hasCompleted = false;
    }

    public void recordException(Throwable e)
        throws CtxException
    {
        System.out.println("There has been an exception");
        e.printStackTrace();
    }

    public void run()
    {
        try
        {
            if ((_context instanceof GraphContext) && (_parent.hasCompleted()))
            {
                GraphContext gctx = (GraphContext)_context;
                if (gctx.graphDone())
                {
                    System.out.println("Graph execution is done. Should not execution anything after this.");
                }
            }
            _hasCompleted = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean hasCompleted()
        throws CtxException
    {
        return _hasCompleted;
    }

    public RuntimeContext startRuntimeContext(String action)
        throws CtxException
    {
        return null;
    }

    public CThreadContext runContext()
        throws CtxException
    {
        return (CThreadContext)_context;
    }

    public Map<String, Object> contextLocals()
        throws CtxException
    {
        return new HashMap<String, Object>();
    }

    public CtxRunnable endWith()
        throws CtxException
    {
        System.out.println("In the EndGraphRunnable Ended the context runnable");
        return null;
    }
}

