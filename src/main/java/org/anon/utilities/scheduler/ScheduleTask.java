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
 * File:                org.anon.utilities.scheduler.ScheduleTask
 * Author:              rsankar
 * Revision:            1.0
 * Date:                28-10-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A task to be scheduled
 *
 * ************************************************************
 * */

package org.anon.utilities.scheduler;

import java.util.Date;

public class ScheduleTask implements java.io.Serializable
{
    private Runnable runnable;
    private Date start = null; //start only at this date time
    private Date end = null; //end when this date is hit
    private int frequency = -1; //run every this seconds
    private int duration = -1; //run start after this duration once

    public ScheduleTask(Runnable task, int d)
    {
        runnable = task;
        duration = d;
    }

    public ScheduleTask(Runnable task, int d, int freq)
    {
        runnable = task;
        frequency = freq;
        duration = d;
    }

    public ScheduleTask(Runnable task, Date s, int freq)
    {
        runnable = task;
        frequency = freq;
        start = s;
    }

    public ScheduleTask(Runnable task, Date s, Date e, int freq)
    {
        runnable = task;
        start = s;
        end = e;
        frequency = freq;
        if (s.getTime() >= e.getTime())
        {
            start = null;
            end = null;
            frequency = -1;
        }
    }

    public Runnable getTask() { return runnable; }

    public int getInitialDelay()
    {
        int delay = 0;
        if (duration > 0)
        {
            delay = duration;
        }
        else if (start != null)
        {
            Date now = new Date();
            long nowt = now.getTime();
            long startt = start.getTime();

            if (startt > nowt)
                delay = (int)(startt - nowt);
        }

        return delay;
    }

    public int getFrequency()
    {
        return frequency;
    }

    public boolean shouldEnd()
    {
        boolean shouldend = false;
        if (end != null)
        {
            Date now = new Date();
            long nowt = now.getTime();
            long endt = end.getTime();

            shouldend = (endt <= nowt);
        }

        return shouldend;
    }

    public Runnable getRunnable()
    {
        return runnable;
    }
}

