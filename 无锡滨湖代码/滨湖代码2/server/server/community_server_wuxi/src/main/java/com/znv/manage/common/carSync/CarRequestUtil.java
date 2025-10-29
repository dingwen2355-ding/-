package com.znv.manage.common.carSync;
/*
 * Copyright 2017 Alibaba Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.cloudapi.sdk.model.ApiResponse;

import java.io.UnsupportedEncodingException;

/**
 * @author zhongfu.xiezf
 */
public class CarRequestUtil {
    private CarRequestUtil(){}

    public static String postRequest(String host, String path, IoTApiRequest request, IoTApiClientBuilderParams params) throws UnsupportedEncodingException {
        SyncApiClient syncClient = new SyncApiClient(params);
        request.setApiVer("1.0.0");
        request.setId("42423423");
        ApiResponse response = syncClient.postBody(host, path, request);
        return new String(response.getBody(),
                "utf-8");
    }

}

