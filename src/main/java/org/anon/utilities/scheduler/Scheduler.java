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
 * File:                org.anon.utilities.scheduler.Scheduler
 * Author:              rsankar
 * Revision:            1.0
 * Date:                28-10-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A scheduler implementation
 *
 * ************************************************************
 * */

package org.anon.utilities.scheduler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.anon.utilities.exception.CtxException;

import static org.anon.utilities.services.ServiceLocator.*;

public class Scheduler
{
    private ScheduledExecutorService _scheduler;

    public Scheduler()
    {
        _scheduler = Executors.newScheduledThreadPool(1);
    }

    public void schedule(ScheduleTask task)
        throws CtxException
    {
        try
        {
            int initdelay = task.getInitialDelay();
            int frequency = task.getFrequency();

            if (frequency > 0)
            {
                _scheduler.scheduleWithFixedDelay(task.getRunnable(), initdelay, frequency, TimeUnit.MILLISECONDS);
            }
            else
            {
                _scheduler.schedule(task.getRunnable(), initdelay, TimeUnit.MILLISECONDS);
            }
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Exception", e.getMessage()));
        }
    }

    public void unschedule(ScheduleTask task)
        throws CtxException
    {
        try
        {
            ScheduledThreadPoolExecutor execute = (ScheduledThreadPoolExecutor)_scheduler;
            execute.remove(task.getRunnable());
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Exception", e.getMessage()));
        }
    }

    public void shutdown()
        throws CtxException
    {
        _scheduler.shutdownNow();
    }
}

