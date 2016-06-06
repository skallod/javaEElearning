package ru.galuzin;

/**
 * Created by galuzin on 03.06.2016.
 */
public class Client {
    void someMethod(){
        showWaitWindow();
        for(SomeForm form : forms){
            form.addServletFinishJobHandler(
                    new ServletFinishJobHandler(){
                        void jobFinished(){
                            //TODO do thomething
                        }
                    }
            );
            form.submit();
        }
        hideWaitWindow();
    }
}
