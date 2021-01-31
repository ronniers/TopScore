package com.ronnie.topscore.specification;

import com.ronnie.topscore.entity.Score;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public final class ScoreSpecifications {

    public static Specification<Score> playerEqualsIgnoreCase(String player) {
        return (root, query, builder)
                -> builder.equal(
                        builder.lower(root.get("player")
                ),
                player.toLowerCase()
        );
    }

    public static Specification<Score> playerIsInListIgnoreCase(List<String> playerList) {
        return (root, query, builder)
                -> builder.lower(root.get("player")).in(
                    playerList.stream().map(p -> p.toLowerCase()).toArray()
        );
    }

    public static Specification<Score> afterStartDate(Date startDate) {
        return (root, query, builder)
                -> builder.greaterThanOrEqualTo(
                    root.get("dateTime"),
                    startDate
        );
    }

    public static Specification<Score> beforeEndDate(Date endDate) {
        return (root, query, builder)
                -> builder.lessThanOrEqualTo(
                    root.get("dateTime"),
                    endDate
        );
    }

}
