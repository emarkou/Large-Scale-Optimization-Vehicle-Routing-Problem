all: build

build:
	mvn -T1.5C clean package -DskipTests

run: 
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.App

run-all: 	
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.App 1 30 10 
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.App 2 30 10 
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.App 3 30 10
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.App 4 30 10

test: 
	mvn test

offline:
	mvn -T1.5C -o clean package 

clean: 
	mvn clean

