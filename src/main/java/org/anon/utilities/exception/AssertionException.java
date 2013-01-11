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
 * File:                org.anon.utilities.exception.AssertionException
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An assertion exception that is thrown on an assert statement
 *
 * ************************************************************
 * */

package org.anon.utilities.exception;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;

public class AssertionException extends CtxException
{
    public AssertionException(String msg)
    {
        super(msg);
    }

    @Override
    public boolean throwme(Object obj, String mthd)
    {
        if ((mthd != null) && (mthd.startsWith("AssertionService.assert")))
            return true; 

        return false;
    }

    @Override
    public Repeatable repeatMe(RepeaterVariants vars)
    {
        RepeatParms parms = (RepeatParms)vars;
        AssertionException except = new AssertionException("AssertionFailed: " + parms.message());
        if ((parms.method() != null) && (parms.method().length() > 0))
        {
            except.addContextData("Assert", parms.method());
        }

        return except;
    }
}

