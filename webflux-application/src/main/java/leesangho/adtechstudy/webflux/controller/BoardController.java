package leesangho.adtechstudy.webflux.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import leesangho.adtechstudy.webflux.dto.BaseResponse;
import leesangho.adtechstudy.webflux.dto.BoardDto;
import leesangho.adtechstudy.webflux.usecase.DeleteBoardItemUseCase;
import leesangho.adtechstudy.webflux.usecase.FindAllPageBoardItemsUseCase;
import leesangho.adtechstudy.webflux.usecase.FindBoardItemUseCase;
import leesangho.adtechstudy.webflux.usecase.SaveBoardItemUseCase;
import leesangho.adtechstudy.webflux.usecase.UpdateBoardItemUseCase;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private final SaveBoardItemUseCase saveBoardItemUseCase;

    private final FindBoardItemUseCase findBoardItemUseCase;

    private final DeleteBoardItemUseCase deleteBoardItemUseCase;

    private final UpdateBoardItemUseCase updateBoardItemUseCase;

    private final FindAllPageBoardItemsUseCase findAllPageBoardItemsUseCase;

    public BoardController(SaveBoardItemUseCase saveBoardItemUseCase,
        FindBoardItemUseCase findBoardItemUseCase,
        DeleteBoardItemUseCase deleteBoardItemUseCase,
        UpdateBoardItemUseCase updateBoardItemUseCase,
        FindAllPageBoardItemsUseCase findAllPageBoardItemsUseCase) {
        this.saveBoardItemUseCase = saveBoardItemUseCase;
        this.findBoardItemUseCase = findBoardItemUseCase;
        this.deleteBoardItemUseCase = deleteBoardItemUseCase;
        this.updateBoardItemUseCase = updateBoardItemUseCase;
        this.findAllPageBoardItemsUseCase = findAllPageBoardItemsUseCase;
    }

    @Operation(tags = "게시판", description = "게시글 등록", summary = "게시글 등록")
    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BaseResponse<BoardDto.ItemIdResponse>> saveBoardItem(
        @RequestBody BoardDto.SaveItemRequest saveItemRequest) {
        return saveBoardItemUseCase.execute(saveItemRequest)
            .map(itemIdResponse -> BaseResponse.createOf("게시글을 등록 하였습니다.", itemIdResponse));
    }

    @Operation(tags = "게시판", description = "게시글 조회", summary = "게시글 조회")
    @GetMapping(value = "/item/{id}")
    public Mono<BaseResponse<BoardDto.FindItemResponse>> findBoardItem(
        @PathVariable("id") String boardItemId) {
        return findBoardItemUseCase.execute(boardItemId)
            .map(itemResponse -> BaseResponse.ok("게시글을 조회 하였습니다.", itemResponse));
    }

    @Operation(tags = "게시판", description = "게시글 삭제", summary = "게시글 삭제")
    @DeleteMapping(value = "/item/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<BaseResponse<Void>> deleteBoardItem(@PathVariable("id") String boardItemId) {
        return deleteBoardItemUseCase.execute(boardItemId)
            .then(Mono.fromCallable(() -> BaseResponse.empty("게시글을 삭제 하였습니다.")));
    }

    @Operation(tags = "게시판", description = "게시글 수정", summary = "게시글 수정")
    @PutMapping(value = "/item/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BaseResponse<BoardDto.ItemIdResponse>> updateBoardItem(
        @PathVariable("id") String boardItemId,
        @RequestBody BoardDto.UpdateItemRequest updateItemRequest) {
        return updateBoardItemUseCase.execute(boardItemId, updateItemRequest)
            .map(itemIdResponse -> BaseResponse.ok("게시글을 수정하였습니다.", itemIdResponse));

    }

    @Operation(tags = "게시판", description = "게시글 목록", summary = "게시글 목록")
    @GetMapping(value = {"/item", "/item/list"})
    @PageableAsQueryParam
    public Flux<BoardDto.FindItemResponse> findAllPageBoardItems(
        @Parameter(hidden = true) Pageable pageable) {
        return findAllPageBoardItemsUseCase.execute(pageable);
    }
}
