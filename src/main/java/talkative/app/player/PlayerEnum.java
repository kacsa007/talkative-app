package talkative.app.player;

public enum PlayerEnum {

    INITIATOR("Intiator"), PLAYERX("Player - John");

    private String description;


    PlayerEnum(String description) {

        this.description = description;

    }

    public String getDescription()
    {
        return description;
    }
}
