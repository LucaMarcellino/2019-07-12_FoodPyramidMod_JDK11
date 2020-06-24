package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Archi;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDAO {
	public void listAllFoods(Map<Integer, Food> mappaCibi){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
					mappaCibi.put(res.getInt("food_code"),new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}

	public List< Food> getVertex(int ctn,Map<Integer,Food> mappaCibi) {
		String sql = "SELECT DISTINCT(`portion`.food_code) AS id,food.display_name AS name, COUNT(`portion`.portion_id) AS CTN FROM `portion`, food WHERE food.food_code=`portion`.food_code GROUP BY `portion`.food_code HAVING CTN=?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, ctn);
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f= new Food(res.getInt("id"),	res.getString("name"));
					list.add(f);
					mappaCibi.put(res.getInt("id"), f);
				
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Archi> getEdge(Map<Integer,Food> mappaCibi){
		String sql="SELECT f1.food_code AS id1,f2.food_code AS id2, AVG(c1.condiment_calories) AS avg FROM food_condiment AS f1,food_condiment AS f2,condiment AS c1, food AS f WHERE f1.food_code>f2.food_code AND f1.condiment_code=c1.condiment_code AND f2.condiment_code=c1.condiment_code and f1.condiment_code=f2.condiment_code GROUP BY f1.food_code,f2.food_code";
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql);
			List<Archi> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					int id1 = res.getInt("id1"); 
					int id2 = res.getInt("id2");
					if(mappaCibi.containsKey(id1) && mappaCibi.containsKey(id2)) {
						Archi a= new Archi(mappaCibi.get(id1), mappaCibi.get(id2), res.getDouble("avg"));
						list.add(a);
					}

				
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
