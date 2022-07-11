package com.codeseek.generated.metamodel;

import com.codeseek.entity.Player;
import com.codeseek.entity.Team;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@StaticMetamodel(Team.class)
public abstract class Team_ {
    public static volatile SingularAttribute<Team, Long> id;
    public static volatile SingularAttribute<Team, String> name;
    public static volatile SingularAttribute<Team, BigInteger> balance;
    public static volatile SingularAttribute<Team, BigDecimal> commission;
    public static volatile SingularAttribute<Team, List<Player>> players;
    public static volatile SingularAttribute<Team, String> countryCode;
    public static volatile SingularAttribute<Team, Boolean> isActive;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String BALANCE = "balance";
    public static final String COMMISSION = "commission";
    public static final String PLAYERS = "players";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String IS_ACTIVE = "isActive";
}
