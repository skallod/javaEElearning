rem mvn archetype:generate maven-archetype-simple

mvn -B archetype:generate ^
-DgroupId=ru.galuzin.tomcat_basic_auth ^
-DartifactId=tomcat_basic_auth2 ^
-DarchetypeArtifactId=maven-archetype-webapp

rem -DarchetypeGroupId=org.apache.maven.archetypes ^
rem maven-archetype-j2ee-simple
rem maven-archetype-webapp
rem maven-archetype-quickstart


