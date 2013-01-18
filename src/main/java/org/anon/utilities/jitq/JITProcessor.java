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
 * File:                org.anon.utilities.jitq.JITProcessor
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A processor for JITProcessQueues
 *
 * ************************************************************
 * */

package org.anon.utilities.jitq;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

import org.anon.utilities.exception.CtxException;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.exception.CtxException;

public class JITProcessor
{
    private ExecutorService _processor;

    public JITProcessor(String group, String name)
        throws CtxException
    {
        _processor = anatomy().jvmEnv().executorFor(group, name);
    }

    public void jitProcess(JITProcessQueue jitq)
        throws CtxException
    {
        //This loop is present here to break time related problems
        //Retry a number of times before giving up. It is possible that the
        //thread that is being processed has already processed before this object was queued
        //For eg:
        //Thread1: JITQueueRunnable: queue.poll
        //Thread2:      queue data
        //Thread2:      Check if the queue is processing
        //Thread2:      process yes, so do not start a thread
        //Thread1: Mark as doneprocessing and come out
        //To break this, we will have the Thread2 go in a loop a few times so the concurrency is
        //broken and it will look as below:
        //Thread1: JITQueueRunnable: queue.poll
        //Thread2:      queue data
        //Thread2:      Check if the queue is processing
        //Thread2:      process yes, so do not start a thread
        //Thread1: Mark as doneprocessing and come out
        //Thread2:      Check if the queue is processing 2nd time
        //Thread2:      Not processing, start a new thread
        int retrycnt = 0;
        final int MAXRETRYCNT = 3;
        while (retrycnt < MAXRETRYCNT)
        {
            if (!jitq.isProcessing())
            {
                if (jitq.setProcessing())
                {
                    JITQueueRunnable qrun = new JITQueueRunnable(jitq);
                    _processor.execute(qrun);
                }
                retrycnt = MAXRETRYCNT; //break the loop
            }
            else
            {
                try
                {
                    Thread.currentThread().sleep(100); //try every 100ms
                }
                catch (Exception e)
                {
                }
            }
            retrycnt++;
        }
    }

    public ExecutorService executor()
    {
        return _processor;
    }

    public void stopJITProcessing()
        throws CtxException
    {
        try
        {
            _processor.shutdown();
            _processor.awaitTermination(100, TimeUnit.MILLISECONDS);
        }
        catch (Exception e)
        {
            except().rt(this, e, new CtxException.Context("JITProcessor.stopJitProcessing", "Exception"));
        }
    }
}

