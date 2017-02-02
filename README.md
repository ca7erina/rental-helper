# Rental Helper
This project is a group work of people at Trinity College Dublinto build web application that helps people find accommodation in group easily.
# How to run
1. Install `sbt` package:
   + Ubuntu: See [install-requirements.sh](scripts/install-requirements.sh)
   + Mac OS X: See [Mac OS X](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Mac.html)
2. Create database named `default` in your MySQL server.
3. Rename the file [application.conf.example](conf/application.conf.example) to [application.conf](conf/application.conf)
4. Specify your MySQL credentials in [application.conf](conf/application.conf)
4. Run
```
sbt run
```
5. Open browser and go the address: http://localhost:9000
