package com.save.mangrove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.save.mangrove.rowmappers.NurseryRowMapper;
import com.save.mangrove.utils.DLTMangroveUtility;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JDBCRepository implements DatabaseRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DLTMangroveUtility dLTMangroveUtility;

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
    public Object retrieveTxn(String colName, String txnId) {
        String sql = "SELECT " + colName + " FROM transactions WHERE txnId = ?";

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

    @Override
    public List<Nursery> fetchNurseryList() {
        String SQL = "select * from nursery";
        List<Nursery> nurseryList = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

        for (Map<String, Object> row : rows) {
            Nursery nursery = new Nursery();
            nursery.setNurseryId(((int) dLTMangroveUtility.isNullOrBlank(row.get("nursery_id"))));
            nursery.setCommunityName(dLTMangroveUtility.isNullOrBlank(row.get("CommunityName")).toString());
            nursery.setGeofenceLookupIndex(dLTMangroveUtility.isNullOrBlank(row.get("GeofenceLookupIndex")).toString());
            nursery.setGeofenceSize(dLTMangroveUtility.isNullOrBlank(row.get("GeofenceSize")).toString());
            nursery.setNurseryAdministratorFamilyName(dLTMangroveUtility.isNullOrBlank(row.get("NurseryAdministratorFamilyName")).toString());
            nursery.setNurseryAdministratorFirstName(dLTMangroveUtility.isNullOrBlank(row.get("NurseryAdministratorFirstName")).toString());
            nursery.setNurseryProposalSiteLocation(dLTMangroveUtility.isNullOrBlank(row.get("NurseryProposalSiteLocation")).toString());
            nursery.setNurserySiteName(dLTMangroveUtility.isNullOrBlank(row.get("NurserySiteName")).toString());
            nursery.setPhoto1Team(dLTMangroveUtility.isNullOrBlank(row.get("Photo1Team")).toString());
            nursery.setPhoto2NurserySite(dLTMangroveUtility.isNullOrBlank(row.get("Photo2NurserySite")).toString());
            nursery.setPhoto3PlantingSite(dLTMangroveUtility.isNullOrBlank(row.get("Photo3PlantingSite")).toString());
            nursery.setPlantationProposalSize((BigDecimal) dLTMangroveUtility.isNullOrBlank(row.get("PlantationProposalSize")));

            nurseryList.add(nursery);
        }

        return nurseryList;
    }

    @Override
    public void addNursery(Nursery nursery) {
        String sql = "INSERT INTO NURSERY " +
                "(NURSERY_ID, NurseryAdministratorFirstName, NurseryAdministratorFamilyName,NurserySiteName,CommunityName," +
                "PlantationProposalSize) VALUES (?, ?, ? , ?, ? ,? )";
        
//        int row=jdbcTemplate.update(sql, new Object[]{nursery.getNurseryId(),
//                nursery.getNurseryAdministratorFirstName(), nursery.getNurseryAdministratorFamilyName(),nursery.getNurserySiteName(),
//                nursery.getCommunityName(),nursery.getNurseryProposalSiteLocation(),nursery.getPlantationProposalSize(),nursery.getPhoto1Team(),
//                nursery.getPhoto2NurserySite(),nursery.getPhoto3PlantingSite(),nursery.getGeofenceLookupIndex(),nursery.getGeofenceSize()
//        });
        int row=jdbcTemplate.update(sql, new Object[]{nursery.getNurseryId(),
                nursery.getNurseryAdministratorFirstName(), nursery.getNurseryAdministratorFamilyName(),nursery.getNurserySiteName(),
                nursery.getCommunityName(),nursery.getPlantationProposalSize()
        });
        System.out.println(row + " row inserted.");
    }

    @Override
    public Nursery fetchNurseryById(int nurseryId) {
        Nursery nursery = new Nursery();
        String SQL = "select * from nursery where nursery_id=?";
        nursery = jdbcTemplate.queryForObject(SQL, new Object[]{nurseryId}, new NurseryRowMapper());
        return nursery;
    }

    @Override
    public List<Media> fetchMedias() {
        String query = "select * from media";
        List<Media> mediaList = new ArrayList<>();
        List<Map<String, Object>> mediaRecords = jdbcTemplate.queryForList(query);
        for (Map<String, Object> mediaRecord : mediaRecords) {
            Media media = new Media();
            media.setMedia_id(((int) dLTMangroveUtility.isNullOrBlank(mediaRecord.get("media_id"))));
            media.setMedia_s3_link(dLTMangroveUtility.isNullOrBlank(mediaRecord.get("media_s3_link")).toString());
            media.setNursery_id(((int) dLTMangroveUtility.isNullOrBlank(mediaRecord.get("nursery_id"))));
            mediaList.add(media);
        }
        return mediaList;
    }

    @Override
    public List<Media> fetchMediaByNurseryid(int nurseryId) {
        String query = "select * from media where  nursery_id=?";
        List<Media> mediaList = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[]{nurseryId});

        for (Map<String, Object> mediaRecord : rows) {
            Media media = new Media();
            media.setMedia_id(((int) dLTMangroveUtility.isNullOrBlank(mediaRecord.get("media_id"))));
            media.setMedia_s3_link(dLTMangroveUtility.isNullOrBlank(mediaRecord.get("media_s3_link")).toString());
            media.setNursery_id(((int) dLTMangroveUtility.isNullOrBlank(mediaRecord.get("nursery_id"))));
            mediaList.add(media);
        }
        return mediaList;
    }

    @Override
    public List<Map<String, Object>> fetchWalletForNurseryId(int nurseryId) {
        String query = "select * from transactions where nursery_id=?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[]{nurseryId});
        List<Map<String, Object>> transactionList = new ArrayList<>();
        int finalAmount = 0;
        for (Map<String, Object> row : rows) {
            Map<String, Object> resultObject = new HashMap<>();
            resultObject.put("amount", (int) dLTMangroveUtility.isNullOrBlank(row.get("amount")));
            resultObject.put("type", dLTMangroveUtility.isNullOrBlank(row.get("type")).toString());

            resultObject.put("mode", dLTMangroveUtility.isNullOrBlank(row.get("mode")).toString());
            if (dLTMangroveUtility.isNullOrBlank(row.get("status")).toString().equals("successful")) {
                if (dLTMangroveUtility.isNullOrBlank(row.get("type")).toString().equals("credit")) {
                    finalAmount += (int) dLTMangroveUtility.isNullOrBlank(row.get("amount"));
                } else {
                    finalAmount -= (int) dLTMangroveUtility.isNullOrBlank(row.get("amount"));

                }

            }
            resultObject.put("nusery_id", (int) dLTMangroveUtility.isNullOrBlank(row.get("nursery_id")));
            resultObject.put("status", dLTMangroveUtility.isNullOrBlank(row.get("status")).toString());
            resultObject.put("closing_balance", finalAmount);

            transactionList.add(resultObject);
        }

        return transactionList;
    }

}
