package com.dbpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestDbPool {

	private static final String DB_URL = "jdbc:mysql://localhost/liqf?useUnicode=true&characterEncoding=GBK&useServerPrepStmts=false";

	public static void search() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = java.sql.DriverManager.getConnection(DB_URL, "liqf",
				"790521");
		Statement stmt = con.createStatement();
		ResultSet rst = stmt.executeQuery("select * from user_info");
		while (rst.next()) {
			System.out.println("<tr>");
			System.out.println("<td>" + rst.getInt("USER_ID") + "</td>");
			System.out.println("<td>" + rst.getString("USER_NAME") + "</td>");
			System.out.println("<td>" + rst.getString("USER_SEX") + "</td>");
			System.out.println("<td>" + rst.getDate("CREATE_DATE") + "</td>");
			System.out.println("</tr>");
		}
		// 关闭连接、释放资源
		rst.close();
		stmt.close();
		con.close();
	}

	public static void deleteAll() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection con = java.sql.DriverManager.getConnection(DB_URL,
					"liqf", "790521");

			Statement stmt = con.createStatement();
			StringBuilder strBuild = new StringBuilder();

			strBuild.append("delete from user_info;");

			// strBuild.append("VALUES(").append(i).append(",'liqf','M','1970-12-31','dd").append(i).append("');");

			System.out.println(strBuild.toString());
			stmt.execute(strBuild.toString());

			// con.commit();
			// 关闭连接、释放资源
			// rst.close();
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

	public static void insert() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection con = java.sql.DriverManager.getConnection(DB_URL,
					"liqf", "790521");
			con.setAutoCommit(false);// 关闭事务自动提交
			PreparedStatement pstmt = null;
			for (int i = 1; i <= 10; i++) {
				// info = "这条记录是第=";
				// info = info.concat(java.lang.Integer.toString(i));
				String str = "INSERT INTO user_info(USER_ID, USER_NAME, USER_SEX, CREATE_DATE, USER_NOTES)  values(?,?,?,?,?);";
				pstmt = con.prepareStatement(str);

				pstmt.setInt(1, i);
				pstmt.setString(2, "liqf");
				pstmt.setString(3, "M");
				pstmt.setString(4, "1979-05-21");
				pstmt.setString(5, "liqf test!!!");
				pstmt.executeUpdate();
			}
			con.commit();
			if(pstmt != null) pstmt.close();
			// 关闭连接、释放资源
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
	
	public static void insertRb() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection con = java.sql.DriverManager.getConnection(DB_URL,
					"liqf", "790521");
			con.setAutoCommit(false);// 关闭事务自动提交
			PreparedStatement pstmt = null;
			for (int i = 1; i <= 10; i++) {
				// info = "这条记录是第=";
				// info = info.concat(java.lang.Integer.toString(i));
				String str = "INSERT INTO user_info(USER_ID, USER_NAME, USER_SEX, CREATE_DATE, USER_NOTES)  values(?,?,?,?,?);";
				pstmt = con.prepareStatement(str);

				pstmt.setInt(1, i);
				pstmt.setString(2, "liqf");
				pstmt.setString(3, "M");
				pstmt.setString(4, "1979-05-21");
				pstmt.setString(5, "liqf test!!!");
				pstmt.executeUpdate();
			}
			con.commit();
			if(pstmt != null) pstmt.close();
			// 关闭连接、释放资源
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
	
	
	public static void insertConPol(int x) {
		try {
			Connection con = DBConnectionManager.getInstance().getConnection("test");
			
			con.setAutoCommit(false);// 关闭事务自动提交
			PreparedStatement pstmt = null;
			//for (int i = 1; i <= 1; i++) {
				// info = "这条记录是第=";
				// info = info.concat(java.lang.Integer.toString(i));
				String str = "INSERT INTO user_info(USER_ID, USER_NAME, USER_SEX, CREATE_DATE, USER_NOTES)  values(?,?,?,?,?);";
				pstmt = con.prepareStatement(str);

				pstmt.setInt(1, x);
				pstmt.setString(2, "liqf");
				pstmt.setString(3, "M");
				pstmt.setString(4, "1979-05-21");
				pstmt.setString(5, "liqf test!!!");
				pstmt.executeUpdate();
			//}
			con.commit();
			if(pstmt != null) pstmt.close();
			// 关闭连接、释放资源
			DBConnectionManager.getInstance().freeConnection("test", con);
			//con.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 
	* 线程池的测试 
	* 
	* @author leizhimin 2009-7-7 14:15:43 
	*/ 
	class DownTask implements Runnable { 
	        private int x;            //线程编号 
	 
	        public DownTask(int x) { 
	                this.x = x; 
	        } 
	 
	        public void run() { 
	                System.out.println(x + " thread doing something!"); 
	                insertConPol(x);
	                try { 
	                		//insert();
	                        TimeUnit.SECONDS.sleep(5L); 
	                        System.out.println("第" + x + "个线程休息完毕"); 
	                } catch (InterruptedException e) { 
	                        e.printStackTrace(); 
	                } 
	        } 
	} 
	 

	public static void main(String[] arges) {
		deleteAll();
		ExecutorService exec = Executors.newFixedThreadPool(10); 
		{
			 for (int i = 0; i < 10; i++) { 
				 TestDbPool test = new TestDbPool();
				 exec.execute(test.new DownTask(i)); 
			 }
		}
		exec.shutdown();
		
		// Connection testCon =
		// DBConnectionManager.getInstance().getConnection(name);
		// testCon.createStatement();
		try {
			search();
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
