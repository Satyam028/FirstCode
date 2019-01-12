package com.pd;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.db.DbConnect;
import com.pto.Product;

public class ProductMain {

	public static void main(String[] args) throws ParseException {
		
		int opt;
		Connection con = null;
		System.out.println("Press 1 for Insertion of new item");
		System.out.println("Press 2 for Display of all item");
		System.out.println("Select any option: ");
		Scanner sc = new Scanner(System.in);
		opt = sc.nextInt();
		switch(opt)
		{
		
		case 1:
			String dof = "10.05.2012";
			String doe = "11.06.2013";
			Date d1 = new SimpleDateFormat("dd.mm.yyyy").parse(dof);
			Date d2 = new SimpleDateFormat("dd.mm.yyyy").parse(doe);
			Product product = new Product(4, "Rice", d1, d2, 10, 200);
			
			try {
				con = DbConnect.getConnection();
				String sql = "insert into Product(pId, productName, dateOfManuf, dateOfExpiry, quantity, price)" +
				"VALUES (?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, product.getpId());
				ps.setString(2, product.getProductName());
				ps.setDate(3, new java.sql.Date(product.getDateOfManuf().getTime()));
				ps.setDate(4, new java.sql.Date(product.getDateOfExpiry().getTime()));
				ps.setInt(5, product.getQuantity());
				ps.setFloat(6, product.getPrice());
				
				int c = ps.executeUpdate();
				System.out.println(c + "No of record/s got inserted");
			} 
			catch (ClassNotFoundException | SQLException e) 
			{
				System.out.println(e);
			}
			finally
			{
				try {
					con.close();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			
			break;
			
		case 2: 
			try {
				con = DbConnect.getConnection();
				Statement statement = con.createStatement();
				String sql = "select pId, productName, dateOfManuf, dateOfExpiry, quantity, price from Product";
				
				ResultSet resultSet = statement.executeQuery(sql);
				//System.out.println("Query Executed");
				
				while(resultSet.next())
				{
					System.out.print(" Product Id = " + resultSet.getInt("pId"));
					System.out.print(" Product Name = " + resultSet.getString("productName"));
					System.out.print(" Date of Manufacuturing = " + resultSet.getDate("dateOfManuf"));
					System.out.print(" Date of Expiry = " + resultSet.getDate("dateOfExpiry"));
					System.out.print(" Quantity = " + resultSet.getInt("quantity"));
					System.out.print(" Price = " + resultSet.getFloat("price"));
				}
			} 
			catch (SQLException e)
			{
				System.out.println(e);
			} catch (ClassNotFoundException e) {
				System.out.println(e);
			}
			
		}

	}

}
