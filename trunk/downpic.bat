@echo off
set ORIGCLASSPATH=%CLASSPATH%
if not "%JAVA_HOME%" == "" goto gotJavaHome
echo You must set JAVA_HOME to point to your Java SDK install directory
goto cleanup
:gotJavaHome

echo %BASE_DIR%
 
set BASE_DIR="%JAVA_HOME%\lib\tools.jar"
set BASE_DIR=%BASE_DIR%;D:\workspace\lib\down.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\commons-httpclient\commons-httpclient\3.1\commons-httpclient-3.1.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\commons-codec\commons-codec\1.4\commons-codec-1.4.jar
set BASE_DIR=%BASE_DIR%;D:\workspace\lib\commons-logging-1.1.1.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\org\jsoup\jsoup\1.6.1\jsoup-1.6.1.jar
set BASE_DIR=%BASE_DIR%;D:\repo\maven_repository\commons-io\commons-io\1.4\commons-io-1.4.jar
 
REM set CLASSPATH=%BASE_DIR%;%CLASSPATH%
 
echo %BASE_DIR%

java -classpath %BASE_DIR% com.net.pic.ControllerImpl http://a.xfjiayuan.com/ http://a.xfjiayuan.com/logging.php?action=login 790521 scholerscn d://pic2// http://a.xfjiayuan.com/forum-25-1.html d://pic2//pic2// d://pic2//pic3//
 
REM @echo on
REM java -jar lib/down.jar
REM @echo off
REM set CLASSPATH=%ORIGCLASSPATH%
