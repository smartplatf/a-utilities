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
 * File:                org.anon.utilities.fsm.FiniteStateMachine
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A finite state machine that stores different states for a state entity
 *
 * ************************************************************
 * */

package org.anon.utilities.fsm;

import java.util.Map;
import java.util.HashMap;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class FiniteStateMachine
{
    private String _stateEntityType;
    private FiniteStateGraph _startState;
    private Map<String, FiniteStateGraph> _states;

    public FiniteStateMachine(String type, String initial)
        throws CtxException
    {
        _stateEntityType = type;
        _states = new HashMap<String, FiniteStateGraph>();
        _startState = addFiniteState(initial, true, false);
    }

    public FiniteStateGraph addState(String name)
        throws CtxException
    {
        return addFiniteState(name, false, false);
    }

    public FiniteStateGraph addEndState(String name)
        throws CtxException
    {
        return addFiniteState(name, false, true);
    }

    private FiniteStateGraph addFiniteState(String name, boolean start, boolean end)
        throws CtxException
    {
        assertion().assertNotNull(name, "Cannot add a null state");
        assertion().assertTrue((name.length() > 0), "Cannot add an empty state");
        assertion().assertFalse(_states.containsKey(name), "Cannot readd a state, this state is already present.");

        FiniteState state = new FiniteState(_stateEntityType, name, start, end);
        FiniteStateGraph graph = new FiniteStateGraph(state);
        _states.put(name, graph);
        return graph;
    }

    public void transition(StateEntity entity, String name)
        throws CtxException
    {
        assertion().assertNotNull(entity, "Cannot transition a null entity.");
        assertion().assertEquals(entity.utilities___stateEntityType(), _stateEntityType, "The type of the object is not the same as this state machine: " + entity.utilities___stateEntityType() + ":" + _stateEntityType);
        FiniteStateGraph graph = _states.get(name);
        assertion().assertNotNull(graph, "Not a valid state: " + name);
        graph.transitionToMe(entity);
    }

    public void start(StateEntity entity)
        throws CtxException
    {
        assertion().assertNotNull(entity, "Cannot start a null entity.");
        assertion().assertEquals(entity.utilities___stateEntityType(), _stateEntityType, "The type of the object is not the same as this state machine: " + entity.utilities___stateEntityType() + ":" + _stateEntityType);
        assertion().assertNotNull(_startState, "No valid start states found ");
        _startState.transitionToMe(entity);
    }

    public boolean isDead(StateEntity entity)
        throws CtxException
    {
        FiniteState state = entity.utilities___currentState();
        boolean ret = ((state != null) && (state.finalState()));
        return ret;
    }
}

