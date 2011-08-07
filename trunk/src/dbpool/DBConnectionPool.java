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
	 * �����µ����ӳ�
	 * 
	 * @param name���ӳ�����
	 * @param URL���ݿ��JDBC
	 *            URL
	 * @param user���ݿ��ʺ�
	 *            ,�� null
	 * @param password����
	 *            ,�� null
	 * @param maxConn�����ӳ������������������
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
	 * ������ʹ�õ����ӷ��ظ����ӳ�
	 * 
	 * @param con�ͻ������ͷŵ�����
	 */
	public synchronized void freeConnection(Connection con) {
		try {
			if(!con.isClosed()) {
			// ��ָ�����Ӽ��뵽����ĩβ
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
	 * �����ӳػ��һ����������.��û�п��е������ҵ�ǰ������С��������� ������,�򴴽�������. ��ԭ���Ǽ�Ϊ���õ����Ӳ�����Ч,�������ɾ��֮,
	 * Ȼ��ݹ�����Լ��Գ����µĿ�������.
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (freeConnections.size() > 0) {// ��ȡ�����е�һ����������
			con = (Connection) freeConnections.firstElement();
			freeConnections.removeElementAt(0);
			try {
				if (con.isClosed()) {
					// log("�����ӳ�" + name + "ɾ��һ����Ч����");
					// �ݹ�����Լ�,�����ٴλ�ȡ��������
					con = getConnection();
				}
			} catch (SQLException e) {
				// log("�����ӳ�" + name + "ɾ��һ����Ч����");
				// �ݹ�����Լ�,�����ٴλ�ȡ��������
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
	 * �����ӳػ�ȡ��������.����ָ���ͻ������ܹ��ȴ����ʱ�� �μ�ǰһ��getConnection()����.
	 * 
	 * @param timeout�Ժ���Ƶĵȴ�ʱ������
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
			if ((new Date().getTime() - startTime) >= timeout) {// wait()���ص�ԭ���ǳ�ʱ
				return null;
			}
		}
		return con;
	}

	/**
	 * �ر���������
	 */
	public synchronized void release() {
		Enumeration allConnections = freeConnections.elements();
		while (allConnections.hasMoreElements()) {
			Connection con = (Connection) allConnections.nextElement();
			try {
				con.close();
				// log("�ر����ӳ�" + name + "�е�һ������");
			} catch (SQLException e) {
				// log(e, "�޷��ر����ӳ�" + name + "�е�����");
				e.printStackTrace();
			}
		}
		freeConnections.removeAllElements();
	}

	/**
	 * �����µ�����
	 */
	private Connection newConnection() {
		Connection con = null;
		try {
			if (user == null || "".equals(user)) {
				con = DriverManager.getConnection(URL);
			} else {
				con = DriverManager.getConnection(URL, user, password);
			}
			// log("���ӳ�" + name + "����һ���µ�����");
		} catch (SQLException e) {
			// log(e, "�޷���������URL������: " + URL);
			return null;
		}
		return con;
	}
}
