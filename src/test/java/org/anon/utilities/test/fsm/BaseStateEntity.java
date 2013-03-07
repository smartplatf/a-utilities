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
 * File:                org.anon.utilities.test.fsm.BaseStateEntity
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A base class for stateentity
 *
 * ************************************************************
 * */

package org.anon.utilities.test.fsm;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import org.anon.utilities.fsm.StateEntity;
import org.anon.utilities.fsm.FiniteState;
import org.anon.utilities.fsm.FiniteStateMachine;
import org.anon.utilities.exception.CtxException;

public abstract class BaseStateEntity implements StateEntity
{
    protected String _stateEntityType;

    private FiniteState _currentState;

    public BaseStateEntity()
        throws CtxException
    {
        initStateEntityType();
        FiniteStateMachine mc = fsm().fsm(_stateEntityType);
        if (mc != null) mc.start(this);
    }

    public String utilities___stateEntityType()
    {
        return _stateEntityType;
    }

    public void utilities___setCurrentState(FiniteState state)
    {
        _currentState = state;
    }

    public FiniteState utilities___currentState() { return _currentState; }

    protected abstract void initStateEntityType();
    public abstract StateEntity utilities___parent()
        throws CtxException;

    public abstract StateEntity[] utilities___children(String setype)
        throws CtxException;
}

