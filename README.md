# Pixel Tracker
Built using spring-boot and Java11.

**Build**:

`mvn clean install
`

**Run**: when build is complete then run. I have added test profile to use h2 database

`mvn spring-boot:run -Dspring-boot.run.profiles=test 
`

**Use Case**:

navigate to http://localhost:8080/contact.html, http://localhost:8080/about.html

above are simple html pages to demonstrate registering visits to the page.

to get visits statics run following hit http://localhost:8080/api/v1/tracks
