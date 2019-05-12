package com.save.mangrove;

import java.util.List;
import java.util.Map;

public interface Mangrove {

    List<Transactions> listTxns();

    List<UserData> listUsers();

    Object createKeys(String id) throws Exception;

    boolean publishTxn(Transactions txn) throws Exception;

    public Nursery fetchNurseryById(int nursery_id);
    
    List<Nursery> listNurseries();
    
public List<Media> getMedias();
    
    public List<Media> getMediaByNurseryid(int nurseryId);
    
    public List<Map<String,Object>> getWalletByNurseryid(int nurseryId);
}
