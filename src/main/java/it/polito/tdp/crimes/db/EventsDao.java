package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getCategorie()
	{
		String sql = "SELECT DISTINCT offense_category_id "
				+ "FROM EVENTS "
				+ "ORDER BY offense_category_id;";
		
		List<String> result = new ArrayList<String>();
		
		try
		{
			
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
	
			ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			
				result.add(res.getString("offense_category_id"));
		}
				
				conn.close();
				return result;
				
			} catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
		
		
	}
	
	public List<String> getVertici(String cat, int a)
	{
		String sql = "SELECT DISTINCT offense_type_id "
				+ "FROM EVENTS "
				+ "WHERE offense_category_id = ? AND YEAR (reported_date) = ?" ;
		
		List<String> vertici = new ArrayList<String>();
		
		try
		{
			
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		
		st.setString(1, cat);
		st.setInt(2, a);
		
		ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			
				vertici.add(res.getString("offense_type_id"));
		}
				
				conn.close();
				return vertici ;
				
			} catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
	
	}
	
	public List<Adiacenza> getArchi(String c, int a)
	{
		String sql = "SELECT e1.offense_type_id, e2.offense_type_id, COUNT(DISTINCT e1.district_id) as peso "
				+ "FROM EVENTS e1, EVENTS e2 "
				+ "WHERE e1.offense_type_id > e2.offense_type_id AND e1.offense_category_id = ? AND e1.offense_category_id = e2.offense_category_id "
				+ "       AND YEAR(e1.reported_date) = ? AND YEAR(e1.reported_date) = YEAR(e2.reported_date) "
				+ "       AND e1.district_id = e2.district_id "
				+ "GROUP BY e1.offense_type_id, e2.offense_type_id";
		
		List<Adiacenza> archi = new ArrayList<Adiacenza>();
		
		try
		{
			
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
		
			st.setString(1, c);
			st.setInt(2, a);
		
			ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			
				archi.add(new Adiacenza(res.getString("e1.offense_type_id"), res.getString("e2.offense_type_id"), res.getInt("peso")));
		}
				
				conn.close();
				return archi;
				
			} catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
		
		
	}

}
