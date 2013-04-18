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
 * File:                org.anon.utilities.concurrency.AsynchExecute
 * Author:              rsankar
 * Revision:            1.0
 * Date:                01-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An asynchronous execution of the runnable
 *
 * ************************************************************
 * */

package org.anon.utilities.concurrency;

import java.util.List;
import java.util.ArrayList;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.utils.RepeaterVariants;
import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.exception.CtxException;

public class AsynchExecute extends AbstractExecute
{
    private List<Thread> _runningThreads;
    private List<ExceptionCollector> _collect;
    private List<ExecutionUnit> _units;

    public AsynchExecute()
    {
        super();
        _runningThreads = new ArrayList<Thread>();
        _units = new ArrayList<ExecutionUnit>();
        _collect = new ArrayList<ExceptionCollector>();
    }

    public void execute(ExecutionUnit unit)
        throws CtxException
    {
        ExceptionCollector collect = new ExceptionCollector();
        ConcurrentRunnable run = new ConcurrentRunnable(unit, collect);
        Thread thrd = new Thread(run);
        boolean add = true;
        if ((unit instanceof ConfigurableUnit) && (!((ConfigurableUnit)unit).waitToComplete()))
            add = false;
        if (add)
            _runningThreads.add(thrd);
        _collect.add(collect);
        thrd.start();
    }

    public void waitToComplete()
        throws CtxException
    {
        try
        {
            for (int i = 0; (_runningThreads != null) && (i < _runningThreads.size()); i++)
                _runningThreads.get(i).join();

            //once completed, throw the exceptions
            //For now only the first exception is thrown, but
            //TODO: throw all the exceptions collected together
            for (int i = 0; i < (_collect.size()); i++)
                _collect.get(i).throwCollection();
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("waitToComplete", "Exception"));
        }
    }

    public Repeatable repeatMe(RepeaterVariants parms)
        throws CtxException
    {
        return new AsynchExecute();
    }
}

