### Business Entity Library (BEL) Repository

This is a data access library and enterprise infrastructure framework that has 
a considerable number of enterprise entity classes and utilities that are used 
in a typical enterprise. It also facilitates consistent and safe access to data 
stores such as files and relational databases...

### Example Installation

mvn install:install-file -DgroupId=jm.com.dpbennett -DartifactId=business-entity-library -Dversion=1.0.0 -Dfile=/home/desbenn/Projects/business-entity-library/target/business-entity-library-1.0.0-SNAPSHOT.jar -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=.  -DcreateChecksum=true

#### Note
- the POM file from the master branch should be used with '-DpomFile=YOUR_POM_FILE_FULL_NAME' 
instead generating one with '-DgeneratePom=true'.