package com.save.mangrove.rest;

import com.save.mangrove.Mangrove;
import com.save.mangrove.Transactions;
import com.save.mangrove.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MangroveRestController {

    @Autowired
    Mangrove mangrove;

    @GetMapping("/users")
    public List<UserData> fetchUsers() {
        return mangrove.listUsers();
    }

    @GetMapping("/txn")
    public List<Transactions> fetchTxns() {
        return mangrove.listTxns();
    }

    @PostMapping("/kyc")
    public Object performKyc(@RequestBody String id) throws Exception {
        /**
         * Step1 : To do the Kyc of the user
         * Step2 : On successful verification, generate public/private key
         * and expose it to user.
         * has to be done only once in lifetime.
         */
        return mangrove.createKeys(id);

    }

    @PostMapping("/txn")
    public boolean addTxn(@RequestBody Transactions txn) throws Exception{
        boolean status=mangrove.publishTxn(txn);
        return status;
    }




}
