package com.save.mangrove.rowmappers;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.save.mangrove.Nursery;
import com.save.mangrove.utils.DLTMangroveUtility;

public class NurseryRowMapper implements RowMapper<Nursery> {
	@Autowired
	private DLTMangroveUtility dLTMangroveUtility;

	@Override
	public Nursery mapRow(ResultSet rs, int rowNum) throws SQLException {
		Nursery nursery = new Nursery();
		nursery.setNurseryId(((int) dLTMangroveUtility.isNullOrBlank(rs.getInt("nursery_id"))));
		nursery.setCommunityName(dLTMangroveUtility.isNullOrBlank(rs.getString("CommunityName")).toString());
		nursery.setGeofenceLookupIndex(
				dLTMangroveUtility.isNullOrBlank(rs.getString("GeofenceLookupIndex")).toString());
		nursery.setGeofenceSize(dLTMangroveUtility.isNullOrBlank(rs.getString("GeofenceSize")).toString());
		nursery.setNurseryAdministratorFamilyName(
				dLTMangroveUtility.isNullOrBlank(rs.getString("NurseryAdministratorFamilyName")).toString());
		nursery.setNurseryAdministratorFirstName(
				dLTMangroveUtility.isNullOrBlank(rs.getString("NurseryAdministratorFirstName")).toString());
		nursery.setNurseryProposalSiteLocation(
				dLTMangroveUtility.isNullOrBlank(rs.getString("NurseryProposalSiteLocation")).toString());
		nursery.setNurserySiteName(dLTMangroveUtility.isNullOrBlank(rs.getString("NurserySiteName")).toString());
		nursery.setPhoto1Team(dLTMangroveUtility.isNullOrBlank(rs.getString("Photo1Team")).toString());
		nursery.setPhoto2NurserySite(dLTMangroveUtility.isNullOrBlank(rs.getString("Photo2NurserySite")).toString());
		nursery.setPhoto3PlantingSite(dLTMangroveUtility.isNullOrBlank(rs.getString("Photo3PlantingSite")).toString());
		nursery.setPlantationProposalSize(
				(BigDecimal) dLTMangroveUtility.isNullOrBlank(rs.getBigDecimal("PlantationProposalSize")));

		return nursery;
	}

}
