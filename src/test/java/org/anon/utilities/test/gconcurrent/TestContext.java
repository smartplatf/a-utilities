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
 * File:                org.anon.utilities.test.gconcurrent.TestContext
 * Author:              rsankar
 * Revision:            1.0
 * Date:                18-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test context for execution
 *
 * ************************************************************
 * */

package org.anon.utilities.test.gconcurrent;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import org.anon.utilities.gconcurrent.Graph;
import org.anon.utilities.gconcurrent.GraphRuntimeNode;
import org.anon.utilities.gconcurrent.execute.ExecuteGraph;
import org.anon.utilities.gconcurrent.execute.ProbeParms;
import org.anon.utilities.gconcurrent.execute.AbstractGraphContext;
import org.anon.utilities.gconcurrent.execute.DefaultProbeParms;
import org.anon.utilities.cthreads.CThreadContext;
import org.anon.utilities.exception.CtxException;

public class TestContext extends AbstractGraphContext implements CThreadContext
{
    private UUID _ctxID;

    public TestContext(Graph g, ExecutorService service)
        throws CtxException
    {
        super(g, service);
        _ctxID = UUID.randomUUID();
    }

    protected ExecuteGraph executorFor(GraphRuntimeNode rtnde, ProbeParms parms)
        throws CtxException
    {
        return new TestExecuteGraph(rtnde, parms);
    }

    protected ProbeParms myParms()
        throws CtxException
    {
        List<Object> parms = new ArrayList<Object>();
        return new DefaultProbeParms(this, parms);
    }

    public UUID id()
    {
        return _ctxID;
    }

    public String extras()
    {
        return "Test";
    }
}

