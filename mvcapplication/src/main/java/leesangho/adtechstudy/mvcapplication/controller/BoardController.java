package leesangho.adtechstudy.mvcapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import leesangho.adtechstudy.mvcapplication.dto.BaseResponse;
import leesangho.adtechstudy.mvcapplication.dto.BoardDto;
import leesangho.adtechstudy.mvcapplication.usecase.SaveBoardItemUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardController {

    private final SaveBoardItemUseCase saveBoardItemUseCase;

    public BoardController(SaveBoardItemUseCase saveBoardItemUseCase) {
        this.saveBoardItemUseCase = saveBoardItemUseCase;
    }

    @Operation(tags = "게시판", description = "게시글 등록", summary = "게시글 등록")

    @PostMapping(value = "/v1/board/item", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<BoardDto.SaveItemResponse> saveBoardItem(@RequestBody BoardDto.SaveItemRequest saveItemRequest) {
        BoardDto.SaveItemResponse saveItemResponse = saveBoardItemUseCase.execute(saveItemRequest);
        return BaseResponse.createOf("게시글 생성이 완료 되었습니다.", saveItemResponse);
    }

}
