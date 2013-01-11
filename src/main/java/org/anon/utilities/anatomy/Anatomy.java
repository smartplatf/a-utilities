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
 * File:                org.anon.utilities.anatomy.Anatomy
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An anatomy of a given application
 *
 * ************************************************************
 * */

package org.anon.utilities.anatomy;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.anon.utilities.exception.CtxException;

public class Anatomy
{
    private static final Anatomy ANATOMY = new Anatomy(new DefaultNaming());
    private static final String DEFAULT_MOD = "DEFAULTMOD";

    private Map<String, AModule> _structure;
    private ModuleNaming _naming;

    private Anatomy(ModuleNaming naming)
    {
        _structure = new HashMap<String, AModule>();
        _naming = naming;
    }

    private void setupNaming(ModuleNaming naming)
    {
        _naming = naming;
    }

    private ModuleNaming naming()
    {
        return _naming;
    }

    private void addModule(AModule mod)
    {
        if (!_structure.containsKey(mod.name()))
            _structure.put(mod.name(), mod);
    }

    public void addDefault(AModule mod)
    {
        if (!_structure.containsKey(DEFAULT_MOD))
            _structure.put(DEFAULT_MOD, mod);
    }

    public AModule rootModule()
    {
        AModule root = null;
        for (AModule mod : _structure.values())
        {
            if (mod.parent() == null)
            {
                root = mod;
                break;
            }
        }
        return root;
    }

    public AModule module(Class cls)
        throws CtxException
    {
        String name = _naming.nameFor(cls);
        AModule ret = _structure.get(name);
        if (ret == null)
            ret = _structure.get(DEFAULT_MOD);
        return ret;
    }

    public AModule module(String name)
    {
        return _structure.get(name);
    }

    public StartConfig configOf(String name)
    {
        AModule mod = module(name);
        if (mod != null)
            return mod.moduleConfig();
        return null;
    }

    public ModuleContext[] context()
    {
        ModuleContext[] sctx = new ModuleContext[_structure.size()];
        int cnt = 0;
        AModule root = rootModule();

        AModule mod = root;
        if (mod != null)
        {
            sctx[cnt] = mod.context();
            cnt++;

            while (mod.child() != null)
            {
                mod = mod.child();
                sctx[cnt] = mod.context();
                cnt++;
            }
        }

        return sctx;
    }

    public ModuleContext rootModule(ModuleFilter filter)
        throws CtxException
    {
        ModuleContext ret = null;
        AModule root = rootModule();

        AModule mod = root;
        if (mod != null)
        {
            ret = filter.meets(mod.context());

            while ((ret == null) && (mod.child() != null))
            {
                mod = mod.child();
                ret = filter.meets(mod.context());
            }
        }

        return ret;
    }

    public void start(StartConfig config)
        throws CtxException
    {
        AModule mod = rootModule();
        if (mod != null)
        {
            mod.start(config);
            while (mod.child() != null)
            {
                mod = mod.child();
                mod.start(config);
            }
        }
    }

    public void stop()
        throws CtxException
    {
        AModule mod = rootModule();
        if (mod != null)
        {
            mod.stop();
            while (mod.child() != null)
            {
                mod = mod.child();
                mod.stop();
            }
        }
    }

    public AModule[] myModules()
        throws CtxException
    {
        List<AModule> mods = new ArrayList<AModule>();
        AModule mod = rootModule();
        if (mod != null)
            mods.add(mod);
        while ((mod != null) && (mod.child() != null))
        {
            mod = mod.child();
            mods.add(mod);
        }

        return mods.toArray(new AModule[0]);
    }

    static void addModule(AModule mod, boolean deflt)
    {
        ANATOMY.addModule(mod);
        if (deflt)
            ANATOMY.addDefault(mod);
    }

    public static Anatomy myanatomy() { return ANATOMY; }

    static String nameFor(Class cls)
        throws CtxException
    {
        return ANATOMY.naming().nameFor(cls);
    }

}

