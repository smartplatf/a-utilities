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
 *
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.anon.utilities.test.gauge.TestPGauge
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * TestPGauge
 *
 * ************************************************************
 * */

package org.anon.utilities.test.gauge;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.logger.Logger;

public class TestPGauge
{
    @Test
    public void testTestPGauge()
        throws Exception
    {
        Logger log = logger().glog("TestPGauge");
        perf().startHere("Testing");
        for (int i = 0; i < 100; i++)
            Thread.currentThread().sleep(10);
        perf().checkpointHere("SecondTest");
        for (int i = 0; i < 100; i++)
            Thread.currentThread().sleep(10);
        perf().dumpHere(log);
    }
}

