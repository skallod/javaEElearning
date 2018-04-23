package ru.rearitem;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class SparkServerRun {
        public static void main(String[] args) {
            staticFiles.externalLocation("src/main/webapp/");
            final int PORT = 44333;
            port(PORT);
            get("/hello", (req, res) -> "Hello World");
            System.out.println("http:/localhost:"+PORT);
        }
}
