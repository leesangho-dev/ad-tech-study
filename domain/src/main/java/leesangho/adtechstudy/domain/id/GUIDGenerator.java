package leesangho.adtechstudy.domain.id;

import java.util.UUID;

public final class GUIDGenerator {

    private GUIDGenerator() {
    }

    public static String newId() {
        return UUID.randomUUID().toString();
    }
}
