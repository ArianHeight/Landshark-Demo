package main.data.structure;

public abstract class GameComponent {
    public enum GcType { //enums for determining type of component
        VISUAL_TEXTURE,
        VISUAL_ANIM,
        PHYSICS,
        HITPOINT,
        TEXT,
        UI
    }

    private GcType componentType;
    private String tag;
    private boolean isActive;

    protected GameComponent(GcType type) {
        this.isActive = true;
        this.componentType = type;
        this.tag = "";
    }

    /*
    REQUIRES:NONE
    MODIFIES:NONE
    EFFECT:Accesses the type for this component
     */
    public GcType getType() {
        return this.componentType;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns data that is carried by this component
     */
    public abstract Object getData();

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns the tag that is carried by this component
     */
    public String getTag() {
        return this.tag;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Sets the tag that is going to be carried by the component
     */
    public void setTag(String newTag) {
        this.tag = newTag;
    }

    /*
    REQUIRES:NONE
    MODIFIES:this
    EFFECT:Activates the GameComponent
     */
    public void activate() {
        this.isActive = true;
    }

    /*
    REQUIRES:NONE
    MODIFIES:this
    EFFECT:Deactivates the GameComponent
     */
    public void deactivate() {
        this.isActive = false;
    }

    /*
    REQUIRES:NONE
    MODIFIES:NONE
    EFFECT:Returns whether the component is active or not
     */
    public boolean isActive() {
        return this.isActive;
    }
}
