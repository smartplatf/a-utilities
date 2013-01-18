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
 * File:                org.anon.utilities.serialize.kryo.UUIDSerializer
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A serializer for UUIDs
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.kryo;

import java.util.UUID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.Input;

public class UUIDSerializer extends Serializer<UUID>
{
    public UUIDSerializer()
    {
    }

    @Override
    public void write(Kryo paramKryo, Output paramOutput, UUID paramT)
    {
        UUID id = (UUID) paramT;
        paramOutput.writeString(id.toString());
    }

    @Override
    public UUID read(Kryo paramKryo, Input paramInput, Class<UUID> paramClass)
    {
        String s = paramInput.readString();
        UUID id = UUID.fromString(s);
        return id;
    }
}

