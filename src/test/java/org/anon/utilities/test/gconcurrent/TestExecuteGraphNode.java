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
 * File:                org.anon.utilities.test.gconcurrent.TestExecuteGraphNode
 * Author:              rsankar
 * Revision:            1.0
 * Date:                18-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A node executor for the graph
 *
 * ************************************************************
 * */

package org.anon.utilities.test.gconcurrent;

import org.anon.utilities.gconcurrent.GraphRuntimeNode;
import org.anon.utilities.gconcurrent.execute.ProbeParms;
import org.anon.utilities.gconcurrent.execute.ExecuteGraphNode;
import org.anon.utilities.exception.CtxException;

public class TestExecuteGraphNode extends ExecuteGraphNode
{
    public TestExecuteGraphNode(GraphRuntimeNode nde, ProbeParms parms)
    {
        super(nde, parms);
    }

    protected Object runtimeObject(Class cls)
        throws CtxException
    {
        return new MyRunner();
    }

    protected boolean successOrFailure(Object ret)
        throws CtxException
    {
        System.out.println("Success");
        return true;
    }

    protected void done()
        throws CtxException
    {
        System.out.println("Done Node.");
    }
}

