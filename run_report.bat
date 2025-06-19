@echo off
echo Running tests and sending report...

REM Clean and compile
call mvn clean compile

REM Run tests
call mvn test

REM Send report
call mvn exec:java -Dexec.mainClass="generic.SendReport"

echo Done! 