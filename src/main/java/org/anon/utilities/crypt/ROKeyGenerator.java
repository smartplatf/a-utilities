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
 * File:                org.anon.utilities.crypt.ROKeyGenerator
 * Author:              rsankar
 * Revision:            1.0
 * Date:                31-05-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A key generation that works for related object loaders
 *
 * ************************************************************
 * */

package org.anon.utilities.crypt;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory;

import org.anon.utilities.loader.RelatedUtils;
import org.anon.utilities.loader.CrossLinkRelatedObject;
import org.anon.utilities.exception.CtxException;

import static org.anon.utilities.services.ServiceLocator.*;

public class ROKeyGenerator implements KeyGenerator, Constants
{
    public ROKeyGenerator()
    {
    }

    public byte[] keyFor(String password)
        throws CtxException
    {
        try
        {
            CrossLinkRelatedObject ro = RelatedUtils.getCLRelatedObject(this);
            String salt = "standard";
            if (ro != null)
                salt = ro.getName();
            PBEKeySpec pbe = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory kfact = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKeySpec sKey = new SecretKeySpec(kfact.generateSecret(pbe).getEncoded(), ENCRYPTION);
            byte[] key = sKey.getEncoded();
            return key;
            /*
            else
            {
                except().te(this, "Do not use this generator for non-related object classloaders.");
            }
            */
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Error", e.getMessage()));
        }

        return null;
    }
}

