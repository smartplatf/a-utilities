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
 * File:                org.anon.utilities.pool.DefaultCreator
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A default creator for entities in pool
 *
 * ************************************************************
 * */

package org.anon.utilities.pool;

import org.anon.utilities.crosslink.CrossLinkAny;
import org.anon.utilities.exception.CtxException;

public class DefaultCreator implements EntityCreator
{
    private Class<? extends PoolEntity> _create;

    public DefaultCreator(Class<? extends PoolEntity> cls)
    {
        _create = cls;
    }

    public PoolEntity newEntity()
        throws CtxException
    {
        CrossLinkAny any = new CrossLinkAny(_create.getName());
        return (PoolEntity)any.create();
    }
}

