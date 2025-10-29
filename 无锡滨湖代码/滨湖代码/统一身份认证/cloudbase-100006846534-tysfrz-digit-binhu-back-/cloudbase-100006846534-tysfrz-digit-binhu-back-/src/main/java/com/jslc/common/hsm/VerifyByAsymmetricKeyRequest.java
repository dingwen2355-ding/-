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

public class VerifyByAsymmetricKeyRequest {

    /**
    * 密钥的唯一标识
    */
    @SerializedName("KeyId")
    @Expose
    private String KeyId;

    /**
    * 签名值，通过调用KMS签名接口生成
    */
    @SerializedName("SignatureValue")
    @Expose
    private String SignatureValue;

    /**
    * 消息原文或消息摘要。如果提供的是消息原文，则消息原文的长度（Base64编码前的长度）不超过4096字节。如果提供的是消息摘要，则消息摘要长度（Base64编码前的长度）必须等于32字节
    */
    @SerializedName("Message")
    @Expose
    private String Message;

    /**
    * 签名算法，支持的算法：SM2DSA，ECC_P256_R1，RSA_PSS_SHA_256，RSA_PKCS1_SHA_256
    */
    @SerializedName("Algorithm")
    @Expose
    private String Algorithm;

    /**
    * 消息类型：RAW，DIGEST，如果不传，默认为RAW，表示消息原文。
    */
    @SerializedName("MessageType")
    @Expose
    private String MessageType;

    /**
     * 获取密钥的唯一标识
     * @return KeyId 密钥的唯一标识
     */
    public String getKeyId() {
        return this.KeyId;
    }

    /**
     * 设置密钥的唯一标识
     * @param KeyId 密钥的唯一标识
     */
    public void setKeyId(String KeyId) {
        this.KeyId = KeyId;
    }

    /**
     * 获取签名值，通过调用KMS签名接口生成
     * @return SignatureValue 签名值，通过调用KMS签名接口生成
     */
    public String getSignatureValue() {
        return this.SignatureValue;
    }

    /**
     * 设置签名值，通过调用KMS签名接口生成
     * @param SignatureValue 签名值，通过调用KMS签名接口生成
     */
    public void setSignatureValue(String SignatureValue) {
        this.SignatureValue = SignatureValue;
    }

    /**
     * 获取消息原文或消息摘要。如果提供的是消息原文，则消息原文的长度（Base64编码前的长度）不超过4096字节。如果提供的是消息摘要，则消息摘要长度（Base64编码前的长度）必须等于32字节
     * @return Message 消息原文或消息摘要。如果提供的是消息原文，则消息原文的长度（Base64编码前的长度）不超过4096字节。如果提供的是消息摘要，则消息摘要长度（Base64编码前的长度）必须等于32字节
     */
    public String getMessage() {
        return this.Message;
    }

    /**
     * 设置消息原文或消息摘要。如果提供的是消息原文，则消息原文的长度（Base64编码前的长度）不超过4096字节。如果提供的是消息摘要，则消息摘要长度（Base64编码前的长度）必须等于32字节
     * @param Message 消息原文或消息摘要。如果提供的是消息原文，则消息原文的长度（Base64编码前的长度）不超过4096字节。如果提供的是消息摘要，则消息摘要长度（Base64编码前的长度）必须等于32字节
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * 获取签名算法，支持的算法：SM2DSA，ECC_P256_R1，RSA_PSS_SHA_256，RSA_PKCS1_SHA_256
     * @return Algorithm 签名算法，支持的算法：SM2DSA，ECC_P256_R1，RSA_PSS_SHA_256，RSA_PKCS1_SHA_256
     */
    public String getAlgorithm() {
        return this.Algorithm;
    }

    /**
     * 设置签名算法，支持的算法：SM2DSA，ECC_P256_R1，RSA_PSS_SHA_256，RSA_PKCS1_SHA_256
     * @param Algorithm 签名算法，支持的算法：SM2DSA，ECC_P256_R1，RSA_PSS_SHA_256，RSA_PKCS1_SHA_256
     */
    public void setAlgorithm(String Algorithm) {
        this.Algorithm = Algorithm;
    }

    /**
     * 获取消息类型：RAW，DIGEST，如果不传，默认为RAW，表示消息原文。
     * @return MessageType 消息类型：RAW，DIGEST，如果不传，默认为RAW，表示消息原文。
     */
    public String getMessageType() {
        return this.MessageType;
    }

    /**
     * 设置消息类型：RAW，DIGEST，如果不传，默认为RAW，表示消息原文。
     * @param MessageType 消息类型：RAW，DIGEST，如果不传，默认为RAW，表示消息原文。
     */
    public void setMessageType(String MessageType) {
        this.MessageType = MessageType;
    }


}

