package com.lqf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author weique.lqf
 *
 */
public class TestConn {

	public static void main(String[] arges) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con;
			
				con = java.sql.DriverManager
						.getConnection(
								"jdbc:mysql://localhost/lqf?useUnicode=true",
								"root", "jackylee790521");
			
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery("select * from site_user");
			while (rst.next()) {
				System.out.println("<tr>");
				System.out.println("<td>" + rst.getString("ID") + "</td>");
				System.out
						.println("<td>" + rst.getString("PASSWORD") + "</td>");
				System.out.println("<td>" + rst.getString("NAME")
						+ "</td>");
				System.out.println("<td>" + rst.getString("EMAIL") + "</td>");
				System.out.println("</tr>");
			}
			// 关闭连接、释放资源
			rst.close();
			stmt.close();
			con.close();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
