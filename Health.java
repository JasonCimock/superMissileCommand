public class Health {

    private int health = 5;

    public Health(){

    }

    public void incrementHealth() {
        this.health++;
    }

    public void decrementHealth() {
        this.health--;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }



}