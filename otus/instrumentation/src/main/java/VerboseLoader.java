
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class VerboseLoader extends URLClassLoader
{
    protected VerboseLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
    
    public Class loadClass(String name) throws ClassNotFoundException {
        System.out.println("loadClass: " + name);
        return super.loadClass(name);
    }

    protected Class findClass(String name) throws ClassNotFoundException {
        Class clas = super.findClass(name);
        System.out.println("findclass: loaded " + name +
            " from this loader");
        return clas;
    }

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (args.length >= 1) {
            try {
                
                // get paths to be used for loading
                ClassLoader base = ClassLoader.getSystemClassLoader();
                URL[] urls;
                if (base instanceof URLClassLoader) {
                    urls = ((URLClassLoader)base).getURLs();
                } else {
                    urls = new URL[] { new File(".").toURI().toURL() };
                }
                
                // list the paths actually being used
                System.out.println("Loading from paths:");
                for (int i = 0; i < urls.length; i++) {
                    System.out.println(" " + urls[i]);
                }
                
                // load the target class using custom class loader
                VerboseLoader loader = new VerboseLoader(urls, base.getParent());
                Class clas = loader.loadClass(args[0]);
                System.out.println("after load class");
                // invoke the "main" method of the application class
                Class[] ptypes = new Class[] { args.getClass() };
                Method main = clas.getDeclaredMethod("main", ptypes);
                String[] pargs = new String[args.length-1];
                System.arraycopy(args, 1, pargs, 0, pargs.length);
                Thread.currentThread().setContextClassLoader(loader);
                System.out.println("before main invoke");
                main.invoke(null, new Object[] { pargs });
                
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            
        } else {
            System.out.println
                ("Usage: VerboseLoader main-class args...");
        }
    }
} 
