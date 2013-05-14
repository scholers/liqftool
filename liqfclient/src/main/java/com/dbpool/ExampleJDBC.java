package com.dbpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.net.pic.util.FileBean;

/**
 * A test project demonstrating the use of BoneCP in a JDBC environment.
 * 
 * @author wwadge
 * 
 */
public class ExampleJDBC {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://10.250.4.98:3306/test01_guess_amount?useUnicode=true&characterEncoding=GBK&useServerPrepStmts=false";
    private static final String DB_ONLINE = "jdbc:mysql://10.250.4.98:3306/guess_amount?useUnicode=true&characterEncoding=GBK&useServerPrepStmts=false";
	private BoneCP connectionPool = null;
	
	
	/**
	 * 
	 */
	public ExampleJDBC() {
		this.init();
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		if (connectionPool != null) {
			return connectionPool.getConnection();
		} else {
			this.init();
			return null;
		}
	}

	public void init() {
		// BoneCP connectionPool = null;
		// Connection connection = null;

		try {
			// load the database driver (make sure this is in your classpath!)
			Class.forName(DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// setup the connection pool
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(DB_ONLINE); //
			config.setUsername("guess");//线上库
			config.setPassword("guess_000");//正式库
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config); // setup the connection pool

			// connection = connectionPool.getConnection(); // fetch a
			// connection

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return connection;
	}



	/**
	 * 批量增加每天可以抽奖的用户
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	public void executeQuery()
			throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = this.getConnection();
			System.out.println("Connection successful!");
			 stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT 1 FROM act_lottery"); 
			//stmt.executeBatch();
			while (rs.next()) {
				System.out.println(rs.getString(1)); // should print out "1"'
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 批量增加每天可以抽奖的用户
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	public void insertBatch(Set<String> employeeStr) throws SQLException {
		Connection connection = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps = connection
					.prepareStatement("insert into act_lottery (employee_num, lottery_time, gmt_created, gmt_modified) value(?, 0,now(),now());");
			//for (int i = 0; i < employeeStr.size(); i++) {
			for (Iterator iter = employeeStr.iterator(); iter.hasNext();) {	
				String employee = (String) iter.next();
				ps.setString(1, employee);
				ps.executeUpdate();
				connection.commit();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		ExampleJDBC exampleJDBC = new ExampleJDBC();
		try {
			exampleJDBC.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}