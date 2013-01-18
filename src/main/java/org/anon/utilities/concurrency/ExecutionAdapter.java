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
 * File:                org.anon.utilities.concurrency.ExecutionAdapter
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An adapter for different types of execution
 *
 * ************************************************************
 * */

package org.anon.utilities.concurrency;

import java.util.List;

import org.anon.utilities.exception.CtxException;
import org.anon.utilities.utils.Repeatable;

public interface ExecutionAdapter extends Repeatable
{
    public void execute(ExecutionUnit run)
        throws CtxException;

    public void waitToComplete()
        throws CtxException;

    public void executeWait(List<ExecutionUnit> runs)
        throws CtxException;

    public void execute(List<ExecutionUnit> runs)
        throws CtxException;
}

