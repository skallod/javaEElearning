package agent;

public class AgentTester {
    public static void main(String[] args) {
        System.out.println("Hello! I`m agent tester");
        A a = new A();
        B b = new B();
        C c = null;
        long size = Agent007.getSize(b);
        System.out.println("size = " + size);
    }
}
class A {};
class B {
    long b;
};
class C {};

