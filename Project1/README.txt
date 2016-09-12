-Create a dir anywhere call Project1 then create a dir structure 
	Project1/src/main/java
	Project1/src/main/webapp/WEB-INF
-create a build.gradle
-run
	project1> gradle eclipse
-this will create a eclipse project which can be then imported in eclipse
-This is a project without XML configurations
-This can be run by putting Project1.war which will be created in build dir to tomcat/webapp
 	http://localhost:8080/Project1/home