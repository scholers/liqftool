package dbpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class DBConnectionPool {
	private int checkedOut;

	private Vector freeConnections = new Vector();

	private int maxConn;

	private String name;

	private String password;

	private String URL;

	private String user;

	/**
	 * 创建新的连接池
	 * 
	 * @param name连接池名字
	 * @param URL数据库的JDBC
	 *            URL
	 * @param user数据库帐号
	 *            ,或 null
	 * @param password密码
	 *            ,或 null
	 * @param maxConn此连接池允许建立的最大连接数
	 */
	public DBConnectionPool(String name, String URL, String user,
			String password, int maxConn) {
		this.name = name;
		this.URL = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}

	/**
	 * 将不再使用的连接返回给连接池
	 * 
	 * @param con客户程序释放的连接
	 */
	public synchronized void freeConnection(Connection con) {
		try {
			if(!con.isClosed()) {
			// 将指定连接加入到向量末尾
			freeConnections.addElement(con);
			checkedOut--;
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		notifyAll();
	
	}

	/**
	 * 从连接池获得一个可用连接.如没有空闲的连接且当前连接数小于最大连接 数限制,则创建新连接. 如原来登记为可用的连接不再有效,则从向量删除之,
	 * 然后递归调用自己以尝试新的可用连接.
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (freeConnections.size() > 0) {// 获取向量中第一个可用连接
			con = (Connection) freeConnections.firstElement();
			freeConnections.removeElementAt(0);
			try {
				if (con.isClosed()) {
					// log("从连接池" + name + "删除一个无效连接");
					// 递归调用自己,尝试再次获取可用连接
					con = getConnection();
				}
			} catch (SQLException e) {
				// log("从连接池" + name + "删除一个无效连接");
				// 递归调用自己,尝试再次获取可用连接
				con = getConnection();
			}
		} else if (maxConn == 0 || checkedOut < maxConn) {
			con = newConnection();
		}
		if (con != null) {
			checkedOut++;
		}
		return con;
	}

	/**
	 * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间 参见前一个getConnection()方法.
	 * 
	 * @param timeout以毫秒计的等待时间限制
	 */
	public synchronized Connection getConnection(long timeout) {
		long startTime = new Date().getTime();
		Connection con;
		while ((con = getConnection()) == null) {
			try {
				wait(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if ((new Date().getTime() - startTime) >= timeout) {// wait()返回的原因是超时
				return null;
			}
		}
		return con;
	}

	/**
	 * 关闭所有连接
	 */
	public synchronized void release() {
		Enumeration allConnections = freeConnections.elements();
		while (allConnections.hasMoreElements()) {
			Connection con = (Connection) allConnections.nextElement();
			try {
				con.close();
				// log("关闭连接池" + name + "中的一个连接");
			} catch (SQLException e) {
				// log(e, "无法关闭连接池" + name + "中的连接");
				e.printStackTrace();
			}
		}
		freeConnections.removeAllElements();
	}

	/**
	 * 创建新的连接
	 */
	private Connection newConnection() {
		Connection con = null;
		try {
			if (user == null || "".equals(user)) {
				con = DriverManager.getConnection(URL);
			} else {
				con = DriverManager.getConnection(URL, user, password);
			}
			// log("连接池" + name + "创建一个新的连接");
		} catch (SQLException e) {
			// log(e, "无法创建下列URL的连接: " + URL);
			return null;
		}
		return con;
	}
}
