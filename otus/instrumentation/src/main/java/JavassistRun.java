
import javassist.*;

public class JavassistRun
{
    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                
                // set up class loader with translator
                Translator xlat = new VerboseTranslator();
                ClassPool pool = ClassPool.getDefault(xlat);
                Loader loader = new Loader(pool);
                    
                // invoke "main" method of target class
                String[] pargs = new String[args.length-1];
                System.arraycopy(args, 1, pargs, 0, pargs.length);
                loader.run(args[0], pargs);
                
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            
        } else {
            System.out.println
                ("Usage: JavassistRun main-class args...");
        }
    }
    
    public static class VerboseTranslator implements Translator
    {
        public void start(ClassPool pool) {}

        public void onWrite(ClassPool pool, String cname) {
            System.out.println("onWrite called for " + cname);
        }
    }
} 
