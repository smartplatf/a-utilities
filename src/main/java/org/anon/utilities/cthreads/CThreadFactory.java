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
 * File:                org.anon.utilities.cthreads.CThreadFactory
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A factory that creates context threads
 *
 * ************************************************************
 * */

package org.anon.utilities.cthreads;

import java.util.concurrent.ThreadFactory;

public class CThreadFactory implements ThreadFactory
{
    private String _name;
    private String _group;

    public CThreadFactory(String group, String name)
    {
        _group = group;
        _name = name;
    }

    public Thread newThread(Runnable r)
    {
        Thread thrd = new CThread(_group, _name, r);
        thrd.setName(_name + thrd.getName());
        return thrd;
    }
}

