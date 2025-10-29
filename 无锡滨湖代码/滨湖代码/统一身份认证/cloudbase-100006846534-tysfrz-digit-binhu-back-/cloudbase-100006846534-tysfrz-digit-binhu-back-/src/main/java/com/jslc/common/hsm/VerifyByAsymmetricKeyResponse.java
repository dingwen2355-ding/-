/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jslc.common.hsm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyByAsymmetricKeyResponse {

    /**
    * 签名是否有效。true：签名有效，false：签名无效。
    */
    @SerializedName("SignatureValid")
    @Expose
    private Boolean SignatureValid;

    /**
    * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
    */
    @SerializedName("RequestId")
    @Expose
    private String RequestId;

    /**
     * 获取签名是否有效。true：签名有效，false：签名无效。
     * @return SignatureValid 签名是否有效。true：签名有效，false：签名无效。
     */
    public Boolean getSignatureValid() {
        return this.SignatureValid;
    }

    /**
     * 设置签名是否有效。true：签名有效，false：签名无效。
     * @param SignatureValid 签名是否有效。true：签名有效，false：签名无效。
     */
    public void setSignatureValid(Boolean SignatureValid) {
        this.SignatureValid = SignatureValid;
    }

    /**
     * 获取唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @return RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public String getRequestId() {
        return this.RequestId;
    }

    /**
     * 设置唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @param RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

}

