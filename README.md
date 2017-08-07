# report-service

plugin-sdk <==> plugins <==> report-service

Report-service with the functionality that allows user to upload a file, process it and receive appropriate report back. 
File process depends on plugin.
All available plugins are loaded by ServiceLoader. 


Jar and plugins location:
 - report-service.jar
 - /plugins  
    - first-plugin.jar
    - second-plugin.jar
    - etc.

To run service: 
java -Dloader.path=plugins/* -jar report-service-0.0.1-SNAPSHOT.jar 


URLs:
- http://localhost:8080/login   (test/test, user/password)
- http://localhost:8080/plugins
- http://localhost:8080/plugins/default-plugin?data=one,two,three
- http://localhost:8080/plugins/first-plugin?data=one,two,three


Java, Spring Boot, plugins architecture, thymeleaf, bootstrap, etc.