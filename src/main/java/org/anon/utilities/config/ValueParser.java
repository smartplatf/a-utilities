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
 * File:                org.anon.utilities.config.ValueParser
 * Author:              rsankar
 * Revision:            1.0
 * Date:                23-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An interface to parse config values
 *
 * ************************************************************
 * */

package org.anon.utilities.config;

import java.util.List;

import org.anon.utilities.exception.CtxException;

public interface ValueParser
{
    public String[] asString(String value)
        throws CtxException;
    public int[] asInt(String value)
        throws CtxException;
    public <T extends ParsedObject> List<T> asObject(String value, Class<T> clazz)
        throws CtxException;
    public boolean recognizesFormat(String value);
}

