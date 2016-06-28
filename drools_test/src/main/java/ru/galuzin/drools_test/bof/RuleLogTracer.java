//package ru.galuzin.drools_test.bof;
//
//import com.gridnine.bof.common.model.system.Money;
//import com.gridnine.bof.common.util.ObjectFilter;
//import com.gridnine.bof.server.util.ObjectTrace;
//import org.apache.commons.logging.LogFactory;
//
//import java.beans.PropertyDescriptor;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@SuppressWarnings("nls")
//public class RuleLogTracer implements Serializable {
//    private static final long serialVersionUID = -8831750310061785592L;
//
//    private static final ObjectFilter<PropertyDescriptor> RULE_TRACE_FILTER =
//        new ObjectFilter<PropertyDescriptor>() {
//            @Override
//            public boolean accept(final PropertyDescriptor descr) {
//                try {
//                    Class<?> propertyType = descr.getPropertyType();
//                    if ((propertyType == null)
//                    /*|| Class.class.isAssignableFrom(propertyType)*/) {
//                        return false;
//                    }
//                    if (propertyType.isPrimitive()) {
//                        return true;
//                    }
//                    if (Class.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (String.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (Integer.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (Long.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (Double.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (BigDecimal.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (Date.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (Boolean.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (Money.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (Iterable.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    if (Enum.class.isAssignableFrom(propertyType)) {
//                        return true;
//                    }
//                    return false;
//                } catch (Throwable t) {
//                    return false;
//                }
//            }
//        };
//
//    private final SimpleDateFormat sdf = new SimpleDateFormat(
//        "yyyy-MM-dd HH:mm:ss:SSS");
//
//    private final StringBuilder sb = new StringBuilder();
//
//    private boolean disabled;
//
//    public void setDisabled(final boolean value) {
//        disabled = value;
//        if (disabled) {
//            sb.setLength(0);
//            sb.trimToSize();
//        }
//    }
//
//    public void traceEnteringMethod(final Class<?> cls, final String methodName) {
//        if (disabled) {
//            return;
//        }
//        traceEvent(
//            "method enter",
//            String.format("class = %s, method = %s",
//                cls != null ? cls.getName() : null, methodName), null);
//    }
//
//    public void traceExitingMethod(final Class<?> cls, final String methodName) {
//        if (disabled) {
//            return;
//        }
//        traceEvent(
//            "method exit",
//            String.format("class = %s, method = %s",
//                cls != null ? cls.getName() : null, methodName), null);
//    }
//
//    public void traceLogic(final String message) {
//        if (disabled) {
//            return;
//        }
//        traceEvent("logic", message, null);
//    }
//
//    public void traceError(final String message, final Throwable e) {
//        if (disabled) {
//            return;
//        }
//        traceEvent("error", message, e);
//    }
//
//    public void traceWarn(final String message, final Throwable e) {
//        if (disabled) {
//            return;
//        }
//        traceEvent("warn", message, e);
//    }
//
//    private void traceEvent(final String logType, final String message,
//                            final Throwable exc) {
//        if (disabled) {
//            return;
//        }
//        if (sb.length() > 0) {
//            sb.append("\r\n");
//        }
//        sb.append(sdf.format(new Date())).append(
//            String.format(" [%s]: %s", logType, message));
//        if (exc != null) {
//            sb.append("\r\n").append(TextUtil.getExceptionStackTrace(exc));
//        }
//    }
//
//    public void traceObject(final String objectName, final Object object,
//                            final ObjectFilter<PropertyDescriptor> filter) {
//        if (disabled) {
//            return;
//        }
//        if (sb.length() > 0) {
//            sb.append("\r\n");
//        }
//        sb.append(sdf.format(new Date())).append(
//            String.format(" [trace of object]: %s\r\n", objectName));
//        try {
//            ObjectTrace.trace(sb, object, filter);
//        } catch (Exception e) {
//            sb.append(String.format("unable to trace object:\r\n %s",
//                TextUtil.getExceptionStackTrace(e)));
//            LogFactory.getLog(getClass()).error(
//                "unable to trace object " + object, e);
//        }
//    }
//
//    public void traceObject(final String objectName, final Object object) {
//        if (disabled) {
//            return;
//        }
//        traceObject(objectName, object, RULE_TRACE_FILTER);
//    }
//
//    public String getTrace() {
//        return sb.toString();
//    }
//}
