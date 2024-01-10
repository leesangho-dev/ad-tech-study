package leesangho.adtechstudy.observation.webflux.infra.filter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBuffer.ByteBufferIterator;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: 직접 구현하여 로깅을 테스트 해보기 위해 작성한 코드 입니다.
//@Component
@Slf4j
public class CustomResponseLogFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Mono<ServerWebExchange> logMono = Mono.just(exchange)
            .doOnNext(this::printRequestLog)
            .map(se -> se.mutate()
                .response(new LoggingResponseDecorator(se))
                .build());

        return logMono.flatMap(chain::filter);
    }

    private void printRequestLog(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        log.info("request method: {} uri: {} params: {} body: {}", request.getMethod(),
            request.getURI(),
            makeQueryParamString(request), "");
    }

    private String makeQueryParamString(ServerHttpRequest request) {
        return request.getQueryParams()
            .entrySet()
            .stream()
            .map(stringListEntry -> stringListEntry.getKey() + "=" + stringListEntry.getValue())
            .collect(Collectors.joining("&"));
    }

    private static class LoggingResponseDecorator extends ServerHttpResponseDecorator {

        private final FastByteArrayOutputStream bodyOutputStream = new FastByteArrayOutputStream();

        public LoggingResponseDecorator(ServerWebExchange serverWebExchange) {
            super(serverWebExchange.getResponse());
            beforeCommit(() -> {
                log.info("response status: {} body: {}", getStatusCode(),
                    bodyToString());
                return Mono.empty();
            });
        }

        private String bodyToString() {
            return bodyOutputStream.toString();
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            Flux<DataBuffer> bodyBufferWrapper = Flux.from(body)
                .map(dataBuffer -> copyBodyBuffer(bodyOutputStream, dataBuffer));

            return super.writeWith(bodyBufferWrapper);
        }

        @Override
        public Mono<Void> writeAndFlushWith(
            Publisher<? extends Publisher<? extends DataBuffer>> body) {
            Flux<Flux<DataBuffer>> bodyBufferWrapper = Flux.from(body)
                .map(publisher -> Flux.from(publisher)
                    .map(buffer -> copyBodyBuffer(bodyOutputStream, buffer)));

            return super.writeAndFlushWith(bodyBufferWrapper);
        }

        private DataBuffer copyBodyBuffer(FastByteArrayOutputStream bodyStream, DataBuffer buffer) {
            try (WritableByteChannel writableByteChannel = Channels.newChannel(bodyStream);
                ByteBufferIterator byteBufferIterator = buffer.readableByteBuffers()) {
                ByteBuffer readOnlyBuffer = byteBufferIterator.next().asReadOnlyBuffer();
                writableByteChannel.write(readOnlyBuffer);
                return buffer;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
