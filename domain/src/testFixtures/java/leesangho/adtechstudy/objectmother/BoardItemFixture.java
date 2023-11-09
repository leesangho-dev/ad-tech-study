package leesangho.adtechstudy.objectmother;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.id.GUIDGenerator;
import net.jqwik.api.Arbitraries;

import java.util.List;

public final class BoardItemFixture {
    private static final FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();

    private static final ArbitraryBuilder<BoardItem> ARBITRARY_BOARD_ITEM = FIXTURE_MONKEY.giveMeBuilder(BoardItem.class)
            .set("id", GUIDGenerator.newId())
            .set("title", Arbitraries.strings().ofMinLength(1).ofMaxLength(100))
            .set("body", Arbitraries.strings().ofMaxLength(4000))
            .set("created.id", Arbitraries.strings().ofMinLength(1).ofMaxLength(100))
            .set("modified.id", Arbitraries.strings().ofMinLength(1).ofMaxLength(100))
            ;

    private BoardItemFixture() {
    }

    public static BoardItem fixtureBoardItem() {
        return ARBITRARY_BOARD_ITEM.sample();
    }

    public static List<BoardItem> fixtureBoardItems(int size) {
        return ARBITRARY_BOARD_ITEM.sampleList(size);
    }

}
