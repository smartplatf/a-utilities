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
 * File:                org.anon.utilities.test.gconcurrent.TestExecuteGraph
 * Author:              rsankar
 * Revision:            1.0
 * Date:                18-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test execution for graph
 *
 * ************************************************************
 * */

package org.anon.utilities.test.gconcurrent;

import java.util.Map;
import java.util.HashMap;

import org.anon.utilities.gconcurrent.GraphRuntimeNode;
import org.anon.utilities.gconcurrent.execute.ExecuteGraph;
import org.anon.utilities.gconcurrent.execute.ExecuteGraphNode;
import org.anon.utilities.gconcurrent.execute.ExecuteGraphParms;
import org.anon.utilities.gconcurrent.execute.ProbeParms;
import org.anon.utilities.cthreads.CtxRunnable;
import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;
import org.anon.utilities.cthreads.CThreadContext;
import org.anon.utilities.cthreads.RuntimeContext;
import org.anon.utilities.exception.CtxException;

public class TestExecuteGraph extends ExecuteGraph implements CtxRunnable
{
    public TestExecuteGraph(GraphRuntimeNode nde, ProbeParms parms)
        throws CtxException
    {
        super(nde, parms);
    }

    protected ExecuteGraphNode graphNodeExecutor(GraphRuntimeNode nde, ProbeParms parms)
        throws CtxException
    {
        return new TestExecuteGraphNode(nde, parms);
    }

    public void recordException(Throwable e)
        throws CtxException
    {
        System.out.println("There has been an exception");
        e.printStackTrace();
    }

    public Repeatable repeatMe(RepeaterVariants vars)
        throws CtxException
    {
        ExecuteGraphParms p = (ExecuteGraphParms)vars;
        return new TestExecuteGraph(p.runtimeNode(), p.parms());
    }
    
    public RuntimeContext startRuntimeContext(String action)
        throws CtxException
    {
        return null;
    }

    public CThreadContext runContext()
        throws CtxException
    {
        return (CThreadContext)_graphContext;
    }

    public Map<String, Object> contextLocals()
        throws CtxException
    {
        return new HashMap<String, Object>();
    }

    public CtxRunnable endWith()
        throws CtxException
    {
        System.out.println("Ended the context runnable");
        return new TestEndGraph((CThreadContext)_graphContext, this);
    }
}

