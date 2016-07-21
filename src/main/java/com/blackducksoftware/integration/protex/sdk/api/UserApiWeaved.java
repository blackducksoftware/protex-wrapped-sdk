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
package com.blackducksoftware.integration.protex.sdk.api;

import java.util.Date;
import java.util.List;

import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.role.Role;
import com.blackducksoftware.sdk.protex.user.User;
import com.blackducksoftware.sdk.protex.user.UserApi;
import com.blackducksoftware.sdk.protex.user.UserIdType;
import com.blackducksoftware.sdk.protex.user.UserPageFilter;
import com.blackducksoftware.sdk.protex.user.UserRequest;

public class UserApiWeaved extends WeavedApi<UserApi> implements UserApi {

    public UserApiWeaved(UserApi api) {
        super(api);
    }

    @Override
    public void addUserRoles(String userId, List<Role> roles)
            throws SdkFault {
        getApi().addUserRoles(userId, roles);

    }

    @Override
    public void deleteUser(String userId) throws SdkFault {
        getApi().deleteUser(userId);

    }

    @Override
    public Boolean getCurrentUserHasServerFileAccess() throws SdkFault {
        return getApi().getCurrentUserHasServerFileAccess();
    }

    @Override
    public String getUserAccountsUrl() throws SdkFault {
        return getApi().getUserAccountsUrl();
    }

    @Override
    public User getUserByEmail(String eMail) throws SdkFault {
        return getApi().getUserByEmail(eMail);
    }

    @Override
    public List<Role> getUserRoles(String userId) throws SdkFault {
        return getApi().getUserRoles(userId);
    }

    @Override
    public List<User> getUsers(UserPageFilter pageFilter) throws SdkFault {
        return getApi().getUsers(pageFilter);
    }

    @Override
    public void removeUserRoles(String userId, List<Role> roles)
            throws SdkFault {
        getApi().removeUserRoles(userId, roles);
    }

    @Override
    public String createUser(UserRequest arg0, String arg1) throws SdkFault {
        return getApi().createUser(arg0, arg1);
    }

    @Override
    public Date getLastSignIn(String arg0) throws SdkFault {
        return getApi().getLastSignIn(arg0);
    }

    @Override
    public List<String> getTags(String arg0) throws SdkFault {
        return getApi().getTags(arg0);
    }

    @Override
    public User getUserById(String arg0, UserIdType arg1) throws SdkFault {
        return getApi().getUserById(arg0, arg1);
    }

    @Override
    public void updateUser(String arg0, UserRequest arg1, String arg2) throws SdkFault {
        getApi().updateUser(arg0, arg1, arg2);
    }

}
