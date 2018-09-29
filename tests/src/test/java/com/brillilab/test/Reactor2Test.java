package com.brillilab.test;

import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Reactor2Test {

    /**
     * Function 包 的使用
     */
    @Test
    public void functionTest(){

        Consumer<String> consumer = value ->{
            System.out.println(value);
        };

        Supplier<Integer> supplier = () -> 123;

        BiConsumer<Consumer<String> , String> biConsumer = (callback ,value) -> {
            for (int i=0; i<10; i++){
                callback.accept(value);
            }
        };

        Function<Integer,String> transformation = integer -> ""+integer;

        biConsumer.accept(
                consumer,
                transformation.apply(
                        supplier.get()
                )
        );
    }

    /**
     *  Reactor 2.0 库的Environment 和 Dispatcher
     */
//    @Test
//    public void EnvironmentAndDispatcher (){
//
//        Environment.initialize();
//
//        DispatcherSupplier supplier=Environment.newCachedDispatchers(2);
//
//        Dispatcher d1=supplier.get();
//        Dispatcher d2=supplier.get();
//        Dispatcher d3=supplier.get();
//        Dispatcher d4=supplier.get();
//
//        Assert.isTrue(d1 == d3 && d2 == d4);
//        supplier.shutdown();
//
//        // 创建并注册带3个调度者的新池
//        DispatcherSupplier supplier1 = Environment.newCachedDispatchers(3,"myPool");
//        DispatcherSupplier supplier2 = Environment.cachedDispatchers("myPool");
//
//        Assert.isTrue( supplier1 == supplier2 );
//        supplier1.shutdown();
//    }

    /**
     * Reactor 2.0 库 Steams 使用 进行流式编程
     */
//    @Test
//    public void ReactorStreamTest(){
//
//        Environment.initialize();
//
//        Stream<String> st=Streams.just("Hello","World","!");
//
//        st.dispatchOn(Environment.cachedDispatcher())
//                .map(String::toUpperCase)
//                .consume(s -> System.out.printf("%s greeting = %s%n", Thread.currentThread(), s));
//
//    }

    /**
     * Reactor 2.0 流式编程之流合并 wrap concat 方法的使用
     */
//    @Test
//    public void ReactorStreamTest2(){
//
//        RingBufferProcessor<String> processor=RingBufferProcessor.create();
//
//        Stream<String> st1=Streams.just("hello ");
//        Stream<String> st2=Streams.just("world ");
//
//        //一个针对传入 Publisher.subscribe(Subscriber<T>) 参数的下发 Stream。
//        // 只支持正确使用 Reactive Stream 协议的格式正确的 Publisher：
//        //onSubscribe > onNext* > (onError | onComplete)
//        Stream<String> st3=Streams.wrap(processor);
//
//        //如果一个 Publisher<T> 已经发送了，在处理下一个等待处理的 Publisher<T> 之前要等待这个 onComplete()。
//        //其名称就暗示它对于串联不同的数据源并保持顺序正确，这些方面的作用。
//        Streams.concat(st1,st2,st3)
//                .reduce((prev,next) -> prev + next)
//                .consume(s -> System.out.printf("%s greeting = %s%n", Thread.currentThread(), s));
//
//        processor.onNext("!");
//        processor.onNext("!!!");
//        processor.onComplete();
//    }
}
