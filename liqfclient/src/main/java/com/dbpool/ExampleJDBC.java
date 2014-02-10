package com.dbpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.net.pic.util.FileBean;
import com.poi.ZhongjiangAcc;

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
		this.init(false);
	}
	
	/**
	 * 
	 */
	public ExampleJDBC(boolean isTest) {
		this.init(isTest);
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
			this.init(false);
			return null;
		}
	}

	public void init(boolean isTest) {
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
			if(isTest) {
				config.setJdbcUrl(DB_URL); //TEST
				config.setUsername("test_guess");//TEST
				config.setPassword("test_guess");//TEST
			} else {
				config.setJdbcUrl(DB_ONLINE); //
				config.setUsername("guess");//线上库
				config.setPassword("guess_000");//正式库
			}
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config); // setup the connection pool

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
	public Map<String, ZhongjiangAcc> executeQuery(Set<String> tempEmployeeList)
			throws SQLException {
		Map<String, ZhongjiangAcc> tempMap = new HashMap<String, ZhongjiangAcc>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = this.getConnection();
			StringBuilder strBuild = new StringBuilder();
			for (Iterator iter = tempEmployeeList.iterator(); iter.hasNext();) {
				String employStr = (String) iter.next();
				strBuild.append(employStr).append(",");
			}
			
			 stmt = connection.createStatement();
			 System.out.println("SELECT employee_num,bind_mobile FROM employee_mobile" +
						" where employee_num in(" + strBuild.toString() + "1)");
			rs = stmt.executeQuery("SELECT employee_num,bind_mobile FROM employee_mobile" +
					"where employee_num in(" + strBuild.toString() + "1)"); 
			
			//stmt.executeBatch();
			while (rs.next()) {
				ZhongjiangAcc tempZhongjiang = new ZhongjiangAcc();
				tempZhongjiang.setEmployee(rs.getString(1));
				tempZhongjiang.setMobile(rs.getString(2));
				//去除重复
				if(tempMap.get(tempZhongjiang.getEmployee()) != null) {
					tempMap.put(tempZhongjiang.getEmployee(), tempZhongjiang);
				}
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
		return tempMap;

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
			exampleJDBC.executeQuery(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}