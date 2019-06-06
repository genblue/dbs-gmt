package com.save.mangrove;

import java.util.List;
import java.util.Map;

public interface DatabaseRepository {

    List<UserData> fetchUsers();

    List<Transactions> fetchTxn();

    void updateUserData(String pubKey, String id);

    int addTxn(Transactions txn);

    Object retrieveTxn(String colName, String txnId);

    String retrieveUser(String colName, String id);
    
    List<Nursery> fetchNurseryList();
    
    Nursery fetchNurseryById(int nurseryId);
        
    void addNursery(Nursery nursery);
    
    List<Media> fetchMedias();
    
    List<Media> fetchMediaByNurseryid(int nurseryId);
    
    List<Map<String,Object>> fetchWalletForNurseryId(int nurseryId);


}
