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
 * File:                org.anon.utilities.gconcurrent.GraphRuntimeNode
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A runtime node for the graphnode
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;

import org.anon.utilities.gconcurrent.execute.PDescriptor;
import org.anon.utilities.exception.CtxException;

public class GraphRuntimeNode
{
    private GraphNode _myNode;
    private Gateway _myGateway;
    private List<GraphRuntimeNode> _blocking;

    public GraphRuntimeNode(GraphNode node)
        throws CtxException
    {
        _myNode = node;
        _myGateway = node.newGateway();
    }

    public boolean releaseBlock()
        throws CtxException
    {
        return _myGateway.releaseBlock();
    }

    //unblocks and returns the ones that are completely unblocked
    public List<GraphRuntimeNode> unblockAndGet()
        throws CtxException
    {
        List<GraphRuntimeNode> unblocked = new ArrayList<GraphRuntimeNode>();
        if (_blocking != null)
        {
            for (GraphRuntimeNode nde : _blocking)
            {
                if ((nde != null) && nde.releaseBlock())
                    unblocked.add(nde);
            }
        }
        return unblocked;
    }

    public void setupDependants(Map<String, GraphRuntimeNode> rtnodes)
    {
        _blocking = _myNode.rtForDependants(rtnodes);
    }

    public List<PDescriptor> descriptors()
    {
        return _myNode.details().pdescriptors();
    }

    public List<GraphRuntimeNode> blockedNodes() { return _blocking; }
    public Method method() { return _myNode.method(); }
    public Class clazz() { return _myNode.clazz(); }
    public NodeDetails details() { return _myNode.details(); }

    public String toString()
    {
        return _myNode.toString();
    }
}

