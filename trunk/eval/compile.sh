#!/bin/bash

cd jetty-distribution-8.1.16.v20140903/;
cd webapps/;
cd EnseirbWebXMLWebapp/;
javac -sourcepath src/ -encoding cp1252 -d WEB-INF/classes/ -cp "WEB-INF/lib/*:../../lib/*" src/fr/enseirb/webxml/servlet//*.java;
cd ../..;
java -jar start.jar;
cd ..;
java -jar EnseirbWebXMLEval1.jar EnseirbWebXMLWebapp 5500 result;

