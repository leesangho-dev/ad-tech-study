package leesangho.adtechstudy.webflux.controller;

import io.swagger.v3.oas.annotations.Operation;
import leesangho.adtechstudy.webflux.dto.BaseResponse;
import leesangho.adtechstudy.webflux.dto.BoardDto;
import leesangho.adtechstudy.webflux.usecase.FindBoardItemUseCase;
import leesangho.adtechstudy.webflux.usecase.SaveBoardItemUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private final SaveBoardItemUseCase saveBoardItemUseCase;

    private final FindBoardItemUseCase findBoardItemUseCase;

    public BoardController(SaveBoardItemUseCase saveBoardItemUseCase, FindBoardItemUseCase findBoardItemUseCase) {
        this.saveBoardItemUseCase = saveBoardItemUseCase;
        this.findBoardItemUseCase = findBoardItemUseCase;
    }

    @Operation(tags = "게시판", description = "게시글 등록", summary = "게시글 등록")
    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseResponse<BoardDto.ItemIdResponse>> saveBoardItem(@RequestBody BoardDto.SaveItemRequest saveItemRequest) {
        return saveBoardItemUseCase.execute(saveItemRequest)
                .map(itemIdResponse -> BaseResponse.createOf("게시글을 등록 하였습니다.", itemIdResponse));
    }

    @Operation(tags = "게시판", description = "게시글 조회", summary = "게시글 조회")
    @GetMapping(value = "/item/{id}")
    public Mono<BaseResponse<BoardDto.FindItemResponse>> findBoardItem(@PathVariable("id") String boardItemId) {
        return findBoardItemUseCase.execute(boardItemId)
                .map(itemResponse -> BaseResponse.ok("게시글을 조회 하였습니다.", itemResponse));
    }
}
