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
 * File:                org.anon.utilities.test.fsm.TestFSM
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of test cases for state changes
 *
 * ************************************************************
 * */

package org.anon.utilities.test.fsm;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.anon.utilities.objservices.ObjectServiceLocator.*;
import org.anon.utilities.fsm.FiniteStateMachine;
import org.anon.utilities.fsm.FiniteStateGraph;
import org.anon.utilities.fsm.StateEntity;

public class TestFSM
{
    private FiniteStateMachine createTSMStates()
        throws Exception
    {
        FiniteStateMachine mc = fsm().fsm("TestStateEntity");
        if (mc == null)
        {
            mc = fsm().create("TestStateEntity", "start");
            mc.addState("state1");
            mc.addEndState("state2");
        }
        return mc;
    }

    @Test
    public void testTestFSM()
        throws Exception
    {
        FiniteStateMachine mc = createTSMStates();

        TestStateEntity tsm = new TestStateEntity();
        //mc.transition(tsm, "start");
        assertTrue(tsm.utilities___currentState() != null);
        assertTrue(tsm.utilities___currentState().stateName().equals("start"));
        System.out.println(tsm.utilities___currentState());
        mc.transition(tsm, "state1");
        assertTrue(tsm.utilities___currentState() != null);
        assertTrue(tsm.utilities___currentState().stateName().equals("state1"));
        System.out.println(tsm.utilities___currentState());
        mc.transition(tsm, "state2");
        assertTrue(tsm.utilities___currentState() != null);
        assertTrue(tsm.utilities___currentState().stateName().equals("state2"));
        System.out.println(tsm.utilities___currentState());
    }

    public void assertEntity2(TestStateEntity2 entity2, String st2, String st1)
        throws Exception
    {
        assertTrue(entity2.utilities___currentState() != null);
        assertTrue(entity2.utilities___currentState().stateName().equals(st2));

        StateEntity[] children = entity2.utilities___children("TestStateEntity");
        assertTrue(children != null);
        for (int i = 0; i < children.length; i++)
        {
            assertTrue(children[i].utilities___currentState() != null);
            assertTrue(children[i].utilities___currentState().stateName().equals(st1));
        }
    }

    private FiniteStateMachine createTSM2States()
        throws Exception
    {
        FiniteStateMachine mc = fsm().fsm("TestStateEntity2");
        if (mc == null)
        {
            mc = fsm().create("TestStateEntity2", "start2");
            FiniteStateGraph graph = mc.addState("state21");
            graph.addChildTransition("TestStateEntity", "state1");
            graph = mc.addEndState("state22");
            graph.addChildTransition("TestStateEntity", "state2");
        }

        return mc;
    }

    @Test
    public void testTestChildren()
        throws Exception
    {
        FiniteStateMachine mc1 = createTSMStates();
        FiniteStateMachine mc = createTSM2States();

        TestStateEntity2 entity2 = new TestStateEntity2();
        assertEntity2(entity2, "start2", "start");
        mc.transition(entity2, "state21");
        assertEntity2(entity2, "state21", "state1");
        mc.transition(entity2, "state22");
        assertEntity2(entity2, "state22", "state2");

        assertTrue(mc.isDead(entity2));
        StateEntity[] children = entity2.utilities___children("TestStateEntity");
        assertTrue(children != null);
        for (int i = 0; i < children.length; i++)
        {
            assertTrue(mc1.isDead(children[i]));
        }
    }

    private FiniteStateMachine createInnerTSM2States()
        throws Exception
    {
        FiniteStateMachine mc = fsm().fsm("TestInnerStateEntity");
        if (mc == null)
        {
            mc = fsm().create("TestInnerStateEntity", "istart2");
            FiniteStateGraph graph = mc.addState("istate21");
            graph.addParentTransition("TestStateEntity2", "state21");
            graph = mc.addEndState("istate22");
            graph.addParentTransition("TestStateEntity2", "state22");
        }

        return mc;
    }

    @Test
    public void testTestParent()
        throws Exception
    {
        FiniteStateMachine mc1 = createTSMStates();
        FiniteStateMachine mc = createTSM2States();
        FiniteStateMachine mc2 = createInnerTSM2States();

        TestStateEntity2 entity2 = new TestStateEntity2();
        TestStateEntity2.TestInnerStateEntity withpar = entity2.innerentity();
        assertTrue(withpar.utilities___currentState() != null);
        assertTrue(withpar.utilities___currentState().stateName().equals("istart2"));

        assertEntity2(entity2, "start2", "start");
        mc2.transition(withpar, "istate21");

        assertTrue(withpar.utilities___currentState() != null);
        assertTrue(withpar.utilities___currentState().stateName().equals("istate21"));
        assertEntity2(entity2, "state21", "state1");

        mc2.transition(withpar, "istate22");
        assertTrue(withpar.utilities___currentState() != null);
        assertTrue(withpar.utilities___currentState().stateName().equals("istate22"));
        assertEntity2(entity2, "state22", "state2");

        assertTrue(mc2.isDead(withpar));
        assertTrue(mc.isDead(entity2));
        StateEntity[] children = entity2.utilities___children("TestStateEntity");
        assertTrue(children != null);
        for (int i = 0; i < children.length; i++)
        {
            assertTrue(mc1.isDead(children[i]));
        }
    }
}

