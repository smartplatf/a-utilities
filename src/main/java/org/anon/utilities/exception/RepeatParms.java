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
 * File:                org.anon.utilities.exception.RepeatParms
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of parameters to be passed for repeating exception
 *
 * ************************************************************
 * */

package org.anon.utilities.exception;

import org.anon.utilities.utils.RepeaterVariants;

public class RepeatParms implements RepeaterVariants
{
    private String _method;
    private String _message;
    private Throwable _throwable;

    public RepeatParms(String mthd, String msg, Throwable t)
    {
        _method = mthd;
        _message = msg;
        _throwable = t;
    }

    public String method() { return _method; }
    public String message() { return _message; }
    public Throwable throwable() { return _throwable; }
}

