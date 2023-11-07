package leesangho.adtechstudy.webfluxapplication.controller;

import leesangho.adtechstudy.webfluxapplication.dto.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseResponse<Void>> saveBoardItem() {
        return Mono.just(BaseResponse.empty("테스트"));
    }
}
