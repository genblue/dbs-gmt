package com.save.mangrove;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.*;

@Component
public class KeyStoreManager {

    public void addToKeyStore(byte[] priKey, String instance, String algo, String KeyStorePwd, String entryPwd, String keyStorePath, String alias) throws Exception {
        /**
         * Keystore is a storage facility to store cryptographic keys and certificates.
         * The three types of entries are:
         * PrivateKey : This is a type of keys which are used in asymmetric cryptography. It is usually protected with password because of its sensitivity. It can also be used to sign a digital signature.
         * Certificate : A certificate contains a public key which can identify the subject claimed in the certificate. It is usually used to verify the identity of a server. Sometimes it is also used to identify a client when requested.
         * SecretKey : A key entry which is sued in symmetric cryptography.
         *
         * KEYSTORE TYPE : JKS, PKCS12 etc.
         * JKS, Java Key Store. You can find this file at
         * sun.security.provider.JavaKeyStore. This keystore is Java specific,
         * it usually has an extension of jks. This type of keystore can contain
         * private keys and certificates, but it cannot be used to store secret keys.
         * Since it's a Java specific keystore, so it cannot be used in other
         * programming languages. The private keys stored in JKS cannot be
         * extracted in Java.
         *
         *
         * Obtain the Keystore instance of specified
         * PKCS12, this is a standard keystore type which can be used in Java
         * and other languages. You can find this keystore implementation at
         * sun.security.pkcs12.PKCS12KeyStore. It usually has an extension of
         * p12 or pfx. You can store private keys, secret keys and certificates on
         * this type. Unlike JKS, the private keys on PKCS12 keystore can be
         * extracted in Java.
         */

//      KeyStore keystore = KeyStore.getInstance(keyStoreType);
        KeyStore keyStore = KeyStore.getInstance(instance);
        keyStore.load(null, null); // Initialize a blank keystore
//      SecretKey key = new SecretKeySpec(new byte[32], "RSA");
        SecretKey key = new SecretKeySpec(priKey, algo);
        byte[] salt = new byte[20];
        /**you hash data+salt, where salt is typically a randomly-generated string.
         * They have (at least) two purposes: To foil an attacker who has access to the
         * hashed data from identifying a collision using a rainbow table
         *
         */

        /**provide an alias name of your key
         * 2 passwords , 1 on keystore and other on each entry we can put
         * Default pwd is changeit of keystore.
         *
         */
        new SecureRandom().nextBytes(salt);
        keyStore.setEntry(alias, new KeyStore.SecretKeyEntry(key),
                new KeyStore.PasswordProtection(KeyStorePwd.toCharArray(),
                        "PBEWithHmacSHA512AndAES_128",
                        new PBEParameterSpec(salt, 100_000)));
        keyStore.store(new FileOutputStream(keyStorePath), entryPwd.toCharArray());
    }

    public Key retrieveKeyFromKeyStore(String instance, String keyStorePwd, String entryPwd, String keyStorePath, String alias) throws Exception {
//        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        KeyStore keystore = KeyStore.getInstance(instance);

//        Base64.Encoder encoder = new Base64.Encoder();
//        keystore.load(new FileInputStream(keystoreFile), keyStorePassword);


        keystore.load(new FileInputStream(keyStorePath), keyStorePwd.toCharArray());
//        Key key = keystore.getKey(alias, keyPassword);
        Key key = keystore.getKey(alias, entryPwd.toCharArray());
        return key;
    }
}
