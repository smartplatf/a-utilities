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
 * File:                org.anon.utilities.gauge.PGauge
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A performance gauge for methods
 *
 * ************************************************************
 * */

package org.anon.utilities.gauge;

import java.util.List;
import java.util.ArrayList;

import org.anon.utilities.logger.Logger;

public class PGauge
{
    class stat
    {
        long _start;
        long _end;
        long _duration;
        String _tag;

        stat(String tag)
        {
            _start = System.nanoTime();
            _tag = tag;
        }

        void end(String tag)
        {
            _end = System.nanoTime();
            _duration = (_end - _start);
            _tag = _tag + "-" + tag;
        }

        void end(stat nxt)
        {
            _end = nxt._start;
            _duration = (_end - _start);
            _tag = _tag + "-" + nxt._tag;
        }

        String dump()
        {
            return _tag + "::" + _duration + "ns ::" + _duration/1000 + "microsecs ::" + _duration/1000000 + "ms ::";
        }
    }

    private List<stat> _recorded;
    private stat _latest;

    public PGauge()
    {
    }

    public void start(String name)
    {
        _recorded = new ArrayList<stat>();
        stat s = new stat(name);
        _recorded.add(s);
        _latest = s;
    }

    public void record(String tag)
    {
        stat s = new stat(tag);
        if (_latest != null)
            _latest.end(s);
        _recorded.add(s);
        _latest = s;
    }

    public void stop()
    {
        if (_latest != null)
            _latest.end("Done");
    }

    public void dump(Logger log)
    {
        if (log.isTraceEnabled())
        {
            for (int i = 0; i < _recorded.size(); i++)
                log.trace("       PERFORMANCE:" + _recorded.get(i).dump());
        }
    }
}

