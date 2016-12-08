package ru.galuzin;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Created by galuzin on 22.11.2016.
 */
public class SecurityInterceptor {
    @Resource
    private SessionContext sessionContext;
    @AroundInvoke
    public Object checkUserRole(InvocationContext context)
            throws Exception {
        if(!sessionContext.isCallerInRole("CSR")) {
            throw new SecurityException("No permission to cancel bid.");
        }
        return context.proceed();
    }
}
