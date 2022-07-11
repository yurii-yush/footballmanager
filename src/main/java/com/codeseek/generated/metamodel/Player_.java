package com.codeseek.generated.metamodel;

import com.codeseek.entity.Player;
import com.codeseek.entity.Team;
import com.codeseek.entity.enums.Position;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(Player.class)
public abstract class Player_ {
    public static volatile SingularAttribute<Player, Long> id;
    public static volatile SingularAttribute<Player, String> name;
    public static volatile SingularAttribute<Player, LocalDate> birthDate;
    public static volatile SingularAttribute<Player, LocalDate> startCareerDate;
    public static volatile SingularAttribute<Player, Team> team;
    public static volatile SingularAttribute<Player, String> countryCode;
    public static volatile SingularAttribute<Player, Position> position;
    public static volatile SingularAttribute<Player, Boolean> isActive;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String BIRTH_DATE = "birthDate";
    public static final String START_CAREER_DATE = "startCareerDate";
    public static final String TEAM = "team";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String POSITION = "position";
    public static final String IS_ACTIVE = "isActive";
}
