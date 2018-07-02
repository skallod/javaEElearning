javac ./agent/Agent007.java ./agent/ClassTransformer.java ^./agent/AgentTester.java
jar -cvfm Agent007.jar agent/manifest.mf  agent/Agent007.class agent/ClassTransformer.class
java -javaagent:Agent007.jar agent.AgentTester