javac StringBuilder.java
javac -cp javassist.jar;. JassistTiming.java
rem modify bytecode
java -cp javassist.jar;. JassistTiming StringBuilder buildString
java StringBuilder