package com.save.mangrove;

import javax.jws.soap.SOAPBinding;
import java.security.PublicKey;
import java.util.List;

public interface DatabaseRepository {

    List<UserData> fetchUsers();

    List<Transactions> fetchTxn();

    void updateUserData(String pubKey, String id);

    int addTxn(Transactions txn);

    Object retrieveTxn(String colName, String txnId);

    String retrieveUser(String colName, String id);
}
