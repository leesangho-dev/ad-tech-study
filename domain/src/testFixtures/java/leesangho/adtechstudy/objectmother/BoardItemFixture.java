package leesangho.adtechstudy.objectmother;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.id.GUIDGenerator;
import leesangho.adtechstudy.domain.member.MemberId;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.util.concurrent.ThreadLocalRandom;

public class BoardItemFixture {

    private static final EasyRandomParameters EASY_RANDOM_PARAMETERS = new EasyRandomParameters()
            .randomize(FieldPredicates.named("id"), GUIDGenerator::newId)
            .randomize(FieldPredicates.named("title"), new StringRandomizer(1, 100, ThreadLocalRandom.current().nextLong()))
            .randomize(FieldPredicates.named("body"), new StringRandomizer(1, 4000, ThreadLocalRandom.current().nextLong()))
            .randomize(MemberId.class, () -> MemberId.of(new StringRandomizer(1, 100, ThreadLocalRandom.current().nextLong()).getRandomValue()));

    private static final EasyRandom EASY_RANDOM = new EasyRandom(EASY_RANDOM_PARAMETERS);

    private BoardItemFixture() {
    }

    public static BoardItem makeBoardItem() {
        return EASY_RANDOM.nextObject(BoardItem.class);
    }

}
