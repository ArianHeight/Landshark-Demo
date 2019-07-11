package Data.Structure;

public abstract class GameComponent {
    public enum gcType { //enums for determining type of component
        VISUAL_TEXTURE,
        VISUAL_ANIM,
        PHYSICS
    }

    private gcType gct_componentType;
    private boolean b_isActive;

    protected GameComponent(gcType gct_type) {
        this.b_isActive = true;
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

    /*
    REQUIRES:NONE
    MODIFIES:this
    EFFECT:Activates the GameComponent
     */
    public void activate() { this.b_isActive = true; }

    /*
    REQUIRES:NONE
    MODIFIES:this
    EFFECT:Deactivates the GameComponent
     */
    public void deactivate() { this.b_isActive = false; }

    /*
    REQUIRES:NONE
    MODIFIES:NONE
    EFFECT:Returns whether the component is active or not
     */
    public boolean isActive() { return this.b_isActive; }
}