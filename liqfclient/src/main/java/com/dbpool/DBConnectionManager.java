package com.dbpool;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DBConnectionManager {
	private static DBConnectionManager instance = new DBConnectionManager(); // Ψһʵ��

	private static  int clients;

	private Vector drivers = new Vector();

	private PrintWriter log;

	//private Hashtable pools = new Hashtable();
	
	private ConcurrentMap<String, DBConnectionPool> pools = new ConcurrentHashMap<String, DBConnectionPool>();

	/**
	 * ����Ψһʵ��.����ǵ�һ�ε��ô˷���,�򴴽�ʵ��
	 * 
	 * @return DBConnectionManager Ψһʵ��
	 */
	public static synchronized  DBConnectionManager getInstance() {
		/*if (instance == null) {
			instance = new DBConnectionManager();
		}
		clients++;*/
		return instance;
	}

	/**
	 * ��������˽���Է�ֹ�������󴴽�����ʵ��
	 */
	private DBConnectionManager() {
		init();
	}

	/**
	 * * �����Ӷ��󷵻ظ�������ָ�������ӳ�
	 * 
	 * @param name�������ļ��ж�������ӳ�����
	 * @param con���Ӷ���
	 *            \\\\r
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			pool.freeConnection(con);
		}
	}

	/**
	 * ���һ�����õ�(���е�)����.���û�п�������,������������С����������� 053 * ����,�򴴽�����������
	 * 
	 * @param name�������ļ��ж�������ӳ�����
	 *            056 *
	 * @return Connection �������ӻ�null 057
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		System.out.println("pools.size()===" + pools.size());
		if (pool != null) {
			
			return pool.getConnection();
		}
		return null;
	}

	/**
	 * ���һ����������.��û�п�������,������������С���������������, �򴴽�������������.����,��ָ����ʱ���ڵȴ������߳��ͷ�����.
	 * 
	 * @param name
	 *            ���ӳ����� 071 *
	 * @param time�Ժ���Ƶĵȴ�ʱ��
	 *            \\\\r
	 * @return Connection �������ӻ�null
	 */
	public Connection getConnection(String name, long time) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null) {
			return pool.getConnection(time);
		}
		return null;
	}

	/**
	 * �ر���������,�������������ע��\\\\r
	 */
	public synchronized void release() {
		// �ȴ�ֱ�����һ���ͻ��������
		if (--clients != 0) {
			return;
		}

		Iterator<DBConnectionPool> teml = pools.values().iterator();
		while (teml.hasNext()) {
			DBConnectionPool dbCon = teml.next();
			dbCon.release();
		}
		Enumeration allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log("����JDBC�������� " + driver.getClass().getName() + "��ע��");
			} catch (SQLException e) {
				log(e, "�޷���������JDBC���������ע��: " + driver.getClass().getName());
			}
		}
	}

	/**
	 * ����ָ�����Դ������ӳ�ʵ��.
	 * 
	 * @param props
	 *            ���ӳ����� 113
	 */
	private void createPools(Properties props) {
		Enumeration propNames = props.propertyNames();
		while (propNames.hasMoreElements()) {
			String name = (String) propNames.nextElement();
			if (name.endsWith(".url")) {
				String poolName = name.substring(0, name.lastIndexOf("."));
				String url = props.getProperty(poolName + ".url");
				if (url == null) {
					log("û��Ϊ���ӳ�" + poolName + "ָ��URL");
					continue;
				}
				String user = props.getProperty(poolName + ".user");
				String password = props.getProperty(poolName + ".password");
				String maxconn = props.getProperty(poolName + ".maxconn", "0");
				int max;
				try {
					max = Integer.valueOf(maxconn).intValue();
				} catch (NumberFormatException e) {
					log("������������������: " + maxconn + " .���ӳ�: " + poolName);
					max = 0;
				}
				DBConnectionPool pool = new DBConnectionPool(poolName, url,
						user, password, max);
				pools.put(poolName, pool);
				System.out.println("�ɹ��������ӳ�" + poolName);
			}
		}
		System.out.println("�ɹ��������ӳ�:::" + pools.size());
	}

	/**
	 * ��ȡ������ɳ�ʼ��
	 */
	private void init() {
		InputStream is = getClass().getResourceAsStream("/db.properties");
		Properties dbProps = new Properties();
		try {
			dbProps.load(is);
		} catch (Exception e) {
			System.err.println("���ܶ�ȡ�����ļ�. "
					+ "��ȷ��db.properties��CLASSPATHָ����·����");
			return;
		}
		String logFile = dbProps.getProperty("logfile",
				"DBConnectionManager.log");
		try {
			log = new PrintWriter(new FileWriter(logFile, true), true);
		} catch (IOException e) {
			System.err.println("�޷�����־�ļ�: " + logFile);
			log = new PrintWriter(System.err);
		}
		loadDrivers(dbProps);
		createPools(dbProps);
	}

	/**
	 * װ�غ�ע������JDBC��������
	 * 
	 * @param props����
	 */
	private void loadDrivers(Properties props) {
		String driverClasses = props.getProperty("drivers");
		StringTokenizer st = new StringTokenizer(driverClasses);
		while (st.hasMoreElements()) {
			String driverClassName = st.nextToken().trim();
			try {
				Driver driver = (Driver) Class.forName(driverClassName)
						.newInstance();
				DriverManager.registerDriver(driver);
				drivers.addElement(driver);
				log("�ɹ�ע��JDBC��������" + driverClassName);
			} catch (Exception e) {
				e.printStackTrace();
				log("�޷�ע��JDBC��������: " + driverClassName + ", ����: " + e);
			}
		}
	}

	/**
	 * ���ı���Ϣд����־�ļ�
	 */
	private void log(String msg) {
		log.println(new Date() + ": " + msg);
	}

	/**
	 * ���ı���Ϣ���쳣д����־�ļ�
	 */
	private void log(Throwable e, String msg) {
		log.println(new Date() + ": " + msg);
		e.printStackTrace(log);
	}
}
