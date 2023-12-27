package leesangho.adtechstudy.webflux.infra.blcokhound;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@DisplayName("blocking 탐지 테스트")
class BlockingTest {

    Logger log = LoggerFactory.getLogger(BlockingTest.class);

    ExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(3);
    }

    @DisplayName("blocking 로직을 blocking 쓰레드에서 테스트")
    @Test
    void blocking_thread_blocking_logic_call() {
        // Given

        // When
        executorService.execute(this::blockingLogicTask);

        // Then

    }

    void blockingLogicTask() {
        log.info("blocking logic task");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void blocking_thread_nonblocking_logic_call() {
    }

    @DisplayName("")
    @Test
    void nonblocking_thread_blocking_logic_call() {
        // Given

        // When
        Mono.fromSupplier(() -> {
                    log.info("publisher task");
                    return 1;
                })
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(it -> blockingLogicTask())
                .block();

        // Then
    }

  @Test
  void name() throws InterruptedException {
    Hooks.onOperatorDebug();

    ExecutorService executorService = Executors.newFixedThreadPool(30);
    ExecutorService executorService2 = Executors.newFixedThreadPool(3);
//
//        Flux.range(0, 30)
//            .parallel()
//            .runOn(Schedulers.parallel())
//            .flatMap(integer -> Flux.range(0, 1)
//                    .publishOn(Schedulers.fromExecutor(executorService))
//                    .map(value -> {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                        return value;
//                    })
//                    .log("blocking")
//            ).log("flatmap")
//            .map(integer -> {
//                log.info("map");
//                return integer;
//            })
//            .subscribe();
//
//        Thread.sleep(10000);

    Flux.range(0, 30)
        .log("take")
        .flatMap(id -> {
          return Flux.range(0, 1)
              .publishOn(Schedulers.fromExecutor(executorService))
              .map(value -> {
                try {
                  Thread.sleep(1000);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                return value;
              })
              .log("blocking")
              ;
        })
        .log("flatmap")
        .map(integer -> {
          log.info("map");
          return integer;
        })
        .subscribeOn(Schedulers.parallel())
        .subscribe();

    Thread.sleep(10000);

//        Flux.range(0, 10)
//                .log("take")
//                .map(id -> {
//                    Mono.fromSupplier(() -> {
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                return Mono.just(id);
//                            })
//                            .log()
//                            .subscribeOn(Schedulers.fromExecutor(executorService))
//                            .subscribe()
//                            ;
//                    log.info("{}", id);
//                    return id;
//                })
//                .log("flatmap")
//                .map(integer -> integer)
//                .subscribeOn(Schedulers.parallel())
//                .blockLast();

//        Thread.sleep(10000);
  }
}
