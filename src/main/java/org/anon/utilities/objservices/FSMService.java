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
 * File:                org.anon.utilities.objservices.FSMService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A finite state machine service that provides FSM functions
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import java.util.Map;
import java.util.HashMap;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.fsm.FiniteStateMachine;
import org.anon.utilities.exception.CtxException;

public class FSMService extends ObjectServiceLocator.ObjectService
{
    private Map<String, FiniteStateMachine> _stateMachines;

    public FSMService()
    {
        super();
        _stateMachines = new HashMap<String, FiniteStateMachine>();
    }

    public FiniteStateMachine getFSM(String stateEntityType)
    {
        return _stateMachines.get(stateEntityType);
    }

    public FiniteStateMachine create(String stateEntityType, String initial)
        throws CtxException
    {
        assertion().assertFalse(_stateMachines.containsKey(stateEntityType), "Cannot create statemachine. Already present.");
        FiniteStateMachine mc = new FiniteStateMachine(stateEntityType, initial);
        _stateMachines.put(stateEntityType, mc);
        return mc;
    }

    public FiniteStateMachine fsm(String stateEntityType)
    {
        return _stateMachines.get(stateEntityType);
    }
}

