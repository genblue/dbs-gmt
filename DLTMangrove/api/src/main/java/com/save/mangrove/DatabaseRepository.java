package com.save.mangrove;

import javax.jws.soap.SOAPBinding;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.util.List;

public interface DatabaseRepository {

    List<UserData> fetchUsers();

    List<Transactions> fetchTxn();

    void updateUserData(String pubKey, String id);

    int addTxn(Transactions txn);

    Object retrieveTxn(String colName, String txnId);

    String retrieveUser(String colName, String id);
    
    List<Nursery> fetchNurseryList();
    
    public Nursery fetchNurseryById(int nurseryId);
        
    int  addNursery(Nursery nursery);
}
