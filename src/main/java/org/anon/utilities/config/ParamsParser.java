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
 * File:                org.anon.utilities.config.ParamsParser
 * Author:              rsankar
 * Revision:            1.0
 * Date:                23-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A parser to parse the parameters to a method
 *
 * ************************************************************
 * */

package org.anon.utilities.config;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class ParamsParser extends ListParser implements Constants
{
    public ParamsParser()
    {
    }

    @Override
    public int[] asInt(String val)
    {
        return new int[0];
    }

    @Override
    public String[] asString(String val)
        throws CtxException
    {
        val = val.trim();
        if (!val.startsWith(PARAM_START))
            except().te(this, "Cannot parse params from " + val + ". Does not start with (");

        if (!val.endsWith(PARAM_END))
            except().te(this, "Cannot parse params from " + val + ". Does not end with )");

        val = val.substring(1, (val.length() -1));
        return super.asString(val);
    }
}

