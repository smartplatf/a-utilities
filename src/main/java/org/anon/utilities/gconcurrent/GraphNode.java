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
 * File:                org.anon.utilities.gconcurrent.GraphNode
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A graph node of execution with dependecies
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;

import org.anon.utilities.exception.CtxException;

public class GraphNode implements java.io.Serializable
{
    private transient Class _clazz;
    private transient Method _method;

    private String _nodeName;
    private NodeDetails _details;

    private List<GraphNode> _dependsOn;
    private List<GraphNode> _dependants;

    public GraphNode(String name, Class cls, Method mthd, NodeDetails det)
    {
        _nodeName = name;
        _clazz = cls;
        _method = mthd;
        _details = det;
        _dependsOn = new ArrayList<GraphNode>();
        _dependants = new ArrayList<GraphNode>();
    }

    void addDependsOn(GraphNode nde) { if (!_dependsOn.contains(nde)) _dependsOn.add(nde); }
    void addDependant(GraphNode nde) { if (!_dependants.contains(nde)) _dependants.add(nde); }

    public NodeDetails details() { return _details; }
    public String nodeName() { return _nodeName; }

    public boolean isBlocked() { return (_dependsOn.size() > 0); }

    public Gateway newGateway()
        throws CtxException
    {
        return new Gateway(_nodeName, _dependsOn.size());
    }

    public GraphRuntimeNode newRuntimeNode()
        throws CtxException
    {
        return new GraphRuntimeNode(this);
    }

    private List<GraphRuntimeNode> rtFor(Map<String, GraphRuntimeNode> rtnodes, List<GraphNode> ndes)
    {
        List<GraphRuntimeNode> rtndes = new ArrayList<GraphRuntimeNode>();
        for (GraphNode nde : ndes)
        {
            rtndes.add(rtnodes.get(nde.nodeName()));
        }
        return rtndes;
    }

    List<GraphRuntimeNode> rtForDependsOn(Map<String, GraphRuntimeNode> rtnodes)
    {
        return rtFor(rtnodes, _dependsOn);
    }

    List<GraphRuntimeNode> rtForDependants(Map<String, GraphRuntimeNode> rtnodes)
    {
        return rtFor(rtnodes, _dependants);
    }

    public Method method() { return _method; }
    public Class clazz() { return _clazz; }

    public String toString()
    {
        return _nodeName + ":" + ":" + _dependsOn;
    }
}

