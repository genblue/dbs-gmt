package com.save.mangrove;

import java.security.*;

public class SampleKeyGenerate {

    public KeyPair generateKeyPair(long seed)throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom rng = SecureRandom.getInstance("SHA1PRNG", "SUN");
        rng.setSeed(seed);
        keyGenerator.initialize(1024, rng);

        return (keyGenerator.generateKeyPair());
    }

    public static void main(String args[]) throws Exception {
        SampleKeyGenerate kpge = new SampleKeyGenerate();
        KeyPair kp = kpge.generateKeyPair(999);
        System.out.println("\n-- Private Key ----");
        PrivateKey priKey = kp.getPrivate();
        System.out.println("   Algorithm=" + priKey.getAlgorithm());
        System.out.println("   Encoded=" + priKey.getEncoded());
        System.out.println("   Format=" + priKey.getFormat());
    }
}
