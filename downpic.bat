@echo off
set ORIGCLASSPATH=%CLASSPATH%
if not "%JAVA_HOME%" == "" goto gotJavaHome
echo You must set JAVA_HOME to point to your Java SDK install directory
goto cleanup
:gotJavaHome

echo %BASE_DIR%
 
set BASE_DIR="%JAVA_HOME%\lib\tools.jar"
set BASE_DIR=%BASE_DIR%;D:\workspace\lib\down.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\commons-codec\commons-codec\1.4\commons-codec-1.4.jar
set BASE_DIR=%BASE_DIR%;D:\workspace\lib\commons-logging-1.1.1.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\org\jsoup\jsoup\1.6.1\jsoup-1.6.1.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\commons-io\commons-io\1.4\commons-io-1.4.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\apache-log4j\log4j\1.2.15\log4j-1.2.15.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\org\apache\httpcomponents\httpclient\4.1\httpclient-4.1.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\org\apache\httpcomponents\httpcore\4.1\httpcore-4.1.jar
REM set CLASSPATH=%BASE_DIR%;%CLASSPATH%
 
echo %BASE_DIR%

java -classpath %BASE_DIR% com.net.pic.ControllerImpl http://www.test.com/ logging.php?action=login 111111 test d://testpic//pic6// http://a.xfjiayuan.com/forum-25-1.html d://testpic//pic7// d://testpic//pic8// 1
 
REM @echo on
REM java -jar lib/down.jar
REM @echo off
REM set CLASSPATH=%ORIGCLASSPATH%
