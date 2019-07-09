package Data.Structure;

public abstract class GameComponent {
    public enum gcType { //enums for determining type of component
        VISUAL_TEXTURE,
        VISUAL_ANIM
    }

    private gcType gct_componentType;

    protected GameComponent(gcType gct_type) {
        this.gct_componentType = gct_type;
    }

    /*
    REQUIRES:NONE
    MODIFIES:NONE
    EFFECT:Accesses the type for this component
     */
    public gcType getType() {
        return this.gct_componentType;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns data that is carried by this component
     */
    public abstract Object getData();
}
