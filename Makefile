all: build

build:
	mvn -T1.5C clean package -DskipTests

run: 
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.VRP 1 30 10

run-all: 	
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.VRP 1 30 10
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.VRP 2 30 10
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.VRP 3 30 10
	java -cp target/vrp-1.0-SNAPSHOT.jar com.vrp.app.VRP 4 30 10

test: 
	mvn test

offline:
	mvn -T1.5C -o clean package 

clean: 
	mvn clean

