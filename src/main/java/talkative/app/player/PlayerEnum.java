package talkative.app.player;

public enum PlayerEnum {

    INITIATOR("Intiator"), PLAYERX("PlayerX");

    private String description;


    PlayerEnum(String description) {

        this.description = description;

    }

    public String getDescription()
    {
        return description;
    }
}
