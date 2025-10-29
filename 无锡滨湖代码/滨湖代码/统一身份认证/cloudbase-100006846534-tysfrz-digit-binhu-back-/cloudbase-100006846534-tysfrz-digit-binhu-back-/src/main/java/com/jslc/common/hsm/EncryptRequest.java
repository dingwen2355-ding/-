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

public class EncryptRequest {

    /**
    * 调用CreateKey生成的CMK全局唯一标识符
    */
    @SerializedName("KeyId")
    @Expose
    private String KeyId;

    /**
    * 被加密的明文数据，该字段必须使用base64编码，原文最大长度支持4K
    */
    @SerializedName("Plaintext")
    @Expose
    private String Plaintext;

    /**
    * 如果为空根据CMK类型来判断，如果是SM4密钥，则使用 SM4_CBC_PKCS7PADDING， 如果是AES 密钥，则默认使用 AES_CBC_PKCS7PADDING 加密算法： SM4_ECB_NOPADDING SM4_ECB_PKCS7PADDING SM4_CBC_NOPADDING SM4_CBC_PKCS7PADDING SM4_CTR_NOPADDING SM4_CTR_PKCS7PADDING SM4_GCM_NOPADDING AES_ECB_NOPADDING AES_ECB_PKCS7PADDING AES_CBC_NOPADDING AES_CBC_PKCS7PADDING
    */
    @SerializedName("Algorithm")
    @Expose
    private String Algorithm;

    /**
     * 获取调用CreateKey生成的CMK全局唯一标识符
     * @return KeyId 调用CreateKey生成的CMK全局唯一标识符
     */
    public String getKeyId() {
        return this.KeyId;
    }

    /**
     * 设置调用CreateKey生成的CMK全局唯一标识符
     * @param KeyId 调用CreateKey生成的CMK全局唯一标识符
     */
    public void setKeyId(String KeyId) {
        this.KeyId = KeyId;
    }

    /**
     * 获取被加密的明文数据，该字段必须使用base64编码，原文最大长度支持4K
     * @return Plaintext 被加密的明文数据，该字段必须使用base64编码，原文最大长度支持4K
     */
    public String getPlaintext() {
        return this.Plaintext;
    }

    /**
     * 设置被加密的明文数据，该字段必须使用base64编码，原文最大长度支持4K
     * @param Plaintext 被加密的明文数据，该字段必须使用base64编码，原文最大长度支持4K
     */
    public void setPlaintext(String Plaintext) {
        this.Plaintext = Plaintext;
    }

    /**
     * 获取如果为空根据CMK类型来判断，如果是SM4密钥，则使用 SM4_CBC_PKCS7PADDING， 如果是AES 密钥，则默认使用 AES_CBC_PKCS7PADDING 加密算法： SM4_ECB_NOPADDING SM4_ECB_PKCS7PADDING SM4_CBC_NOPADDING SM4_CBC_PKCS7PADDING SM4_CTR_NOPADDING SM4_CTR_PKCS7PADDING SM4_GCM_NOPADDING AES_ECB_NOPADDING AES_ECB_PKCS7PADDING AES_CBC_NOPADDING AES_CBC_PKCS7PADDING
     * @return Algorithm 如果为空根据CMK类型来判断，如果是SM4密钥，则使用 SM4_CBC_PKCS7PADDING， 如果是AES 密钥，则默认使用 AES_CBC_PKCS7PADDING 加密算法： SM4_ECB_NOPADDING SM4_ECB_PKCS7PADDING SM4_CBC_NOPADDING SM4_CBC_PKCS7PADDING SM4_CTR_NOPADDING SM4_CTR_PKCS7PADDING SM4_GCM_NOPADDING AES_ECB_NOPADDING AES_ECB_PKCS7PADDING AES_CBC_NOPADDING AES_CBC_PKCS7PADDING
     */
    public String getAlgorithm() {
        return this.Algorithm;
    }

    /**
     * 设置如果为空根据CMK类型来判断，如果是SM4密钥，则使用 SM4_CBC_PKCS7PADDING， 如果是AES 密钥，则默认使用 AES_CBC_PKCS7PADDING 加密算法： SM4_ECB_NOPADDING SM4_ECB_PKCS7PADDING SM4_CBC_NOPADDING SM4_CBC_PKCS7PADDING SM4_CTR_NOPADDING SM4_CTR_PKCS7PADDING SM4_GCM_NOPADDING AES_ECB_NOPADDING AES_ECB_PKCS7PADDING AES_CBC_NOPADDING AES_CBC_PKCS7PADDING
     * @param Algorithm 如果为空根据CMK类型来判断，如果是SM4密钥，则使用 SM4_CBC_PKCS7PADDING， 如果是AES 密钥，则默认使用 AES_CBC_PKCS7PADDING 加密算法： SM4_ECB_NOPADDING SM4_ECB_PKCS7PADDING SM4_CBC_NOPADDING SM4_CBC_PKCS7PADDING SM4_CTR_NOPADDING SM4_CTR_PKCS7PADDING SM4_GCM_NOPADDING AES_ECB_NOPADDING AES_ECB_PKCS7PADDING AES_CBC_NOPADDING AES_CBC_PKCS7PADDING
     */
    public void setAlgorithm(String Algorithm) {
        this.Algorithm = Algorithm;
    }

}

