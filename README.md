# MagnetoWantsYou
App with Spring boot and  hibernate that publish two methods one to storage data on postgres database and other to recover it,

Development environment
Hardware 
Dell Inspirion 15 5000 Series
Windows 64bit
RAM 8 GB

Software
Java 1.8.0_191
Apache Maven 3.3.9
Spring boot 2.1.2.RELEASE
Bootstrap 4.2.1
Postgresql 9.4

Run Instructions
1 - create a database on posgresql using this sql 

CREATE TABLE mutants
(
  id_mutant bigint NOT NULL,
  is_mutant boolean,
  nitrogenous_base character varying(255),
  CONSTRAINT mutants_pkey PRIMARY KEY (id_mutant)
);

2 - change database username and password on applicartion.properties

You can find the file in 

MagnetoWantsYou\src\main\resources 

The properties that you have to change are those, the local database username and password respectively 

spring.datasource.username
spring.datasource.password

3 - Run the code

To run the code you have to run these three command on command line prompt

      mvn clean package
      mvn dependency:tree
      mvn spring-boot:run

The code creates automatically the table mutants on database due to JPA mapping

Then you can use the application on browser on URL

  http://localhost:8080/
