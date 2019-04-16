package com.save.mangrove;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public interface KeyGenerator {

    void applyAlgorithm() throws Exception;

    void writeKeys(String path, byte[] key, String userId) throws Exception;

    SecretKey readKey(String userId) throws Exception;

    PublicKey getPublicKey();

    PrivateKey getPrivateKey();

    boolean isSignatureVerified(PublicKey publicKey, byte[] digitalSignature, byte[] data) throws Exception;

    byte[] signDocument(PrivateKey pvt, byte[] data) throws Exception;

}
