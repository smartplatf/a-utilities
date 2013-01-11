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
 * File:                org.anon.utilities.config.YMLConfig
 * Author:              rsankar
 * Revision:            1.0
 * Date:                24-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A config in YML format
 *
 * ************************************************************
 * */

package org.anon.utilities.config;

import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.yaml.snakeyaml.Yaml;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;
import org.anon.utilities.exception.CtxException;

public class YMLConfig implements Format
{
    //TODO: possibly we need to change this to be a path instead of direct config
    protected Map _values;

    public YMLConfig()
    {
    }

    public YMLConfig(InputStream str)
        throws CtxException
    {
        try
        {
            if (str != null)
            {
                InputStreamReader reader = new InputStreamReader(str);
                Yaml yam = new Yaml();
                _values = (Map)yam.load(reader);
            }

        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("YML Read: ", e.getMessage()));
        }
    }

    public Object valueFor(String name)
        throws CtxException
    {
        return _values.get(name);
    }

    public String asString(String name)
        throws CtxException
    {
        if (_values.containsKey(name))
            return _values.get(name).toString();

        return null;
    }

    public int asInt(String name)
        throws CtxException
    {
        return -1;
    }

    public boolean containsName(String name)
        throws CtxException
    {
        return _values.containsKey(name);
    }

    public Map<String, Object> valueForStartsWith(String name)
        throws CtxException
    {
        Map<String, Object> ret = new HashMap<String, Object>();
        for (Object key : _values.keySet())
        {
            if (key.toString().startsWith(name))
                ret.put(key.toString(), _values.get(key));
        }

        return ret;
    }

    public Map allValues()
        throws CtxException
    {
        return _values;
    }

    public Repeatable repeatMe(RepeaterVariants parms)
        throws CtxException
    {
        ConfigRepeaterVariants vars = (ConfigRepeaterVariants)parms;
        return new YMLConfig(vars.configStream());
    }
}

