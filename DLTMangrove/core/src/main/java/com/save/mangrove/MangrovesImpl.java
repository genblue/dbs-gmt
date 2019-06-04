package com.save.mangrove;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MangrovesImpl implements Mangrove {

    public static int userCount = 0;
    public static String USER = "user";

    @Autowired
    KeyGenerator store;

    @Autowired
    DatabaseRepository repository;

    public Object createKeys(String id) throws Exception {
//        if (store.readKey(id) == null ) {
            store.applyAlgorithm();
            store.writeKeys("KeyPair/privateKey" + userCount, store.getPrivateKey().getEncoded(), id);
            Key key = store.readKey(id);
//            userCount++;
            Base64.Encoder encoder = Base64.getEncoder();
            System.out.println("---BEGIN PRIVATE KEY---AS CREATED");
            System.out.println(encoder.encode(store.getPrivateKey().getEncoded()));
            System.out.println("---END OF PRIVATE KEY-----------");
            System.out.println();
            System.out.println("---BEGIN PUBLIC KEY---AS CREATED");
            System.out.println(encoder.encode(store.getPublicKey().getEncoded()));
            System.out.println();
            System.out.println("---BEGIN PRIVATE KEY---FROM KEYSTORE");
            String encoded = encoder.encode(key.getEncoded()).toString();
            System.out.println(encoded);
            System.out.println("---END PRIVATE KEY---");

            repository.updateUserData(encoder.encode(store.getPublicKey().getEncoded()).toString(), id);
//        }
        return store.getPrivateKey().getEncoded();
    }

    public List<UserData> listUsers() {
        return repository.fetchUsers();
    }

    public List<Transactions> listTxns() {
        return repository.fetchTxn();
    }

    public boolean publishTxn(Transactions txn) throws Exception{
        //create the hash
        //create and store the digital signature in keystore

//        createKeys(txn.userId);
        repository.addTxn(txn);
        Object txns=repository.retrieveTxn("ts",txn.txnId);
//        String data=repository.retrieveUser("2");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String data=txn.txnId+txn.userId+txns.toString()+txn.amount;

        byte[] hashInBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));

        //sign by user
        byte[] digitalSign=store.signDocument(store.getPrivateKey(),hashInBytes);

        return store.isSignatureVerified(store.getPublicKey(),digitalSign,hashInBytes);

//        String message = "the answer to life the universe and everything";
//
//        //Encrypt the message
//        String cipherText = GenerateKeys.encrypt(message, store.getPublicKey());
//
//        //Now decrypt it
//        String decipheredMessage = GenerateKeys.decrypt(cipherText, store.getPrivateKey());
//
//        System.out.println(decipheredMessage);
//
//        //Let's sign our message
//        String signature = GenerateKeys.sign("foobar", store.getPrivateKey());
//
//        //Let's check the signature
//        boolean isCorrect = GenerateKeys.verify("foobar", signature, store.getPublicKey());
//        System.out.println("Signature correct: " + isCorrect);
//        return isCorrect;

    }

	@Override
	public List<Nursery> listNurseries() {
		 return repository.fetchNurseryList();
	}

	@Override
	public Nursery fetchNurseryById(int nursery_id) {
		return repository.fetchNurseryById(nursery_id);
	}

	@Override
	public List<Media> getMedias() {
		return repository.fetchMedias();
	}

	@Override
	public List<Media> getMediaByNurseryid(int nurseryId) {		
		return repository.fetchMediaByNurseryid(nurseryId);
	}

	@Override
	public List<Map<String,Object>> getWalletByNurseryid(int nurseryId) {
		return repository.fetchWalletForNurseryId(nurseryId);

	}




}
