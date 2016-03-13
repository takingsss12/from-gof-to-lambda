package org.mfusco.fromgoftolambda.examples.observer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ObserverLambda {
    public static class Observable {
        private final Map<Object, Consumer<Object>> listeners = new ConcurrentHashMap<>();

        public void register(Object key, Consumer<Object> listener) {
            listeners.put(key, listener);
        }

        public void unregister(Object key) {
            listeners.remove(key);
        }

        public void sendEvent(Object event) {
            listeners.values().forEach( listener -> listener.accept( event ) );
        }
    }

    public static class Observer1 {
        Observer1(Observable observable) {
            observable.register( this, System.out::println );
        }
    }

    public static void main( String[] args ) {
        Observable observable = new Observable();
        new Observer1( observable );

        observable.sendEvent( "Hello World!" );
    }
}
