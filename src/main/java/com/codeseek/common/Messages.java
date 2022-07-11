package com.codeseek.common;

public interface Messages {

    String FREE_AGENT = "Free Agent";
    String NOT_EMPTY_NAME = "Name shouldn't be empty.";
    String REQUIRED_NAME_LENGTH = "Name length should be 5-40 letters.";
    String REQUIRED_BALANCE_FOR_CREATING_TEAM = "Minimum balance for creating new team should be more than 100_000";
    String REQUIRED_MIN_TEAM_COMMISSION = "Team commission should be more or equal than 0.1%";
    String REQUIRED_PLAYER= "Add player to transfer";
    String REQUIRED_FROM_TEAM= "Add team which sell player";
    String REQUIRED_TO_TEAM= "Add team which buy player";

    String REQUIRED_MAX_TEAM_COMMISSION = "Team commission should be less or equal than 10.0%";
    String NOT_NULL_BIRTH_DATE = "Birth date required";
    String NOT_NULL_START_CAREER_DATE = "Start career date required";


    //Controllers URI
    String TEAM_CONTROLLER_URI = "/api/v1/teams";
    String ID_FOR_URI = "/id=%s";
    String CREATED_TEAM_URI = TEAM_CONTROLLER_URI + ID_FOR_URI;

    String PLAYER_CONTROLLER_URI = "/api/v1/players";
    String CREATED_PLAYER_URI = PLAYER_CONTROLLER_URI + ID_FOR_URI;
    String TRANSFER_CONTROLLER_URI = "/api/v1/transfers";
    String CREATED_TRANSFER_URI = TRANSFER_CONTROLLER_URI +  ID_FOR_URI;
    String ID_PATH = "id";
    String ID_MAPPING = "/{id}";

    //Exceptions messages
    String NOT_VALID_START_CAREER_DATE = "Start career date not valid";
    String RESOURCE_NOT_FOUND = "Resource not found";
    String PLAYER_ID_NOT_FOUND = "Player with id %d not found.";
    String TEAM_ID_NOT_FOUND = "Team with id %d not found.";
    String TEAM_NOT_UPDATABLE = "You can't update not active team";
    String PLAYER_NOT_UPDATABLE = "You can't update not active player";
    String TEAM_NAME_ALREADY_EXIST = "Team with name %s already exist.";
    String REQUIRED_PLAYER_AGE = "Player should be older than %d years.";
    String NOT_ENOUGH_MONEY_ON_BALANCE = "Team have not enough money on balance for buying that player. Has %s, Required %s" ;
    String CHANGE_ANOTHER_TO_TEAM = "Player are already in that team. Choose another one" ;
    String PLAYER_FINISHED_CAREER = "Player finished career. He can't be sell" ;
    String TEAM_FINISHED_CAREER = "Team not active." ;

    String REQUIRED_PLAYER_EXPERIENCE = "Player had to start career after %d years old.";
    String VALIDATION_FAILED = "Validation Failed";
    String FIELD_VALIDATION_FAILED = "Field validation failed";
    String NOT_ENOUGH_MONEY = "Not money on balance";
    String TRANSFER_VALIDATION_EXCEPTION = "Transfer validation failed";
    String PLAYER_VALIDATION_EXCEPTION= "Player validation failed";
    String TEAM_VALIDATION_EXCEPTION= "Team validation failed";
    String FAILED_TO_CONVERT_VALUE = "Failed to convert value";
    String NULL_POINTER_EXCEPTION = "NullPointerException";
    String OTHER_EXCEPTION = "Something was wrong. Try later";
}
