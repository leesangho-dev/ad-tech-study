package leesangho.adtechstudy.webflux.infra.blcokhound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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





}
