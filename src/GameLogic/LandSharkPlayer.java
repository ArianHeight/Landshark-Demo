package GameLogic;

import Data.Player;
import Data.Structure.HPComponent;
import Data.Structure.PhysicsComponent;
import Data.Structure.VisualTextureComponent;

import javax.swing.*;
import java.awt.*;

public class LandSharkPlayer extends Player {
    //cstr TODO some stuff w/ file loading and such
    public LandSharkPlayer() {
        super(new PhysicsComponent(0.0, 0.0, 1.0, 1.0, 1.0),
                new VisualTextureComponent(new ImageIcon("./Game/Assets/zombieKnight.png").getImage(), new Rectangle(0, 0, 1, 1)),
                new HPComponent(1));
    }

    /*
    REQUIRES:A valid String
    MODIFIES:this
    EFFECT:Calls Player.inputResponse with parameter of str_input
           calls this.jump() if str_input equals "JumpPlayer"
           calls this.crouch if str_input equals "CrouchPlayer"
     */
    @Override
    public void inputResponse(String str_input) {
        super.inputResponse(str_input);

        if (str_input.equals("JumpPlayer")) {
            //call jump()
        }
        else if (str_input.equals("CrouchPlayer")) {
            //call crouch();
        }
    }
}
