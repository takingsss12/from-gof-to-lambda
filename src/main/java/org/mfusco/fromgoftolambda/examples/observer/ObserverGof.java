/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mfusco.fromgoftolambda.examples.observer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObserverGof {

    interface Listener {
        void onEvent(Object event);
    }

    public static class Observable {
        private final Map<Object, Listener> listeners = new ConcurrentHashMap<>();

        public void register(Object key, Listener listener) {
            listeners.put(key, listener);
        }

        public void unregister(Object key) {
            listeners.remove(key);
        }

        public void sendEvent(Object event) {
            for (Listener listener : listeners.values()) {
                listener.onEvent( event );
            }
        }
    }

    public static class Observer1 {
        Observer1(Observable observable) {
            observable.register( this, new Listener() {
                @Override
                public void onEvent( Object event ) {
                    System.out.println(event);
                }
            } );
        }
    }

    public static class Observer2 implements Listener {
        Observer2(Observable observable) {
            observable.register( this, this );
        }
        @Override
        public void onEvent( Object event ) {
            System.out.println(event);
        }
    }

    public static void main( String[] args ) {
        Observable observable = new Observable();
        new Observer1( observable );
        new Observer2( observable );

        observable.sendEvent( "Hello World!" );
    }
}
