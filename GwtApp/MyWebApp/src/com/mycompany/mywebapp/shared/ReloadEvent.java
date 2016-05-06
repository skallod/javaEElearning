/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: BOF Unifest
 *
 * $Id: $
 *****************************************************************/
//package com.mycompany.mywebapp.shared;
//
//import com.google.gwt.event.shared.GwtEvent;
//
//public class ReloadEvent<T> extends GwtEvent<ReloadHandler<T>> {
//    private static Type<ReloadHandler<?>> TYPE;
//
//    public static <T> void fire(final HasReloadHandler<T> source) {
//        if (TYPE != null) {
//            ReloadEvent<T> event = new ReloadEvent<T>();
//            source.fireEvent(event);
//        }
//    }
//
//    public static Type<ReloadHandler<?>> getType() {
//        if (TYPE == null) {
//            TYPE = new Type<ReloadHandler<?>>();
//        }
//        return TYPE;
//    }
//
//    @Override
//    protected void dispatch(final ReloadHandler<T> handler) {
//        handler.onReload(this);
//    }
//
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    @Override
//    public Type<ReloadHandler<T>> getAssociatedType() {
//        return (Type) TYPE;
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public T getSource() {
//        return (T) super.getSource();
//    }
//}
