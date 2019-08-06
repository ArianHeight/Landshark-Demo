package main.model.io;

import main.model.data.game0exceptions.ImageDidNotLoadException;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/*
this class is in charge of loading every single image
 */
public class ImageManager {
    private HashMap<String, Image> loadedImages;

    //catr
    public ImageManager() {
        loadedImages = new HashMap<String, Image>();
    }

    /*
    MODIFIES:this
    EFFECT:loads an Image given the filepath, or return a already loaded Image with the same filepath if one exists
           throws an ImageDidNotLoadException if the Image doesn't load correctly
     */
    public Image loadImage(String filePath) throws ImageDidNotLoadException {
        Image returnVal = this.loadedImages.get(filePath);

        if (returnVal != null) { //early return - Image has been loaded
            return returnVal;
        }

        ImageIcon icon = new ImageIcon(filePath);
        if (icon.getImageLoadStatus() == MediaTracker.ERRORED) { //image did not load properly
            throw new ImageDidNotLoadException();
        }

        //image loaded, add to collection
        returnVal = icon.getImage();
        this.loadedImages.put(filePath, returnVal);
        return returnVal;
    }
}
