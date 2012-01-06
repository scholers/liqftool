package com.grv


sql = Sql.newInstance( 'jdbc:jtds:sqlserver://serverName/dbName-CLASS;domain=domainName', 'username',
		'password', 'net.sourceforge.jtds.jdbc.Driver' )
sql.eachRow( 'select * from tableName' ) { println "$it.id -- ${it.firstName} --" }
//}
