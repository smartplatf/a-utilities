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
 * File:                org.anon.utilities.cthreads.CThread
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A context thread that reflects context and runtime
 *
 * ************************************************************
 * */

package org.anon.utilities.cthreads;

import java.util.Map;
import java.util.HashMap;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class CThread extends Thread
{
    private CThreadContext _thrdContext;
    private String _savedName;
    private RuntimeContext _runtime;
    private Map<String, Object> _contextLocals;

    public CThread(String group, String name, Runnable run)
    {
        super(new ThreadGroup(name + "_" + group), run);
    }

    void setContext(CThreadContext ctx)
    {
        _thrdContext = ctx;
    }

    public void setContextLocals(Map<String, Object> locals)
    {
        _contextLocals = locals;
    }

    public void addToContextLocals(String name, Object val)
    {
        if (_contextLocals == null)
            _contextLocals = new HashMap<String, Object>();

        _contextLocals.put(name, val);
    }

    public Object ctxLocal(String key)
    {
        if (_contextLocals != null)
            return _contextLocals.get(key);

        return null;
    }

    public RuntimeContext runtime()
    {
        return _runtime;
    }

    public CThreadContext thrdContext()
    {
        return _thrdContext;
    }

    public void startRuntime(String action, CtxRunnable crun)
        throws CtxException
    {
        if (crun != null)
            _runtime = crun.startRuntimeContext(action);
    }

    void reflectCtx()
    {
        _savedName = this.getName();
        if (_thrdContext != null)
        {
            String nm = _savedName + "-" + _thrdContext.id().toString();
            if ((_thrdContext.extras() != null) && (_thrdContext.extras().length() > 0))
                nm = nm + "-" + _thrdContext.extras();

            this.setName(nm);
        }
    }

    void resetCtx()
    {
        _thrdContext = null;
        _runtime = null;
        _contextLocals = null;
        this.setName(_savedName);
    }

}

