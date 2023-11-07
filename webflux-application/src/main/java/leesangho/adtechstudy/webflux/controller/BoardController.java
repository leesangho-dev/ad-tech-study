package leesangho.adtechstudy.webflux.controller;

import leesangho.adtechstudy.webflux.dto.BaseResponse;
import leesangho.adtechstudy.webflux.dto.BoardDto;
import leesangho.adtechstudy.webflux.usecase.SaveBoardItemUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private final SaveBoardItemUseCase saveBoardItemUseCase;

    public BoardController(SaveBoardItemUseCase saveBoardItemUseCase) {
        this.saveBoardItemUseCase = saveBoardItemUseCase;
    }

    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseResponse<BoardDto.ItemIdResponse>> saveBoardItem(@RequestBody BoardDto.SaveItemRequest saveItemRequest) {
        return saveBoardItemUseCase.execute(saveItemRequest)
                .map(itemIdResponse -> BaseResponse.createOf("게시글을 등록 하였습니다.", itemIdResponse));
    }
}
