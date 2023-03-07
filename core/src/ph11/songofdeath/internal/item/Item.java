package battle.item;

public class Item {
    private String name;
    private String description;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item(String name, String description){
        this.name = name;
        this.description = description;
    }
}
