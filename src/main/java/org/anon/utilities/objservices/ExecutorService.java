/**
 * SMART - State Machine ARchiTecture
 *
 * Copyright (C) 2012 Individual contributors as indicated by
 * the @authors tag
 *
 * This file is a part of SMART.
 *
 * SMART is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SMART is distributed in the hope that it will be useful,
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
 * File:                org.anon.utilities.objservices.ExecutorService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service provides execution related services
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import java.util.List;

import org.anon.utilities.concurrency.AsynchExecute;
import org.anon.utilities.concurrency.SynchExecute;
import org.anon.utilities.concurrency.ExecutionUnit;
import org.anon.utilities.concurrency.ExecutionAdapter;
import org.anon.utilities.exception.CtxException;

public class ExecutorService extends ObjectServiceLocator.ObjectService
{
    enum executionstrategy
    {
        sync(new SynchExecute()),
        async(new AsynchExecute());

        private ExecutionAdapter _adapter;
        private executionstrategy(ExecutionAdapter adapt)
        {
            _adapter = adapt;
        }

        public ExecutionAdapter getAdapter()
            throws CtxException
        {
            return (ExecutionAdapter)_adapter.repeatMe(null);
        }
    }

    public ExecutorService()
    {
        super();
    }

    public void asynchWait(List<ExecutionUnit> runs)
        throws CtxException
    {
        ExecutionAdapter adapt = executionstrategy.async.getAdapter();
        adapt.executeWait(runs);
    }

    public void synch(List<ExecutionUnit> runs)
        throws CtxException
    {
        ExecutionAdapter adapt = executionstrategy.sync.getAdapter();
        adapt.executeWait(runs);
    }
}

