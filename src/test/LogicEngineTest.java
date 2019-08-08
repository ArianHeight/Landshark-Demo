package test;

import model.data.GameObject;
import model.data.GameScene;
import model.data.communication.CollisionDetectedRequest;
import model.data.communication.GameScript;
import model.data.structure.GameComponent;
import model.data.structure.PhysicsComponent;
import model.data.structure.UiComponent;
import model.game0logic.LogicEngine;
import model.io.IoEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogicEngineTest {
    LogicEngine subject;
    GameObject scene;
    Vector<GameScript> someVector;
    IoEngine fileIo;

    @BeforeEach
    public void before() {
        subject = new LogicEngine();
        scene = new GameScene();
        someVector = new Vector<GameScript>();
        fileIo = new IoEngine();
        subject.startGame(scene, someVector, fileIo);
    }

    @Test
    public void test1() { //just to make sure it runs
        for (int i = 0; i < 50; i++) {
            subject.spawnEnemies(scene, 1.0);
        }
        Vector<GameComponent> comps = new Vector<GameComponent>();
        scene.compileComponentList(comps, GameComponent.GcType.UI);
        assertTrue(comps.size() == 0);
        subject.togglePause();
        scene.compileComponentList(comps, GameComponent.GcType.UI);
        assertTrue(comps.size() == 1);
        ((UiComponent)comps.get(0)).press();
        subject.checkReplayButton(scene);
    }

    @Test
    public void test2() { //logic update and scripts
        PhysicsComponent pcOne = new PhysicsComponent(null, 1.0, true);
        pcOne.setTag("Player");
        PhysicsComponent pcTwo = new PhysicsComponent(null, 1.0, false);
        pcTwo.setTag("Map");
        subject.runScript(new CollisionDetectedRequest(pcOne, pcTwo), scene);
        assertTrue(subject.getCollisionRequestQueue().size() == 1);
        subject.logicUpdate(scene, 1.0);
        pcTwo.setTag("Enemy");
        subject.runScript(new CollisionDetectedRequest(pcOne, pcTwo), scene);
        assertTrue(subject.getCollisionRequestQueue().size() == 0);
        subject.logicUpdate(scene, 1.0);
        Vector<GameComponent> comps = new Vector<GameComponent>();
        scene.compileComponentList(comps, GameComponent.GcType.UI);
        assertTrue(comps.size() == 1);
    }
}
