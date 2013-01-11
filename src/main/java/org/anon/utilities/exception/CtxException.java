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
 * File:                org.anon.utilities.exception.CtxException
 * Author:              rsankar
 * Revision:            1.0
 * Date:                03-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An exception thrown in all anon module
 *
 * ************************************************************
 * */

package org.anon.utilities.exception;

import java.util.List;
import java.util.ArrayList;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;

public class CtxException extends Exception implements Repeatable
{
    public static class Context
    {
        String _label;
        String _message;

        public Context(String label, String message)
        {
            _label = label;
            _message = message;
        }

        public String toString()
        {
            return "[" + _label + ":" + _message + "]";
        }
    }

    //This stores the current context from which the exception is thrown
    private List<Context> _ctx;

    public CtxException(Throwable t)
    {
        super(t);
    }

    public CtxException(String msg)
    {
        super(msg);
    }

    public CtxException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public void addContextData(String label, String value)
    {
        if (_ctx == null)
            _ctx = new ArrayList<Context>();
        _ctx.add(new Context(label, value));
    }

    public void addContextData(Context[] ctx)
    {
        if (_ctx == null)
            _ctx = new ArrayList<Context>();
        for (int i = 0; i < ctx.length; i++)
        {
            if (ctx[i] != null)
                _ctx.add(ctx[i]);
        }
    }

    public String getCtxMessage(String msg)
    {
        if (msg == null) msg = "";
        msg += ":";

        for (int i = 0; (_ctx != null) && (i < _ctx.size()); i++)
        {
            msg += _ctx.get(i).toString();
        }

        return msg;
    }

    @Override
    public String getMessage()
    {
        if (getCause() != null)
        {
            return getCtxMessage(getCause().getMessage());
        }

        return getCtxMessage(super.getMessage());
    }

    //override me
    public boolean throwme(Object obj, String mthd)
    {
        return true; //default is this
    }

    //override me
    public Repeatable repeatMe(RepeaterVariants vars)
    {
        RepeatParms parms = (RepeatParms)vars;
        return new CtxException(parms.message(), parms.throwable());
    }
}

