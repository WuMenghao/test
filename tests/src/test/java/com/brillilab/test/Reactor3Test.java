package com.brillilab.test;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import com.brillilab.reactor.MyEventListener;

import java.util.List;
import java.util.function.Function;

public class Reactor3Test {

    /**
     * Reactor 3.0  Flux 的发布与订阅
     */
    @Test
    public void FluxSubscribe(){
        Flux<String> seq=Flux.just("foo","bar","foobar");
        seq.subscribe(i -> System.out.printf("%s%n",i));
    }

    /**
     * Reactor 3.0 Flux 的错误处理与完成后的处理
     */
    @Test
    public void FluxErrorDone(){
        Flux<Integer> ints=Flux.range(1,3)
                .map(i -> {
                    if(i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });

        //subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer, Runnable completeConsumer)
        ints.subscribe(
                i -> System.out.printf("%s%n",i),
                error -> System.err.printf("Error : %s%n",error),   //错误处理
                () -> {System.out.println("Done");}                 //完成后的处理
                );
    }

    /**
     * Reactor 3.0 Flux 配置订阅者采用背压方式
     */
    @Test
    public void FluxSubscriber(){
        Flux<String> seq=Flux.just("foo","bar","foobar");

        //subscribe(Subscriber<? super T> actual)
        seq.map(String::toUpperCase).subscribe(new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("hookOnSubscribe");
                request(1);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.printf("hookOnNext: %s%n",value);
                request(1);
            }
        });
    }

    /**
     * Reactor 3.0 Flux 以create方式绑定监听器 （前端编程中可用到）
     */
    @Test
    public void creatFlux(){


        Flux<Object> bridge=Flux.create(fluxSink -> {
            new MyEventListener<String>() {

                @Override
                public void onDataChunk(List<String> chunk) {
                    chunk.forEach(fluxSink::next);
                }

                @Override
                public void processComplete() {
                    fluxSink.complete();
                }
            };
        });

        bridge.subscribe(
                i -> System.out.printf("%s%n",i),
                error -> System.err.printf("Error : %s%n",error),
                () -> {System.out.println("Done");}
        );

    }

    /**
     * Reactor 3.0 Flux 以创建push方式的Flux
     */
    @Test
    public void createPushModleFlux(){

        Flux<Object> bridge=Flux.push(fluxSink -> {
            new MyEventListener<String>() {

                @Override
                public void onDataChunk(List<String> chunk) {
                    chunk.forEach(fluxSink::next);
                }

                @Override
                public void processComplete() {
                    fluxSink.complete();
                }
            };
        });

        bridge.subscribe(
                i -> System.out.printf("%s%n",i),
                error -> System.err.printf("Error : %s%n",error),
                () -> {System.out.println("Done");}
        );
    }
}
