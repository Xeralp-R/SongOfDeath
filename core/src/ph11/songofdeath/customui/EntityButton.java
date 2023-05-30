package ph11.songofdeath.customui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import ph11.songofdeath.battle.internal.entities.Entity;

public class EntityButton extends ImageButton {

    public final Entity associatedEntity;
    public EntityButton(Entity associatedEntity, Drawable imageUp) {
        super(imageUp);
        this.associatedEntity = associatedEntity;
    }
}
