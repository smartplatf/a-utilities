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
 * File:                org.anon.utilities.gconcurrent.GraphRuntime
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A runtime for the graph
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.anon.utilities.exception.CtxException;

public class GraphRuntime
{
    public Map<String, GraphRuntimeNode> _nodes;
    public List<GraphRuntimeNode> _rootNodes;

    public GraphRuntime(List<GraphNode> graph)
        throws CtxException
    {
        _nodes = new HashMap<String, GraphRuntimeNode>();
        _rootNodes = new ArrayList<GraphRuntimeNode>();

        for (GraphNode nde : graph)
        {
            GraphRuntimeNode rnde = nde.newRuntimeNode();
            _nodes.put(nde.nodeName(), rnde);
            if (!nde.isBlocked())
                _rootNodes.add(rnde);
        }
        for (GraphRuntimeNode nde : _nodes.values())
        {
            nde.setupDependants(_nodes);
        }
    }

    public List<GraphRuntimeNode> level1Nodes() { return _rootNodes; }

}

