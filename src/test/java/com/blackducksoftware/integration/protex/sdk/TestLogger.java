/**
 * Suite SDK Integration library for Protex API
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.integration.protex.sdk;

import java.util.ArrayList;
import java.util.List;

import com.blackducksoftware.integration.suite.sdk.logging.IntLogger;
import com.blackducksoftware.integration.suite.sdk.logging.LogLevel;

public class TestLogger implements IntLogger {

    private static final String NL = System.getProperty("line.separator");

    private List<String> testOutput = new ArrayList<String>();

    private List<Throwable> testExceptions = new ArrayList<Throwable>();

    private LogLevel level = LogLevel.INFO;

    public void clear() {
        testOutput.clear();
        testExceptions.clear();
    }

    @Override
    public void info(String txt) {
        System.out.println(getLogLevel() + txt);
        if (isMinLevel(LogLevel.INFO)) {
            testOutput.add(txt);
        }
    }

    @Override
    public void error(String txt, Throwable e) {
        System.out.println(getLogLevelString(LogLevel.ERROR) + txt);
        System.out.println(getLogLevel() + "EXCEPTION : " + e.toString());
        if (isMinLevel(LogLevel.ERROR)) {
            testOutput.add(txt);
            testExceptions.add(e);
        }
    }

    @Override
    public void error(String txt) {
        System.out.println(getLogLevel() + txt);
        if (isMinLevel(LogLevel.ERROR)) {
            testOutput.add(txt);
        }
    }

    @Override
    public void warn(String txt) {
        System.out.println(getLogLevel() + txt);
        if (isMinLevel(LogLevel.WARN)) {
            testOutput.add(txt);
        }
    }

    @Override
    public void trace(String txt) {
        System.out.println(getLogLevel() + txt);
        if (isMinLevel(LogLevel.TRACE)) {
            testOutput.add(txt);
        }
    }

    @Override
    public void error(Throwable t) {
        if (t.getCause() != null) {
            System.out.println("EXCEPTION : " + t.getCause().toString());
        } else {
            System.out.println("EXCEPTION : " + t.toString());
        }
        if (isMinLevel(LogLevel.ERROR)) {
            testExceptions.add(t);
        }
    }

    @Override
    public void trace(String txt, Throwable t) {
        System.out.println(txt);
        System.out.println("EXCEPTION : " + t.toString());
        if (isMinLevel(LogLevel.TRACE)) {
            testOutput.add(txt);
            testExceptions.add(t);
        }
    }

    @Override
    public void debug(String txt) {
        System.out.println(getLogLevel() + txt);
        if (isMinLevel(LogLevel.DEBUG)) {
            testOutput.add(txt);
        }
    }

    @Override
    public void debug(String txt, Throwable t) {
        System.out.println(txt);
        System.out.println("EXCEPTION : " + t.toString());
        if (isMinLevel(LogLevel.DEBUG)) {
            testOutput.add(txt);
            testExceptions.add(t);
        }
    }

    public List<String> getTestOutput() {
        return testOutput;
    }

    public String printTestOutput() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String line : testOutput) {
            if (!first) {
                sb.append(NL);
            } else {
                first = false;
            }
            sb.append(line);
        }
        return sb.toString();
    }

    public List<Throwable> getTestExceptions() {
        return testExceptions;
    }

    @Override
    public void setLogLevel(LogLevel level) {
        this.level = level;
    }

    @Override
    public LogLevel getLogLevel() {
        return level;
    }

    private String getLogLevelString(LogLevel level) {
        return "[" + level.toString() + "] ";
    }

    private boolean isMinLevel(LogLevel thisLevel) {
        // System.err.println(".......... looking for " + thisLevel + " logger @ " + level + " ==> " +
        // LogLevel.isLoggable(level, thisLevel));
        return LogLevel.isLoggable(level, thisLevel);
    }

}
