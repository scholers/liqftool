package com.lqf.mem.h2;

public class User {
		long ID;
		String Name;
		String Passwd;
		
		User(long vID, String vName, String vPasswd)
		{
			ID = vID;
			Name = vName;
			Passwd = vPasswd;
		}

		public long getID() {
			return ID;
		}

		public void setID(long iD) {
			ID = iD;
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public String getPasswd() {
			return Passwd;
		}

		public void setPasswd(String passwd) {
			Passwd = passwd;
		}

	}