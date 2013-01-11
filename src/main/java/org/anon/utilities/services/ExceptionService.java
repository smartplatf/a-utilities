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
 * File:                org.anon.utilities.services.ExceptionService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service that provides throw and rethrow for exceptions
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import java.util.List;
import java.util.ArrayList;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;
import org.anon.utilities.exception.AssertionException;
import org.anon.utilities.exception.RepeatParms;
import org.anon.utilities.exception.CtxException.Context;
import org.anon.utilities.logger.Logger;

public class ExceptionService extends ServiceLocator.Service
{
    private List<CtxException> _registeredExceptions = new ArrayList<CtxException>();

    protected ExceptionService()
    {
        super();
        registerException(new AssertionException("")); //by default out of the box
    }

    //To avoid reflection during every exception call. This just is a reference instance which
    //will beused to create other instances
    protected void registerException(CtxException exceptclass)
    {
        _registeredExceptions.add(exceptclass);
    }

    protected CtxException throwHandler(Object obj, String mthd, Throwable t, String msg, Context[] ctx)
    {
        CtxException throwexcept = null;
        if ((t != null) && (t instanceof CtxException))
        {
            throwexcept = (CtxException)t;
        }
        else
        {
            for (CtxException except : _registeredExceptions)
            {
                if (except.throwme(obj, mthd))
                {
                    RepeatParms parms = new RepeatParms(mthd, msg, t);
                    throwexcept = (CtxException)except.repeatMe(parms);
                    break;
                }
            }
        }

        if (throwexcept == null)
            throwexcept = new CtxException(msg, t);

        String mod = "Context.Exception";
        if (obj != null)
            mod = obj.getClass().getName();

        Logger log = logger().logger(obj, mod);
        log.fatal(msg, throwexcept);

        if ((ctx != null) && (ctx.length > 0))
            throwexcept.addContextData(ctx);
        return throwexcept;
    }

    public void rt(Object obj, String mthd, Throwable t, String msg, Context ctx)
        throws CtxException
    {
        CtxException except = throwHandler(obj, mthd, t, msg, new Context[] { ctx } );
        throw except;
    }

    public void rt(Object obj, Throwable t, String msg, Context ctx)
        throws CtxException
    {
        rt(obj, "", t, msg, ctx);
    }

    public void rt(Object obj, Throwable t, Context ctx)
        throws CtxException
    {
        rt(obj, t, t.getMessage(), ctx);
    }

    public void rt(Throwable t, Context ctx)
        throws CtxException
    {
        rt(null, t, t.getMessage(), ctx);
    }

    public void rt(Throwable t, String msg, Context ctx)
        throws CtxException
    {
        rt(null, t, msg, ctx);
    }

    public void te(Object obj, String mthd, String msg, Context ctx)
        throws CtxException
    {
        Context[] actx = null;
        if (ctx != null)
            actx = new Context[] { ctx };
        CtxException except = throwHandler(obj, mthd, null, msg, actx);
        throw except;
    }

    public void te(String msg, Context ctx)
        throws CtxException
    {
        te(null, "", msg, ctx);
    }

    public void te(String mthd, String msg, Context ctx)
        throws CtxException
    {
        te(null, mthd, msg, ctx);
    }

    public void te(Object obj, String msg, Context ctx)
        throws CtxException
    {
        te(obj, "", msg, ctx);
    }

    public void te(Object obj, String mthd, String msg)
        throws CtxException
    {
        te(obj, mthd, msg, null);
    }

    public void te(Object obj, String msg)
        throws CtxException
    {
        te(obj, "", msg, null);
    }

    public void te(String msg)
        throws CtxException
    {
        te(null, "", msg, null);
    }
}

