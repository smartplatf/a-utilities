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
 * File:                org.anon.utilities.gconcurrent.execute.AbstractGraphContext
 * Author:              rsankar
 * Revision:            1.0
 * Date:                17-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An abstract context from which to derive
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent.execute;

import java.util.UUID;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.gconcurrent.Graph;
import org.anon.utilities.gconcurrent.GraphRuntime;
import org.anon.utilities.gconcurrent.GraphRuntimeNode;
import org.anon.utilities.atomic.AtomicCounter;
import org.anon.utilities.exception.CtxException;

public abstract class AbstractGraphContext implements GraphContext
{
    private UUID _executionID;
    private int _totalNodes;
    private AtomicCounter _createdCount;
    private AtomicCounter _finishedCount;
    private AtomicCounter _nodesDone;
    private Graph _graph;
    private ExecutorService _service;

    public AbstractGraphContext(Graph graph, ExecutorService service)
        throws CtxException
    {
        _graph = graph;
        _executionID = UUID.randomUUID();
        _service = service;
        _createdCount = anatomy().jvmEnv().createAtomicCounter(_executionID.toString() + "-Created", 0);
        _finishedCount = anatomy().jvmEnv().createAtomicCounter(_executionID.toString() + "-Finished", 0);
        _nodesDone = anatomy().jvmEnv().createAtomicCounter(_executionID.toString() + "-NodesDone", 0);
        _totalNodes = _graph.graphSize();
    }

    public int createdTask() 
        throws CtxException
    { 
        return _createdCount.incrementAndGet(); 
    }

    public int nodeDone() 
        throws CtxException
    { 
        return _nodesDone.incrementAndGet(); 
    }

    public boolean graphDone()
        throws CtxException
    {
        int finished = _finishedCount.incrementAndGet();
        boolean ret = (_createdCount.get() == finished);
        ret = ret && (_nodesDone.get() == _totalNodes);
        return ret;
    }

    public void schedule(Runnable run)
        throws CtxException
    {
        try
        {
            _service.execute(run);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Error scheduling task", "Exception"));
        }
    }

    protected abstract ExecuteGraph executorFor(GraphRuntimeNode rtnde, ProbeParms parms)
        throws CtxException;

    protected abstract ProbeParms myParms()
        throws CtxException;

    public void executeGraph()
        throws CtxException
    {
        GraphRuntime rt = _graph.newRuntime();
        List<GraphRuntimeNode> ln = rt.level1Nodes();
        ProbeParms parms = myParms();
        for (int i = 0; (ln != null) && (i < ln.size()); i++)
        {
            ExecuteGraph g = executorFor(ln.get(i), parms);
            schedule(g);
        }
    }
}

