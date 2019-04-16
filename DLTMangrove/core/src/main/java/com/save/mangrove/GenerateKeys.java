package com.save.mangrove;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class GenerateKeys implements KeyGenerator {

    @Autowired
    KeyStoreManager keyStore;

    @Value("${KEYSTORE_ALGO}")
    private String ALGO;
    @Value("${KEYSTORE_INSTANCE}")
    private String INSTANCE;
    @Value("${KEYSTORE_PWD}")
    private String KSPWD;
    @Value("${ENTRY_PWD}")
    private String ENTRYPWD;
    @Value("${KEYSTORE_LOCATION}")
    private String KSLOCATION;
    private KeyPairGenerator keyGen;
    private KeyPair pair;
    @Getter
    private PrivateKey privateKey;
    @Getter
    private PublicKey publicKey;
    private int keyLength = 1024;


    public void applyAlgorithm() throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance(ALGO);
        this.keyGen.initialize(keyLength);
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public void writeKeys(String path, byte[] key, String userId) throws Exception {
        keyStore.addToKeyStore(key, INSTANCE, ALGO, KSPWD, ENTRYPWD, KSLOCATION, userId);
        //addToKeyStore(byte[] priKey, String instance, String algo, String KeyStorePwd, String entryPwd, String keyStorePath)
        File f = new File(path);
        f.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public SecretKey readKey(String userId) throws Exception {
        Key key = keyStore.retrieveKeyFromKeyStore(INSTANCE, KSPWD, ENTRYPWD, KSLOCATION, userId);
        return key!=null?(SecretKey) key:null;
    }

    /***
     * checks whether a digital Signature is valid or not by matching it with the
     * pair of signed public key
     * @param publicKey
     * @param digitalSignature
     * @return
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public boolean isSignatureVerified(PublicKey publicKey, byte[] digitalSignature, byte[] data) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(publicKey);
        sign.update(data);
        byte[] signatureBytes = Base64.getDecoder().decode(digitalSignature);

        return sign.verify(signatureBytes);

//        Signature publicSignature = Signature.getInstance("SHA256withRSA");
//        publicSignature.initVerify(publicKey);
//        publicSignature.update(plainText.getBytes(UTF_8));
//
//        byte[] signatureBytes = Base64.getDecoder().decode(signature);
//
//        return publicSignature.verify(signatureBytes);
    }

    /***
     * Signs the document and returns the signature
     * @param pvt
     * @return
     * @throws Exception
     */
    public byte[] signDocument(PrivateKey pvt, byte[] data) throws Exception {
        // Get an instance of Signature object and initialize it.
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(pvt);

        /**
         * Supply the data to be signed to the Signature object
         * using the update() method and generate the digital
         * signature.
         */

        signature.update(data);
        byte[] sign=signature.sign();
        return Base64.getEncoder().encodeToString(sign).getBytes();
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }
}
