call mvn clean:clean
@@@call mvn package -Dtest assembly:assembly -DfailIfNoTests=false

call mvn -U clean install -DskipTests=true
@pause