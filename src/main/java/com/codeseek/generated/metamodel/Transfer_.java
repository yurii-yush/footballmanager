package com.codeseek.generated.metamodel;

import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.entity.Transfer;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@StaticMetamodel(Transfer.class)
public abstract class Transfer_ {
    public static volatile SingularAttribute<Player, Long> id;
    public static volatile SingularAttribute<Player, Player> player;
    public static volatile SingularAttribute<Player, Team> fromTeam;
    public static volatile SingularAttribute<Player, Team> toTeam;
    public static volatile SingularAttribute<Player, BigInteger> price;
    public static volatile SingularAttribute<Player, LocalDateTime> datetime;

    public static final String ID = "id";
    public static final String PLAYER = "player";
    public static final String FROM_TEAM = "fromTeam";
    public static final String TO_TEAM = "toTeam";
    public static final String PRICE = "price";
    public static final String DATETIME = "datetime";
}
