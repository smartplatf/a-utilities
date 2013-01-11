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
 * File:                org.anon.utilities.objservices.JITQueueService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A jit queue service that stores and remembers the processors for each group
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.jitq.JITProcessor;
import org.anon.utilities.jitq.JITProcessQueue;
import org.anon.utilities.exception.CtxException;

public class JITQueueService extends ObjectServiceLocator.ObjectService
{
    private Map<String, JITProcessor> _processors;

    public JITQueueService()
    {
        super();
        _processors = new ConcurrentHashMap<String, JITProcessor>();
    }

    private String key(String group, String name)
    {
        return group + "-" + name;
    }

    public JITProcessor registerProcessor(String group)
        throws CtxException
    {
        //assumption is that group is like object, response etc, while
        //name is the tenant name?
        String name = loader().relatedName();
        String key = key(group, name);
        if (_processors.get(key) == null)
        {
            JITProcessor process = new JITProcessor(group, name);
            _processors.put(key, process);
        }

        return _processors.get(key);
    }

    public void processJITQueue(JITProcessQueue jitq)
        throws CtxException
    {
        String group = jitq.belongsTo();
        String name = loader().relatedName();
        String key = key(group, name);
        JITProcessor processor = _processors.get(key);
        if (processor != null)
        {
            processor.jitProcess(jitq);
        }
        else
            except().te(this, "Please register a processor for this queue. " + key);
    }
}

