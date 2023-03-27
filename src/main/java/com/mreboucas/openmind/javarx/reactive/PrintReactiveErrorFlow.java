package com.mreboucas.openmind.javarx.reactive;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PrintReactiveErrorFlow {

    public static void main(String[] args) throws InterruptedException {
        Flowable.interval(1,1, TimeUnit.SECONDS)
                .map(PrintReactiveErrorFlow::transform)
                .subscribe(PrintReactiveErrorFlow::process,
                           PrintReactiveErrorFlow::dealWithError);
        TimeUnit.SECONDS.sleep(10);
    }

    private static void dealWithError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private static void process(Long number) {
        System.out.println("Received number " + number);
    }

    private static <@NonNull R> Long transform(Long number) {
        if(new Random().nextDouble() < 0.3) {
            throw new RuntimeException("Ops!");
        }
        return number * 2;
    }
}
