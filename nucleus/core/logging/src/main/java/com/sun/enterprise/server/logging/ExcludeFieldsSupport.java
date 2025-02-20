/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

// Portions Copyright [2021] [Payara Foundation and/or its affiliates]
package com.sun.enterprise.server.logging;

import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sanshriv
 *
 */
public class ExcludeFieldsSupport {

    private static final Logger LOG = LogFacade.LOGGING_LOGGER;

    public enum SupplementalAttribute {TID, USERID, ECID, TIME_MILLIS, LEVEL_VALUE, VERSION}

    private final BitSet excludeSuppAttrsBits = new BitSet();

    public ExcludeFieldsSupport(String excludeFields) {
        setExcludeFields(excludeFields);
    }

    private void setExcludeFields(String excludeFields) {
        excludeSuppAttrsBits.clear();
        if (excludeFields != null) {
            String[] fields = excludeFields.split(",");
            for (String field : fields) {
                switch (field) {
                    case "tid":
                        excludeSuppAttrsBits.set(SupplementalAttribute.TID.ordinal());
                        break;
                    case "userId":
                        excludeSuppAttrsBits.set(SupplementalAttribute.USERID.ordinal());
                        break;
                    case "ecid":
                        excludeSuppAttrsBits.set(SupplementalAttribute.ECID.ordinal());
                        break;
                    case "timeMillis":
                        excludeSuppAttrsBits.set(SupplementalAttribute.TIME_MILLIS.ordinal());
                        break;
                    case "levelValue":
                        excludeSuppAttrsBits.set(SupplementalAttribute.LEVEL_VALUE.ordinal());
                        break;
                    case "version":
                        excludeSuppAttrsBits.set(SupplementalAttribute.VERSION.ordinal());
                        break;
                    default:
                        LOG.log(Level.WARNING, String.format("Unknown Exclude Field provided : '%s'", field));
                }
            }
        } 
    }
    
    public boolean isSet(SupplementalAttribute attr) {
        return excludeSuppAttrsBits.get(attr.ordinal());
    }

}
