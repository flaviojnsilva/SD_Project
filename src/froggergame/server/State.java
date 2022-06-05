package froggergame.server;

import java.io.Serializable;

public class State implements Serializable {
    private int id;
    private String msg;
    private String position;
    private String level;

    public State(String position, String level) {
        this.position = position;
        this.level = level;
    }

    public State(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}

