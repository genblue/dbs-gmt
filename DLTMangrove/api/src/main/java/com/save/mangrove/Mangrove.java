package com.save.mangrove;

import java.util.List;

public interface Mangrove {

    List<Transactions> listTxns();

    List<UserData> listUsers();

    Object createKeys(String id) throws Exception;

    boolean publishTxn(Transactions txn) throws Exception;

    public Nursery fetchNurseryById(int nursery_id);
    
    List<Nursery> listNurseries();
}
