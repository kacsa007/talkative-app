package talkative.app.player;

public class Sender {

    private String message;
    private PlayerEnum player;


    public Sender(){

    }

    public Sender(PlayerEnum player) {
        this.player = player;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public PlayerEnum getPlayer(){
        return player;
    }

    public void setPlayer(PlayerEnum player)
    {
        this.player = player;
    }



}
