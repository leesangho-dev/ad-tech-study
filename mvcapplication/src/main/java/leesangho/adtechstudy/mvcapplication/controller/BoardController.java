package leesangho.adtechstudy.mvcapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import leesangho.adtechstudy.mvcapplication.dto.BaseResponse;
import leesangho.adtechstudy.mvcapplication.dto.BoardDto;
import leesangho.adtechstudy.mvcapplication.usecase.DeleteBoardItemUseCase;
import leesangho.adtechstudy.mvcapplication.usecase.FindBoardItemUseCase;
import leesangho.adtechstudy.mvcapplication.usecase.SaveBoardItemUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/board", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private final SaveBoardItemUseCase saveBoardItemUseCase;

    private final FindBoardItemUseCase findBoardItemUseCase;

    private final DeleteBoardItemUseCase deleteBoardItemUseCase;

    public BoardController(SaveBoardItemUseCase saveBoardItemUseCase, FindBoardItemUseCase findBoardItemUseCase, DeleteBoardItemUseCase deleteBoardItemUseCase) {
        this.saveBoardItemUseCase = saveBoardItemUseCase;
        this.findBoardItemUseCase = findBoardItemUseCase;
        this.deleteBoardItemUseCase = deleteBoardItemUseCase;
    }

    @Operation(tags = "게시판", description = "게시글 등록", summary = "게시글 등록")
    @PostMapping(value = "/item")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<BoardDto.SaveItemResponse> saveBoardItem(@RequestBody BoardDto.SaveItemRequest saveItemRequest) {
        BoardDto.SaveItemResponse saveItemResponse = saveBoardItemUseCase.execute(saveItemRequest);
        return BaseResponse.createOf("게시글 생성이 완료 되었습니다.", saveItemResponse);
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
}
