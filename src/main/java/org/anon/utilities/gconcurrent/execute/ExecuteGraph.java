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
 * File:                org.anon.utilities.gconcurrent.execute.ExecuteGraph
 * Author:              rsankar
 * Revision:            1.0
 * Date:                17-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A execution for the graph
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent.execute;

import java.util.List;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.gconcurrent.GraphRuntimeNode;
import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.exception.CtxException;

public abstract class ExecuteGraph implements Runnable, Repeatable
{
    protected GraphContext _graphContext;
    private GraphRuntimeNode _rtNode;
    private ProbeParms _parameters;
    private boolean _hasCompleted;

    protected ExecuteGraph(GraphRuntimeNode nde, ProbeParms parms)
        throws CtxException
    {
        _graphContext = parms.context();
        _graphContext.createdTask();
        _rtNode = nde;
        _hasCompleted = false;
        _parameters = parms;
    }

    public boolean hasCompleted() 
        throws CtxException 
    { 
        return _hasCompleted; 
    }

    protected abstract ExecuteGraphNode graphNodeExecutor(GraphRuntimeNode nde, ProbeParms parms)
        throws CtxException;

    public abstract void recordException(Throwable t)
        throws CtxException;

    public void run()
    {
        try
        {
            ExecuteGraphNode egn = graphNodeExecutor(_rtNode, _parameters);
            runNode(egn, _rtNode);
        }
        catch(Exception e)
        {
            try
            {
                recordException(e);
            }
            catch (Exception e1)
            {
                //TODO: log
                e.printStackTrace();
            }
        }
        _hasCompleted = true;
    }

    protected void runNode(ExecuteGraphNode egn, GraphRuntimeNode rnde)
        throws CtxException
    {
        try
        {
            egn.runGraphNode();
        }
        catch(Exception e)
        {
            markNodeDone(rnde, true);
            except().rt(e, new CtxException.Context("Exception: ", ""));
        }

        List<GraphRuntimeNode> unblocked = rnde.unblockAndGet();
        GraphRuntimeNode runNow = null;
        if ((unblocked != null) && (unblocked.size() > 0))
            runNow = unblocked.get(0);

        for (int i = 1; (unblocked != null) && (i < unblocked.size()); i++)
        {
            ExecuteGraphParms p = new ExecuteGraphParms(unblocked.get(i), _parameters);
            ExecuteGraph g = (ExecuteGraph)this.repeatMe(p);
            _graphContext.schedule(g);
        }

        if (runNow != null)
        {
            ExecuteGraphNode nde = graphNodeExecutor(runNow, _parameters);
            runNode(nde, runNow);
        }
    }

    private void markNodeDone(GraphRuntimeNode nde, boolean except)
        throws CtxException
    {
        if (except) //means not going to progress further so mark them all done
        {
            List<GraphRuntimeNode> ndes = nde.blockedNodes();
            for (int i = 0; (ndes != null) && (i < ndes.size()); i++)
            {
                _graphContext.nodeDone();
                markNodeDone(ndes.get(i), except);
            }
        }
    }
}

