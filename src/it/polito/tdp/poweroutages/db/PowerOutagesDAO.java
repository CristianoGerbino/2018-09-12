package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutagesDAO {
	
	public List<Nerc> loadAllNercs(Map<Integer, Nerc> idMapNerc) {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				if (idMapNerc.get(res.getInt("id")) == null) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				idMapNerc.put(n.getId(), n);
				nercList.add(n);
			} else {
				nercList.add(idMapNerc.get(res.getInt("id")));
			}
		}
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	public List<Adiacenza> loadAllAdiacenze(Map<Integer, Nerc> idMapNerc) {
		String sql = "SELECT DISTINCT p1.nerc_id AS id1, p2.nerc_id AS id2, YEAR(p1.date_event_began) AS anno, " + 
				"month(p1.date_event_began) AS mese, COUNT(*) AS cnt " + 
				"FROM nercrelations r, poweroutages p1, poweroutages p2 " + 
				"WHERE p1.nerc_id = r.nerc_one AND p2.nerc_id = r.nerc_two " + 
				"AND YEAR(p1.date_event_began) = YEAR(p2.date_event_began) " + 
				"AND MONTH(p1.date_event_began) = MONTH(p2.date_event_began) " + 
				"AND p1.id < p2.id " + 
				"GROUP BY id1, id2";
		List<Adiacenza> list = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				
				Adiacenza ad = new Adiacenza (idMapNerc.get(res.getInt("id1")), idMapNerc.get(res.getInt("id2")), res.getInt("cnt"));
				
				list.add(ad);
			
		}
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return list;
	}

	public List<PowerOutage> loadAllOutages(Map<Integer, Nerc> idMapNerc) {
		String sql = "SELECT * FROM poweroutages";
		List<PowerOutage> list = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				
				PowerOutage po = new PowerOutage(res.getInt("id"), idMapNerc.get(res.getInt("nerc_id")),
						res.getTimestamp("date_event_began").toLocalDateTime(), res.getTimestamp("date_event_finished").toLocalDateTime());
				
				list.add(po);
			
		}
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return list;
	}
}
