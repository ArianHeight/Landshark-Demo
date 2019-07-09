package Data.Structure;

import java.awt.Image;

/*
A game component specifically used for storing Textures
Extends the GameComponent class
 */
public class VisualTextureComponent extends GameComponent {
    private Image im_data;

    //cstr
    public VisualTextureComponent(Image im_newData) {
        super(gcType.VISUAL_TEXTURE);
        this.im_data = im_newData;
    }

    /*
    REQUiRES:NONE
    MODIFIES:NONE
    EFFECT:Returns an Image obj that is carried by this component
     */
    @Override
    public Object getData() {
        return this.im_data;
    }
}
