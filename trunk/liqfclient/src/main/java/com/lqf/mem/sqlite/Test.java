package com.lqf.mem.sqlite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.sqlite.JDBC");   
	        Connection conn = DriverManager.getConnection("jdbc:sqlite:dc_client.db");   
			
			Statement stat = conn.createStatement();
			
			//update(conn);
			create(stat);
			
			//select(stat);
			//insert(stat);
			
			//delete(conn);
			
			conn.close();
			
			System.out.println("Test End!");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createDb(Statement stat)
	{
		
	}
	
	public static void create(Statement stat) throws SQLException
	{
		stat.execute("CREATE TABLE TEST(ID INTEGER PRIMARY KEY, NAME VARCHAR(255), PASSWORD VARCHAR(255))");
	}
	
	
	public static void insert(Statement stat) throws SQLException
	{
		System.out.println("insert begin...");
		
		long lstart = System.currentTimeMillis();
		for(int i = 1; i <= 100000; i ++)
		{
			//new User(i, "user", "super");
			stat.execute("INSERT INTO test VALUES(" + i + ", 'user', 'super')");
			
			if (i % 10000 == 0)
				System.out.println(i + ":" + (System.currentTimeMillis() - lstart));
		}
		
		System.out.println("insert end:" + (System.currentTimeMillis() - lstart));
	}
	
	public static void select(Statement stat) throws SQLException
	{
		System.out.println("select begin...");
		
		long lstart = System.currentTimeMillis();
		
		for(int i = 1; i <= 100001; i ++)
		{
			ResultSet rs = stat.executeQuery("SELECT ID, NAME, PASSWORD FROM TEST WHERE ID = " + i);
			if (rs.next())
			{
				System.out.print("ID=" + rs.getInt("ID"));
				//System.out.print(",NAME=" + rs.getString("NAME"));
				//System.out.print(",PASSWORD=" + rs.getString("PASSWORD"));
				System.out.println();
			}
			
			if (i % 10000 == 0)
				System.out.println(i + ":" + (System.currentTimeMillis() - lstart));
		}
		
		System.out.println("select end:" + (System.currentTimeMillis() - lstart));
	}

	public static void update(Connection conn) throws SQLException
	{
		System.out.println("update begin...");
		
		long lstart = System.currentTimeMillis();
		
		for(int i = 1; i <= 10000000; i ++)
		{
			String sql = "update test set NAME = 'xiebo' where ID = " + i;
			PreparedStatement pstmt = conn.prepareStatement(sql); 
			pstmt.executeUpdate();
			
			if (i % 1000000 == 0)
				System.out.println(i + ":" + (System.currentTimeMillis() - lstart));
		}
		
		System.out.println("update end:" + (System.currentTimeMillis() - lstart));
	}
	
	public static void delete(Connection conn) throws SQLException
	{
		System.out.println("delete begin...");
		
		long lstart = System.currentTimeMillis();
		for(int i = 1; i <= 10000000; i ++)
		{
			String sql = "delete FROM test where ID = " + i;
			PreparedStatement pstmt = conn.prepareStatement(sql); 
			
			pstmt.executeUpdate();

			if (i % 1000000 == 0)
				System.out.println(i + ":" + (System.currentTimeMillis() - lstart));
		}
		
		System.out.println("delete end:" + (System.currentTimeMillis() - lstart));
	}
	
}

/*
 insert begin...
10000:839
20000:1263
30000:1652
40000:2060
50000:2451
60000:2882
70000:3251
80000:3625
90000:4040
100000:4408
insert end:4408
select begin...
10000:818
20000:1279
30000:1785
40000:2238
50000:2675
60000:3111
70000:3546
80000:4037
90000:4473
100000:4910
select end:4910
Test End!
CPU:Intel(R) Xeon 3.0GHZ * 2
Memory:1G
Red Hat 4.1.1-52

*/
