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
 * File:                org.anon.utilities.fsm.FiniteStateGraph
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A graph for the current state
 *
 * ************************************************************
 * */

package org.anon.utilities.fsm;

import java.util.Map;
import java.util.HashMap;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public final class FiniteStateGraph
{
    private FiniteState _state;
    private Map<String, String> _parentTransition;
    private Map<String, String> _childTransition;

    FiniteStateGraph(FiniteState state)
    {
        _state = state;
        _parentTransition = new HashMap<String, String>();
        _childTransition = new HashMap<String, String>();
    }

    public FiniteState state() { return _state; }

    public void addParentTransition(String type, String state)
        throws CtxException
    {
        assertion().assertFalse(type.equals(_state.stateEntityType()), "Cannot co-transition the same type of object to another state.");
        assertion().assertFalse(_parentTransition.containsKey(type), "Co-Transition for type: " + type + " already present.");

        _parentTransition.put(type, state);
    }

    public void addChildTransition(String type, String state)
        throws CtxException
    {
        assertion().assertFalse(type.equals(_state.stateEntityType()), "Cannot co-transition the same type of object to another state.");
        assertion().assertFalse(_childTransition.containsKey(type), "Co-Transition for type: " + type + " already present.");

        _childTransition.put(type, state);
    }

    private void transitionDependant(StateEntity entity, String state)
        throws CtxException
    {
        FiniteStateMachine mc = fsm().fsm(entity.utilities___stateEntityType());
        assertion().assertNotNull(mc, "No state machine found for statetype: " + entity.utilities___stateEntityType());
        mc.transition(entity, state);
    }

    public void transitionToMe(StateEntity entity)
        throws CtxException
    {
        entity.utilities___setCurrentState(_state);
        StateEntity parent = entity.utilities___parent();
        if ((parent != null) && (_parentTransition.containsKey(parent.utilities___stateEntityType())))
        {
            String state = _parentTransition.get(parent.utilities___stateEntityType());
            transitionDependant(parent, state);
        }

        for (String type : _childTransition.keySet())
        {
            String state = _childTransition.get(type);
            StateEntity[] children = entity.utilities___children(type);
            for (int i = 0; (children != null) && (i < children.length); i++)
                transitionDependant(children[i], state);
        }
    }
}

