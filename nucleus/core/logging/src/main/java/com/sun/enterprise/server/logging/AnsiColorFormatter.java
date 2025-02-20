/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017-2021 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
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
package com.sun.enterprise.server.logging;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * @since 4.1.1.173
 * @author Steve Millidge (Payara Foundation)
 */
public abstract class AnsiColorFormatter extends CommonFormatter {
    
    private boolean ansiColor;
    private HashMap<Level,AnsiColor> colors;
    private AnsiColor loggerColor;
    
    public AnsiColorFormatter(String excludeFields) {
        super(excludeFields);
        LogManager manager = LogManager.getLogManager();
        String color = manager.getProperty(this.getClass().getCanonicalName() + ".ansiColor");
        if ("true".equals(color)) {
            ansiColor = true;
        }
        colors = new HashMap<>();
        colors.put(Level.INFO, AnsiColor.BOLD_INTENSE_GREEN);
        colors.put(Level.WARNING, AnsiColor.BOLD_INTENSE_YELLOW);
        colors.put(Level.SEVERE, AnsiColor.BOLD_INTENSE_RED);
        loggerColor = AnsiColor.BOLD_INTENSE_BLUE;
        String infoColor = manager.getProperty(this.getClass().getCanonicalName()+".infoColor");
        if (infoColor != null) {
            try {
                colors.put(Level.INFO, AnsiColor.valueOf(infoColor));
            }catch (IllegalArgumentException iae) {
                colors.put(Level.INFO, AnsiColor.BOLD_INTENSE_GREEN);
            }
        }
        String colorProp = manager.getProperty(this.getClass().getCanonicalName()+".warnColor");
        if (colorProp != null) {
            try {
                colors.put(Level.WARNING, AnsiColor.valueOf(colorProp));
            }catch (IllegalArgumentException iae) {
                colors.put(Level.WARNING, AnsiColor.BOLD_INTENSE_YELLOW);
            }
        }
        colorProp = manager.getProperty(this.getClass().getCanonicalName()+".severeColor");
        if (colorProp != null) {
            try {
                colors.put(Level.SEVERE, AnsiColor.valueOf(colorProp));
            }catch (IllegalArgumentException iae) {
                colors.put(Level.SEVERE, AnsiColor.BOLD_INTENSE_RED);
            }
        }
        
        colorProp = manager.getProperty(this.getClass().getCanonicalName()+".loggerColor");
        if (colorProp != null) {
            try {
                loggerColor = AnsiColor.valueOf(colorProp);
            }catch (IllegalArgumentException iae) {
                loggerColor = AnsiColor.BOLD_INTENSE_BLUE;
            }
        }
        
    }

    public AnsiColor getLoggerColor() {
        return loggerColor;
    }
    
    
    
    protected boolean color() {
        return ansiColor;
    }
    
    public void noAnsi(){
        ansiColor = false;
    }
    
    protected AnsiColor getColor(Level level) {
        AnsiColor result = colors.get(level);
        if (result == null) {
            result = AnsiColor.NOTHING;
        }
        return result;
    }
    
    protected AnsiColor getReset() {
        return AnsiColor.RESET;
    }
    
}
