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
    
    public Nursery fetchNurseryById(int nurseryId);
        
    int  addNursery(Nursery nursery);
    
    public List<Media> fetchMedias();
    
    public List<Media> fetchMediaByNurseryid(int nurseryId);
    
    public List<Map<String,Object>> fetchWalletForNurseryId(int nurseryId);
}
