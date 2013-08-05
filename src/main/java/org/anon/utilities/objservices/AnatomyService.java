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
 * File:                org.anon.utilities.objservices.AnatomyService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A anatomy related service
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import static org.anon.utilities.anatomy.Anatomy.*;

import org.anon.utilities.anatomy.AModule;
import org.anon.utilities.anatomy.StartConfig;
import org.anon.utilities.anatomy.ModuleFilter;
import org.anon.utilities.anatomy.ModuleContext;
import org.anon.utilities.anatomy.JVMEnvironment;
import org.anon.utilities.anatomy.SingleJVMEnvironment;
import org.anon.utilities.exception.CtxException;

public class AnatomyService extends ObjectServiceLocator.ObjectService
{
    class VMEnvFilter implements ModuleFilter
    {
        public ModuleContext meets(ModuleContext ctx)
            throws CtxException
        {
            if (ctx.vmEnvironment() != null)
                return ctx;

            return null;
        }
    }

    private VMEnvFilter _envFilter;

    public AnatomyService()
    {
        super();
        _envFilter = new VMEnvFilter();
    }

    public ModuleContext context(Class cls)
        throws CtxException
    {
        AModule mod = myanatomy().module(cls);
        if (mod == null)
            mod = myanatomy().rootModule();

        if (mod == null)
            return null;

        return mod.context();
    }

    public ModuleContext overriddenContext(Class cls)
        throws CtxException
    {
        AModule mod = myanatomy().module(cls);
        if (mod == null)
            mod = myanatomy().rootModule();

        if (mod == null)
            return null;

        return mod.overriddenContext();
    }

    public ModuleContext[] structureContext()
        throws CtxException
    {
        return myanatomy().context();
    }

    public void startup(StartConfig config)
        throws CtxException
    {
        myanatomy().start(config);
    }

    public void startup(StartConfig config, String[] order)
        throws CtxException
    {
        myanatomy().start(config, order);
    }

    public void shutDown()
        throws CtxException
    {
        myanatomy().stop();
    }

    public ModuleContext filteredModule(ModuleFilter filter)
        throws CtxException
    {
        return myanatomy().rootModule(filter);
    }

    public AModule moduleOf(String name)
    {
        return myanatomy().module(name);
    }

    public AModule[] allModules()
        throws CtxException
    {
        return myanatomy().myModules();
    }

    public StartConfig configFor(String name)
    {
        return myanatomy().configOf(name);
    }


    public JVMEnvironment jvmEnv()
        throws CtxException
    {
        ModuleContext ctx = filteredModule(_envFilter);
        if (ctx != null)
            return ctx.vmEnvironment();

        return new SingleJVMEnvironment();
    }
}

