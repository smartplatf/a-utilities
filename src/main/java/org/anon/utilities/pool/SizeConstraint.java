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
 * File:                org.anon.utilities.pool.SizeConstraint
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A constraint based on size
 *
 * ************************************************************
 * */

package org.anon.utilities.pool;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.logger.Logger;

public class SizeConstraint
{
    private int _max;
    private int _min;
    private int _incBy;
    private Logger _logger;

    SizeConstraint(int minsz, int maxsz, int inc)
    {
        _min = minsz;
        _max = maxsz;
        _incBy = inc;
        _logger = logger().rlog(this);
    }

    SizeConstraint(int minsz, int maxsz)
    {
        this(minsz, maxsz, Pool.INCREASE_BY);
    }

    SizeConstraint(int size)
    {
        this(size, size, Pool.INCREASE_BY);
    }

    public int increase(Pool pool)
    {
        int sz = pool.size();
        _logger.trace("SizeConstraint: " + sz + ":" + _max);
        if (sz >= _max)
            return 0;
        else
            return _incBy;
    }

    public boolean canUnlock(Pool pool)
    {
        int sz = pool.free();
        _logger.trace("SizeConstraint: canReleaseInto " + sz + ":" + _max);
        if (sz < _max)
            return true;

        return false;
    }

    public int adjust(Pool pool)
    {
        int adjustBy = 0;
        int sz = pool.size();
        if (sz > _min)
            adjustBy = (sz - _min);

        return adjustBy;
    }
}

