
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Run
{
    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                
                // load the target class to be run
                Class clas = Run.class.getClassLoader().loadClass(args[0]);
                    
                // invoke the "main" method of the application class
                Class[] ptypes = new Class[] { args.getClass() };
                Method main = clas.getDeclaredMethod("main", ptypes);
                String[] pargs = new String[args.length-1];
                System.arraycopy(args, 1, pargs, 0, pargs.length);
                main.invoke(null, new Object[] { pargs });
                
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            
        } else {
            System.out.println
                ("Usage: Run main-class args...");
        }
    }
} 
