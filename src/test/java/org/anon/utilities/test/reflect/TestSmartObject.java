/**
 * SMART - State Machine ARchiTecture
 *
 * Copyright (C) 2012 Individual contributors as indicated by
 * the @authors tag
 *
 * This file is a part of SMART.
 *
 * SMART is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SMART is distributed in the hope that it will be useful,
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
 * File:                org.anon.utilities.test.reflect.TestSmartObject
 * Author:              rsankar
 * Revision:            1.0
 * Date:                27-08-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test with smart fields to check dirty fields work
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.anon.utilities.fsm.StateEntity;
import org.anon.utilities.fsm.FiniteState;
import org.anon.utilities.fsm.FiniteStateMachine;
import org.anon.utilities.exception.CtxException;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;

public class TestSmartObject implements java.io.Serializable, StateEntity
{
    private String FlowName;
    private String SoaFile;
    private String Summary;
    private String Description;
    private String Information;
    private String JavaDocURL;
    private String JarFile;
    private String Developer;
    private String displayImage;
    private FiniteState ___smart_state___;
    private DataLegend ___smart_legend___;
    private String ___smart_name___;
    //private Date CreatedDate;

    public TestSmartObject()
        throws Exception
    {
        SimpleDateFormat fmt = new SimpleDateFormat("EEE, MMM d, ''yy");
        ___smart_legend___ = new DataLegend();
        ___smart_name___ = "TestSmartObject";
        FlowName = "Survey1";
        SoaFile = "Survey.soa";
        Summary = "Survey1";
        Description = "Survey1";
        Information = "Survey1";
        //CreatedDate = fmt.parse("Wed, Jul 4, '01");
        JarFile = "/home/rsankar/privategithub/p-smart/sm.kernel/src/main/resources/fsStore/fixchg/Archives/fdd62020-4bf6-4b3e-8079-35b0bd1e9cf0.jar";
        Developer = "rsankar";
        displayImage = "809491c3-ed3f-427d-b595-0982cf93fe62.jpg";
        //JavaDocURL = "";
        FiniteStateMachine mc = fsm().fsm("ComplexTestObject");
        if (mc != null) mc.start(this);
    }

    public String utilities___stateEntityType()
    {
        return "ComplexTestObject";
    }

    public void utilities___setCurrentState(FiniteState state)
    {
        ___smart_state___ = state;
    }

    public FiniteState utilities___currentState() { 
        return ___smart_state___; 
    }

    public StateEntity utilities___parent()
        throws CtxException
    {
        return null;
    }

    public StateEntity[] utilities___children(String setype)
        throws CtxException
    {
        return null;
    }
}

