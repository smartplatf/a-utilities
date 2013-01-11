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
 * File:                org.anon.utilities.config.Format
 * Author:              rsankar
 * Revision:            1.0
 * Date:                23-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A interface to be implemented to support configurations
 *
 * ************************************************************
 * */

package org.anon.utilities.config;

import java.util.Map;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.exception.CtxException;

public interface Format extends Repeatable
{
    public Object valueFor(String name)
        throws CtxException;
    public String asString(String name)
        throws CtxException;
    public int asInt(String name)
        throws CtxException;
    public boolean containsName(String name)
        throws CtxException;
    public Map<String, Object> valueForStartsWith(String name)
        throws CtxException;
    public Map allValues()
        throws CtxException;
}

