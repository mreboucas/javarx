package com.mreboucas.openmind.javarx.reactive;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;

import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static io.reactivex.rxjava3.schedulers.Schedulers.computation;


/**
 * Strategy to tell to publisher that program is unable to process data at that time.
 */
public class Backpressure {

    public static void main(String[] args) throws InterruptedException {
        Flowable.create(Backpressure::emit,
                        BackpressureStrategy.BUFFER //Suporta todos os dados em buffer para processar posteriormente (fila fica grande)
//                        BackpressureStrategy.DROP //Caso do processamento não suportar, perde ele - ou seja, ele não é emitido (se perda de dados não importar, esse é o caminho).
//                        BackpressureStrategy.ERROR
//                        BackpressureStrategy.LATEST
//                        BackpressureStrategy.MISSING
                )
                .observeOn(computation(), true, 2)
                .subscribe(Backpressure::process);

         sleep(10000);
    }

    private static void process(Object number) throws InterruptedException {
        System.out.println("Processing " + number);
        sleep(1000);
    }

    private static void emit(@NonNull FlowableEmitter<Integer> emitter) {
        IntStream.rangeClosed(1,20)
                .forEach(n -> {
                    System.out.println("Emitting " +n);
                    emitter.onNext(n);
                    sleep(500);
                });
        emitter.onComplete();
    }

    private static void sleep(int milli) {
        try {
            MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
