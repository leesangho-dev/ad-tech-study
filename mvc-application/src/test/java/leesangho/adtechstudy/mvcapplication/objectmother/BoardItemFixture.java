package leesangho.adtechstudy.mvcapplication.objectmother;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.id.GUIDGenerator;
import leesangho.adtechstudy.domain.member.MemberId;
import leesangho.adtechstudy.mvcapplication.dto.BoardDto;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.util.concurrent.ThreadLocalRandom;

import static org.jeasy.random.FieldPredicates.named;

public class BoardItemFixture {

    private static final EasyRandomParameters EASY_RANDOM_PARAMETERS = new EasyRandomParameters()
            .randomize(named("id"), GUIDGenerator::newId)
            .randomize(named("title"), new StringRandomizer(1, 100, ThreadLocalRandom.current().nextLong()))
            .randomize(named("body"), new StringRandomizer(1, 4000, ThreadLocalRandom.current().nextLong()))
            .randomize(MemberId.class, () -> MemberId.of(new StringRandomizer(1, 100, ThreadLocalRandom.current().nextLong()).getRandomValue()));

    private static final EasyRandom EASY_RANDOM = new EasyRandom(EASY_RANDOM_PARAMETERS);

    private BoardItemFixture() {
    }

    public static BoardItem makeBoardItem() {
        return EASY_RANDOM.nextObject(BoardItem.class);
    }

    public static BoardDto.FindItemResponse mappedBoardItemResponse(BoardItem boardItem) {
        return BoardDto.FindItemResponse.of(
                boardItem.getId(), boardItem.getTitle(), boardItem.getBody(),
                boardItem.getCreated().getId(), boardItem.getModified().getId()
        );
    }
}
