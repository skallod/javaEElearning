javac Run.java VerboseLoader.java
rem set our class loader
java VerboseLoader Run StringBuilder 1000
rem javaassist class loader interceptor
javac -cp javassist.jar;. JavassistRun.java
java -cp javassist.jar;. JavassistRun Run StringBuilder 1000