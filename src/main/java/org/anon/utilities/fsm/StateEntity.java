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
 * File:                org.anon.utilities.fsm.StateEntity
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An entity which is associated with state transitions
 *
 * ************************************************************
 * */

package org.anon.utilities.fsm;

import org.anon.utilities.exception.CtxException;

public interface StateEntity
{
    public String utilities___stateEntityType();

    public void utilities___setCurrentState(FiniteState state);
    public FiniteState utilities___currentState();

    public StateEntity utilities___parent()
        throws CtxException;

    public StateEntity[] utilities___children(String setype)
        throws CtxException;
}

