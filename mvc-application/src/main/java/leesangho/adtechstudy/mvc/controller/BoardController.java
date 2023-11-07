package leesangho.adtechstudy.mvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import leesangho.adtechstudy.mvc.dto.BaseResponse;
import leesangho.adtechstudy.mvc.dto.BoardDto;
import leesangho.adtechstudy.mvc.usecase.*;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private final SaveBoardItemUseCase saveBoardItemUseCase;

    private final FindBoardItemUseCase findBoardItemUseCase;

    private final DeleteBoardItemUseCase deleteBoardItemUseCase;

    private final UpdateBoardItemUseCase updateBoardItemUseCase;

    private final FindAllPageBoardItemUseCase findAllPageBoardItemUseCase;

    public BoardController(SaveBoardItemUseCase saveBoardItemUseCase, FindBoardItemUseCase findBoardItemUseCase, DeleteBoardItemUseCase deleteBoardItemUseCase, UpdateBoardItemUseCase updateBoardItemUseCase, FindAllPageBoardItemUseCase findAllPageBoardItemUseCase) {
        this.saveBoardItemUseCase = saveBoardItemUseCase;
        this.findBoardItemUseCase = findBoardItemUseCase;
        this.deleteBoardItemUseCase = deleteBoardItemUseCase;
        this.updateBoardItemUseCase = updateBoardItemUseCase;
        this.findAllPageBoardItemUseCase = findAllPageBoardItemUseCase;
    }

    @Operation(tags = "게시판", description = "게시글 등록", summary = "게시글 등록")
    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<BoardDto.ItemIdResponse> saveBoardItem(@RequestBody BoardDto.SaveItemRequest saveItemRequest) {
        BoardDto.ItemIdResponse itemIdResponse = saveBoardItemUseCase.execute(saveItemRequest);
        return BaseResponse.createOf("게시글 생성이 완료 되었습니다.", itemIdResponse);
    }

    @Operation(tags = "게시판", description = "게시글 조회", summary = "게시글 조회")
    @GetMapping(value = "/item/{id}")
    public BaseResponse<BoardDto.FindItemResponse> findBoardItem(
        @Parameter(description = "게시글 아이디") @PathVariable(value = "id") String boardItemId) {
        BoardDto.FindItemResponse findItemResponse = findBoardItemUseCase.execute(boardItemId);
        return BaseResponse.ok("게시글을 조회 하였습니다.", findItemResponse);
    }

    @Operation(tags = "게시판", description = "게시글 삭제", summary = "게시글 삭제")
    @DeleteMapping(value = "/item/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse<Void> deleteBoardItem(
            @Parameter(description = "게시글 아이디") @PathVariable(value = "id") String boardItemId) {
        deleteBoardItemUseCase.execute(boardItemId);
        return BaseResponse.empty("게시글을 삭제 하였습니다.");
    }

    @Operation(tags = "게시판", description = "게시글 수정", summary = "게시글 수정")
    @PutMapping(value = "/item/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<BoardDto.ItemIdResponse> updateBoardItem(
            @Parameter(description = "게시글 아이디") @PathVariable(value = "id") String boardItemId,
            @RequestBody BoardDto.UpdateItemRequest updateItemRequest) {

        BoardDto.ItemIdResponse itemIdResponse = updateBoardItemUseCase.execute(boardItemId, updateItemRequest);
        return BaseResponse.ok("게시글을 수정 하였습니다.", itemIdResponse);
    }

    @Operation(tags = "게시판", description = "게시글 목록 조회", summary = "게시글 목록 조회")
    @GetMapping(value = {"/item", "/item/list"})
    @PageableAsQueryParam
    public BaseResponse<Page<BoardDto.FindItemResponse>> findAllPageBoardItems(
            @Parameter(hidden = true) Pageable pageable) {
        Page<BoardDto.FindItemResponse> page = findAllPageBoardItemUseCase.execute(pageable);
        return BaseResponse.ok("게시글 목록을 조회하였습니다.", page);
    }
}
