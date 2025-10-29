package com.jslc.common.hsm;
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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DecryptRequest{

    /**
    * 待解密的密文数据
    */
    @SerializedName("CiphertextBlob")
    @Expose
    private String CiphertextBlob;

    /**
    * 用户提供的用于加密的pem格式公钥字符串
    */
    @SerializedName("EncryptionPublicKey")
    @Expose
    private String EncryptionPublicKey;

    /**
    * 可选，默认 SM2，允许值 SM2, SM2_C1C3C2_ASN1(返回SM2C1C3C2 ASN1格式密文), SM2_XY（国密X||Y格式公钥）， RSAES_PKCS1_V1_5、RSAES_OAEP_SHA_1、RSAES_OAEP_SHA_256
    */
    @SerializedName("EncryptionAlgorithm")
    @Expose
    private String EncryptionAlgorithm;

    /**
     * 获取待解密的密文数据
     * @return CiphertextBlob 待解密的密文数据
     */
    public String getCiphertextBlob() {
        return this.CiphertextBlob;
    }

    /**
     * 设置待解密的密文数据
     * @param CiphertextBlob 待解密的密文数据
     */
    public void setCiphertextBlob(String CiphertextBlob) {
        this.CiphertextBlob = CiphertextBlob;
    }

    /**
     * 获取用户提供的用于加密的pem格式公钥字符串
     * @return EncryptionPublicKey 用户提供的用于加密的pem格式公钥字符串
     */
    public String getEncryptionPublicKey() {
        return this.EncryptionPublicKey;
    }

    /**
     * 设置用户提供的用于加密的pem格式公钥字符串
     * @param EncryptionPublicKey 用户提供的用于加密的pem格式公钥字符串
     */
    public void setEncryptionPublicKey(String EncryptionPublicKey) {
        this.EncryptionPublicKey = EncryptionPublicKey;
    }

    /**
     * 获取可选，默认 SM2，允许值 SM2, SM2_C1C3C2_ASN1(返回SM2C1C3C2 ASN1格式密文), SM2_XY（国密X||Y格式公钥）， RSAES_PKCS1_V1_5、RSAES_OAEP_SHA_1、RSAES_OAEP_SHA_256
     * @return EncryptionAlgorithm 可选，默认 SM2，允许值 SM2, SM2_C1C3C2_ASN1(返回SM2C1C3C2 ASN1格式密文), SM2_XY（国密X||Y格式公钥）， RSAES_PKCS1_V1_5、RSAES_OAEP_SHA_1、RSAES_OAEP_SHA_256
     */
    public String getEncryptionAlgorithm() {
        return this.EncryptionAlgorithm;
    }

    /**
     * 设置可选，默认 SM2，允许值 SM2, SM2_C1C3C2_ASN1(返回SM2C1C3C2 ASN1格式密文), SM2_XY（国密X||Y格式公钥）， RSAES_PKCS1_V1_5、RSAES_OAEP_SHA_1、RSAES_OAEP_SHA_256
     * @param EncryptionAlgorithm 可选，默认 SM2，允许值 SM2, SM2_C1C3C2_ASN1(返回SM2C1C3C2 ASN1格式密文), SM2_XY（国密X||Y格式公钥）， RSAES_PKCS1_V1_5、RSAES_OAEP_SHA_1、RSAES_OAEP_SHA_256
     */
    public void setEncryptionAlgorithm(String EncryptionAlgorithm) {
        this.EncryptionAlgorithm = EncryptionAlgorithm;
    }

}

