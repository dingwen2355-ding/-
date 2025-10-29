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

public class DigestRequest {

    /**
    * 摘要算法：SM3，默认为SM3
    */
    @SerializedName("Algorithm")
    @Expose
    private String Algorithm;

    /**
    * Base64编码的摘要原文，支持0-4096字节
    */
    @SerializedName("Message")
    @Expose
    private String Message;

    /**
     * 获取摘要算法：SM3，默认为SM3
     * @return Algorithm 摘要算法：SM3，默认为SM3
     */
    public String getAlgorithm() {
        return this.Algorithm;
    }

    /**
     * 设置摘要算法：SM3，默认为SM3
     * @param Algorithm 摘要算法：SM3，默认为SM3
     */
    public void setAlgorithm(String Algorithm) {
        this.Algorithm = Algorithm;
    }

    /**
     * 获取Base64编码的摘要原文，支持0-4096字节
     * @return Message Base64编码的摘要原文，支持0-4096字节
     */
    public String getMessage() {
        return this.Message;
    }

    /**
     * 设置Base64编码的摘要原文，支持0-4096字节
     * @param Message Base64编码的摘要原文，支持0-4096字节
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

}

