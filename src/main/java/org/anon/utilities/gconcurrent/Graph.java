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
 * File:                org.anon.utilities.gconcurrent.Graph
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A graph representation of what should be run
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent;

import java.util.List;
import java.util.ArrayList;

import org.anon.utilities.exception.CtxException;

public class Graph
{
    private List<GraphNode> _nodes;

    public Graph()
    {
        _nodes = new ArrayList<GraphNode>();
    }

    public void addGraphNode(GraphNode nde)
    {
        _nodes.add(nde);
    }

    public void addDependency(GraphNode parent, GraphNode child)
    {
        parent.addDependant(child);
        child.addDependsOn(parent);
    }

    public GraphRuntime newRuntime()
        throws CtxException
    {
        return new GraphRuntime(_nodes);
    }

    public int graphSize()
    {
        return _nodes.size();
    }

    public List<GraphNode> nodes()
    {
        return _nodes;
    }

    public String toString()
    {
        return _nodes.toString();
    }
}


