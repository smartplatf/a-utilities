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
 * File:                org.anon.utilities.fsm.FiniteState
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A representation of the state of an object
 *
 * ************************************************************
 * */

package org.anon.utilities.fsm;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public final class FiniteState implements java.io.Serializable
{
    private String _stateEntityType;
    private final String _stateName;
    private String _script;
    private boolean _initialState;
    private boolean _finalState;

    FiniteState(String type, String name)
        throws CtxException
    {
        this(type, name, false, false);
    }

    public FiniteState(String type, String stateName, boolean start, boolean end)
        throws CtxException
    {
        if (start || end)
            assertion().assertTrue((start != end), "The same state cannot be start and end"); //start and end states cannot be the same
        _stateEntityType = type;
        _stateName = stateName;
        _initialState = start;
        _finalState = end;
    }

    public void setScript(String script)
    {
        _script = script;
    }

    public String stateEntityType() { return _stateEntityType; }
    public String stateName() { return _stateName; }
    public boolean initialState() { return _initialState; }
    public boolean finalState() { return _finalState; }
    public String script() { return _script; }
    public String toString()
    {
        return _stateEntityType + ":" + _stateName;
    }

    public FiniteState createNew()
        throws CtxException
    {
        FiniteState state = new FiniteState(_stateEntityType, _stateName, _initialState, _finalState);
        return state;
    }
}

