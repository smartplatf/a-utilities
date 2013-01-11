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
 * File:                org.anon.utilities.test.fsm.TestStateEntity2
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A testing state entity 2
 *
 * ************************************************************
 * */

package org.anon.utilities.test.fsm;

import java.util.List;
import java.util.ArrayList;


import org.anon.utilities.fsm.StateEntity;
import org.anon.utilities.exception.CtxException;

public class TestStateEntity2 extends BaseStateEntity
{
    public class TestInnerStateEntity extends BaseStateEntity
    {
        private TestStateEntity2 _parent;
        public TestInnerStateEntity(TestStateEntity2 ent)
            throws CtxException
        {
            super();
            _parent = ent;
        }

        protected void initStateEntityType()
        {
            _stateEntityType = "TestInnerStateEntity";
        }

        public StateEntity parent()
            throws CtxException
        {
            return _parent;
        }

        public StateEntity[] children(String setype)
            throws CtxException
        {
            return null;
        }
    }

    private TestInnerStateEntity _entity;
    private List<TestStateEntity> _children;

    public TestStateEntity2()
        throws CtxException
    {
        super();
        _entity = new TestInnerStateEntity(this);
        _children = new ArrayList<TestStateEntity>();
        for (int i = 0; i < 10; i++)
            _children.add(new TestStateEntity());
    }

    protected void initStateEntityType()
    {
        _stateEntityType = "TestStateEntity2";
    }

    public TestInnerStateEntity innerentity() { return _entity; }

    public StateEntity parent()
        throws CtxException
    {
        return null;
    }

    public StateEntity[] children(String setype)
        throws CtxException
    {
        if (setype.equals("TestStateEntity"))
            return _children.toArray(new StateEntity[0]);

        return null;
    }
}

