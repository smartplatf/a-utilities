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
 * File:                org.anon.utilities.anatomy.AModule
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A module within an anatomy
 *
 * ************************************************************
 * */

package org.anon.utilities.anatomy;

import static org.anon.utilities.anatomy.Anatomy.*;
import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.exception.CtxException;

public abstract class AModule implements Repeatable
{
    private String _modName;
    private AModule _child;
    private StartConfig _config;

    protected AModule _parent;
    protected ModuleContext _context;

    protected AModule(AModule parent, ModuleContext ctx, boolean deflt)
        throws CtxException
    {
        _modName = nameFor(this.getClass());
        _context = ctx;
        setup();
        setParent(parent);
        if (parent != null)
            parent.setChild(this);
        addModule(this, deflt);
    }

    public String name() { return _modName; }
    public StartConfig moduleConfig() { return _config; }

    public void setParent(AModule mod) { _parent = mod; }
    public void setChild(AModule mod) { _child = mod; }
    protected AModule parent() { return _parent; }
    protected AModule child() { return _child; }

    protected void setup()
        throws CtxException
    {
    }

    public void start(StartConfig config)
        throws CtxException
    {
        _config = config;
    }

    public void stop()
        throws CtxException
    {
    }

    public Chore[] chores()
        throws CtxException
    {
        return new Chore[0];
    }

    public ModuleContext context() { return _context; }
    public ModuleContext parentContext() { if (_parent != null) return _parent._context; else return null; }

    public ModuleContext overriddenContext()
    {
        if ((_parent == null) || (_parent._context == null))
            return _context;

        return _parent.overriddenContext();
    }
}

