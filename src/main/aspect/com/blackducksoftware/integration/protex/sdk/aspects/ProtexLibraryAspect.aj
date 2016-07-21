/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
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
 *******************************************************************************/
package com.blackducksoftware.integration.protex.sdk.aspects;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.xml.ws.WebServiceException;

import com.blackducksoftware.integration.protex.sdk.ProtexServerProxy;
import com.blackducksoftware.integration.protex.sdk.exceptions.ServerConnectionException;
import com.blackducksoftware.integration.suite.sdk.aspects.util.AspectUtility;
import com.blackducksoftware.integration.suite.sdk.logging.IntLogger;
import com.blackducksoftware.sdk.fault.SdkFault;

public aspect ProtexLibraryAspect {

    private static IntLogger logger = null;

    /**
     * if true, use set Thread Context ClassLoader to the Loader for ProtexProxyServer, before every call to the API.
     */
    private ClassLoader contextClassLoader = null;

    pointcut anyGetApiExecution(): execution(public * com.blackducksoftware.integration.protex.sdk.ProtexServerProxy.get*Api(..));

    pointcut anyApiMethodExecutionWithoutSdkFault(): execution(* com.blackducksoftware.sdk.protex..*Api+.*(..));

    pointcut anyApiMethodExecutionWithSdkFault(): execution(* com.blackducksoftware.sdk.protex..*Api+.*(..) throws SdkFault);

    Object around() : anyGetApiExecution() || anyApiMethodExecutionWithoutSdkFault() {
        Object result = null;
        if (contextClassLoader != null) {
            logger.trace("useContextClassloader ...");
            ClassLoader originalClassLoader = Thread.currentThread()
                    .getContextClassLoader();
            boolean changed = false;
            try {
                if (contextClassLoader != originalClassLoader) {
                    logger.trace("Adjusting the current ContextClassloader to the ClassLoader of : " + ProtexServerProxy.class.getSimpleName());
                    changed = true;
                    Thread.currentThread().setContextClassLoader(
                            ProtexServerProxy.class.getClassLoader());
                }
                result = proceed();
            } finally {
                if (changed) {
                    logger.trace("Changing the current ContextClassloader back to the original");
                    Thread.currentThread().setContextClassLoader(
                            originalClassLoader);
                }
            }
        } else {
            result = proceed();
        }
        return result;
    }

    Object around() : anyApiMethodExecutionWithoutSdkFault() {
        // FIXME get the log level of the logger (API change needed) to prevent the performance hit for calculating the
        // data that never gets logged
        long startNano = System.nanoTime();
        Object result = null;
        try {
            logger.trace("Executing method : " + thisJoinPointStaticPart.getSignature());
            Object[] arguments = thisJoinPoint.getArgs();
            if (arguments.length > 0) {
                String inputParameterString = "";
                inputParameterString = AspectUtility.inputArgumentsToString(arguments, logger);
                logger.trace("With input parameters : " + inputParameterString);
            }
            result = proceed();
        } catch (WebServiceException e) {
            if (e.getMessage().toLowerCase().contains("could not send message")) {
                logger.error(e);
                if (e.getCause() != null && e.getCause().getCause() != null) {
                    throw new ServerConnectionException(e.getCause().getCause().toString(), e);
                } else if (e.getCause() != null) {
                    throw new ServerConnectionException(e.getCause().getMessage(), e);
                } else {
                    throw new ServerConnectionException(e.getMessage(), e);
                }
            } else {
                logger.error(e);
                throw new ServerConnectionException(e.getMessage(), e);
            }
        } catch (MalformedURLException e) {
            logger.error("The provided Url was not valid : " + e.getMessage(), e);
            throw new ServerConnectionException("The provided Url was not valid : " + e.getMessage(), e);
        } catch (UnknownHostException e) {

            if (e.getMessage().toLowerCase().contains("could not send message")) {
                logger.error(e);
                if (e.getCause() != null) {
                    throw new ServerConnectionException(e.getCause().getMessage(), e);
                } else {
                    throw new ServerConnectionException(e.getMessage(), e);
                }
            } else {
                logger.error(e);
                throw new ServerConnectionException(e.getMessage(), e);
            }
        } catch (SocketTimeoutException e) {
            if (e.getMessage().toLowerCase().contains("could not send message")) {
                logger.error(e);
                if (e.getCause() != null) {
                    throw new ServerConnectionException(e.getCause().getMessage(), e);
                } else {
                    throw new ServerConnectionException(e.getMessage(), e);
                }
            } else {
                logger.error(e);
                throw new ServerConnectionException(e.getMessage(), e);
            }
            // FIXME catch and log any other exception happening?
        } finally {
            long endNano = System.nanoTime() - startNano;
            logger.trace("Execution time of method " + thisJoinPoint.getSignature() + " : " + endNano / 1000 + " \u03BCs");
        }
        return result;
    }

    Object around() throws SdkFault : anyApiMethodExecutionWithSdkFault(){
        
        long startNano = System.nanoTime();
        Object result = null;
        String inputParameterString = "";
        try {
            Object[] arguments = thisJoinPoint.getArgs();
            if (arguments.length > 0) {
                inputParameterString = AspectUtility.inputArgumentsToString(arguments, logger);
            }
            result = proceed();
        } catch (SdkFault e) {
            StringBuilder faultBuilder = new StringBuilder();
            faultBuilder.append("SDKFAULT {");
            faultBuilder.append("Method : " + thisJoinPointStaticPart.getSignature());
            faultBuilder.append(" :: ");
            faultBuilder.append("Input Parameters : " + inputParameterString);
            faultBuilder.append(" --> ");
            faultBuilder.append(e.getFaultInfo().getErrorCode());
            faultBuilder.append(" :: ");
            faultBuilder.append(e.getMessage());
            faultBuilder.append("}");

            logger.error(faultBuilder.toString());
            throw e;
        } catch (WebServiceException e) {
            if (e.getMessage().toLowerCase().contains("could not send message")) {
                logger.error(e);
                if (e.getCause() != null && e.getCause().getCause() != null) {
                    throw new ServerConnectionException(e.getCause().getCause().toString(), e);
                } else if (e.getCause() != null) {
                    throw new ServerConnectionException(e.getCause().getMessage(), e);
                } else {
                    throw new ServerConnectionException(e.getMessage(), e);
                }
            } else {
                logger.error(e);
                throw new ServerConnectionException(e.getMessage(), e);
            }
        } catch (MalformedURLException e) {
            logger.error("The provided Url was not valid : " + e.getMessage(), e);
            throw new ServerConnectionException("The provided Url was not valid : " + e.getMessage(), e);
        } catch (UnknownHostException e) {

            if (e.getMessage().toLowerCase().contains("could not send message")) {
                logger.error(e);
                if (e.getCause() != null) {
                    throw new ServerConnectionException(e.getCause().getMessage(), e);
                } else {
                    throw new ServerConnectionException(e.getMessage(), e);
                }
            } else {
                logger.error(e);
                throw new ServerConnectionException(e.getMessage(), e);
            }
        } catch (SocketTimeoutException e) {
            if (e.getMessage().toLowerCase().contains("could not send message")) {
                logger.error(e);
                if (e.getCause() != null) {
                    throw new ServerConnectionException(e.getCause().getMessage(), e);
                } else {
                    throw new ServerConnectionException(e.getMessage(), e);
                }
            } else {
                logger.error(e);
                throw new ServerConnectionException(e.getMessage(), e);
            }
            // FIXME catch and log any other exception happening?
        } finally {
            long endNano = System.nanoTime() - startNano;
            logger.trace("Execution time of method " + thisJoinPoint.getSignature() + " : " + endNano / 1000 + " \u03BCs");
        }
        return result;
    }

    /*
     * Capture the logger from the ServerProxy class
     */
    after(IntLogger l): execution(public void com.blackducksoftware.integration.protex.sdk.ProtexServerProxy.setLogger(IntLogger)) && args(l) {
        logger = l;
    }

    /*
     * Capture the useContextClassLoader flag from the ServerProxy class
     */
    after(ProtexServerProxy proxy, boolean flag): execution(public void com.blackducksoftware.integration.protex.sdk.ProtexServerProxy.setUseContextClassLoader(boolean)) && args(flag) && target(proxy) {
        if (flag) {
            contextClassLoader = proxy.getClass().getClassLoader();
        } else {
            contextClassLoader = null;
        }
    }

}
