package com.save.mangrove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JDBCRepository implements DatabaseRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL = "select * from user_data";

    public List<UserData> fetchUsers() {

        List<UserData> users = new ArrayList<UserData>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

        for (Map<String, Object> row : rows) {
            UserData user = new UserData();
            user.setId(row.get("id").toString());
            user.setFirstName(row.get("firstName").toString());
            user.setLastName(row.get("lastName").toString());
            user.setEmail(row.get("email").toString());
            user.setKycStatus(Boolean.parseBoolean(row.get("kycStatus").toString()));
            users.add(user);
        }

        return users;
    }

    public List<Transactions> fetchTxn() {
        String SQL = "select * from transactions";
        List<Transactions> txns = new ArrayList<Transactions>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

        for (Map<String, Object> row : rows) {
            Transactions txn = new Transactions();
            txn.setTxnId(row.get("txnId").toString());
            txn.setUserId(row.get("userid").toString());
            txn.setAmount(Integer.parseInt(row.get("amount").toString()));
            txn.setMode(row.get("mode").toString());
            txn.setStatus(row.get("status").toString());
            txn.setType(row.get("type").toString());
            txns.add(txn);
        }

        return txns;
    }

    public void updateUserData(String pubKey, String id) {

        String SQL = "update user_data set pubKey = ? where id = ?";
        int result = jdbcTemplate.update(SQL, pubKey, id);
        if (result == 1) {
            System.out.println("User data updated successfully");
        }
    }

    @Override
    public int addTxn(Transactions txn) {

        int result = jdbcTemplate.update(
                "INSERT INTO transactions (txnid, userid, status, mode, amount, type) VALUES (?,?,?,?,?,?)",
                txn.txnId, txn.userId, txn.status, txn.mode, txn.amount, txn.type
        );
        return result;
    }

    @Override
    public Object retrieveTxn(String colName,String txnId) {
        String sql = "SELECT "+colName+" FROM transactions WHERE txnId = ?";

        Object txn = jdbcTemplate.queryForObject(
                sql, new Object[]{txnId}, Object.class);

        return txn;
    }

    @Override
    public String retrieveUser(String colName, String id) {
        String sql = "SELECT " + colName + " FROM user_data WHERE userId = ?";
        String data = jdbcTemplate.queryForObject(
                sql, new Object[]{id}, String.class);

        return data;
    }

}
