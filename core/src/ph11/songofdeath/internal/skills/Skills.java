package battle.skills;

public class Skills {
    private String name;
    private String description; 
    private Modifiers modifiers;
    private boolean isAOE;

    public Modifiers getModifiers() {
        return this.modifiers;
    }

    public void setModifiers(Modifiers modifiers) {
        this.modifiers = modifiers;
    }
    public boolean isIsAOE() {
        return this.isAOE;
    }
    public void setIsAOE(boolean isAOE) {
        this.isAOE = isAOE;
    }

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

    public Skills(String name, String description, double attackModifier, double defenseModifier, double speedModifier, boolean isAOE){
        this.name = name;
        this.description = description;
        this.modifiers = new Modifiers(attackModifier, defenseModifier, speedModifier);
        this.isAOE = isAOE;
    }

    public static int damageCalculation(int userAttack, int targetDefense, double damageMultiplier, double defenseMultiplier, boolean guardActive){
        int damage = (int) (damageMultiplier*(userAttack - (targetDefense * defenseMultiplier)));
        if(guardActive){
            damage -= damage*0.4;
        }
        return damage;
        
    }

    public int healingCalculation(int userDefense, double healingMultiplier){
        return (int) (userDefense * healingMultiplier);
    }
}
