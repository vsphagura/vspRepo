-Create a dir anywhere call ContactList then create a dir structure 
	ContactList/src/main/java
	ContactList/src/main/webapp/WEB-INF
-create a build.gradle
-run
	ContactList> gradle eclipse
-this will create a eclipse project which can be then imported in eclipse
-This is a project without XML configurations
- To build this project just use this from under ContactList dir:
	gradle clean build cpWar OR
	gradle cpWar (Note the 'mustRunAfter' cmd in gradle)
-This can be run by putting Project1.war which will be created in build dir to tomcat/webapp
 	http://localhost:8080/ContactList/home
-Important to note that PU has to be spcified if there are multiple units
-While declaring emf you have to make sure to call setPackageToScan()
-Because resources is defined in addResourceHandlers, use:
		http://localhost:8080/ContactList/resources/index.html
- Test GET:
		http://localhost:8080/ContactList/contact/7
