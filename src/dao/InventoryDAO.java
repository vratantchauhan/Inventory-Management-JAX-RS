package com.vratant.jerseyapi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vratant.jerseyapi.util.SQLConnection;
import java.sql.*;
//import data.PreparedStatement;
//import data.String;

import com.vratant.jerseyapi.model.Inventory;

public class InventoryDAO {

	static SQLConnection DBMgr = SQLConnection.getInstance();
	
	private static ArrayList<Inventory> ReturnMatchingInventoryList (String queryString) {
		ArrayList<Inventory> inventoryListInDB = new ArrayList<Inventory>();
		
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();  
		try {
			stmt = conn.createStatement();
			ResultSet inventoryList = stmt.executeQuery(queryString);
			while (inventoryList.next()) {
				Inventory inventory = new Inventory(); 
				inventory.setProductId(inventoryList.getInt("product_id"));
				inventory.setProductName(inventoryList.getString("product_name"));
				inventory.setCost(inventoryList.getDouble("cost"));
				inventory.setAvailablity(inventoryList.getBoolean("availability"));  
				inventoryListInDB.add(inventory);	
			}
		} catch (SQLException e) {}
		return inventoryListInDB;
	}
	
	public static ArrayList<Inventory>  listInventories() {  
		return ReturnMatchingInventoryList(" SELECT * from inventory_table ORDER BY product_name");
}
	
	public static ArrayList<Inventory>   searchInventory (int id)  {  
		System.out.println(id);
		return ReturnMatchingInventoryList(" SELECT * from inventory_table WHERE product_id = '"+id+"' ");
}
	
	public static int insertInventory (Inventory inventory) {
		Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();  
	
	 	int tinyInt;
	 	 if(inventory.isAvailablity()==true) {
	 		 tinyInt = 1;
	 	 }else {
	 		 tinyInt = 0;
	 	 }
		try {
			stmt = conn.createStatement();
			String insertInventory = "INSERT INTO inventory_table (product_id,product_name,cost,availability) " + " VALUES ('"  
					+ inventory.getProductId()  + "','"
					+ inventory.getProductName() + "','"		
					+ inventory.getCost() + "','"
					+ tinyInt + "')";
			stmt.executeUpdate(insertInventory);	
			conn.commit(); 
		} catch (SQLException e) {System.out.println("Exception>>> "+e);}
		return inventory.getProductId();
	}
	
	public static int updateInventory (int id, Inventory inventory) {
		//Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();  
		
		List<Inventory> inventoryInDB = new ArrayList<Inventory>();
		inventoryInDB = ReturnMatchingInventoryList(" SELECT * from inventory_table WHERE product_id = '"+id+"' ");
	
		if(!inventoryInDB.isEmpty()) {
		 	int tinyInt;
		 	 if(inventory.isAvailablity()==true) {
		 		 tinyInt = 1;
		 	 }else {
		 		 tinyInt = 0;
		 	 }
			try {
//				stmt = conn.createStatement();
				String query = "Update inventory_table set product_name=?, cost=?, availability=? where product_id = ?";
		           
		           PreparedStatement pt = conn.prepareStatement(query);
		           
		           pt.setString(1, inventory.getProductName());
		           pt.setDouble(2, inventory.getCost());
		           pt.setInt(3, tinyInt);
		           pt.setInt(4, id);
		           pt.executeUpdate();
					
				conn.commit(); 
				
				return id;
			} 
			catch (SQLException e) {
				System.out.println("Exception>>> "+e);
				}
		}
		return 0;
	}
	
	public static boolean inventoryDelete (int id) {
		//Statement stmt = null;
		Connection conn = SQLConnection.getDBConnection();  
		
		List<Inventory> inventoryInDB = new ArrayList<Inventory>();
		inventoryInDB = ReturnMatchingInventoryList(" SELECT * from inventory_table WHERE product_id = '"+id+"' ");
	
		if(!inventoryInDB.isEmpty()) {
		 	
			try {
//				stmt = conn.createStatement();
				  String query = "delete from inventory_table where product_id = ?";
			      PreparedStatement preparedStmt = conn.prepareStatement(query);
			      preparedStmt.setInt(1, id);
			      preparedStmt.execute();
				conn.commit(); 
				
				return true;
			} 
			catch (SQLException e) {
				System.out.println("Exception>>> "+e);
				}
		}
		return false;
	}
	
}