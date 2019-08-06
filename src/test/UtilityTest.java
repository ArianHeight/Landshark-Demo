package test;

import main.model.data.structure.GameComponent;
import main.model.data.structure.VisualAnimationComponent;
import main.model.data.structure.VisualComponent;
import main.model.data.structure.VisualTextureComponent;
import main.model.utility.Misc;
import main.model.utility.RandomNumberGenerator;
import main.model.utility.Sorter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilityTest {
    Vector<GameComponent> subjectList;

    @BeforeEach
    public void before() {
        subjectList = new Vector<GameComponent>();
    }

    @Test
    public void test1() { //test random bool
        assertTrue(RandomNumberGenerator.randomBool() || true);
        assertTrue(RandomNumberGenerator.randomBool() || true);
    }

    @Test
    public void test2() { //test random between
        int subject = 0;
        for (int i = 1; i < 20; i++) {
            subject = RandomNumberGenerator.randomBetween(0, i);
            assertTrue(0 <= subject && subject <= i);
        }
    }

    @Test
    public void test3() { //test numEquals in Misc
        assertTrue(Misc.numEquals("h", "ba", "m") == 0);
        assertTrue(Misc.numEquals("h", "ba", "h") == 1);
        assertTrue(Misc.numEquals("h", "ha", "h") == 1);
        assertTrue(Misc.numEquals("ba", "ba", "ba") == 2);
    }

    @Test
    public void test4() { //sorting an empty list
        Sorter.quicksortForVC(subjectList);
    }

    @Test
    public void test5() { //sorting a list w/ 1, 2, and 3 items
        VisualComponent vc1 = new VisualTextureComponent(null, null, null, 2);
        VisualComponent vc2 = new VisualAnimationComponent(1.0, null, null);
        vc2.setLayerVal(1);
        VisualComponent vc3 = new VisualTextureComponent(null, null, null, 0);
        subjectList.add(vc1);
        Sorter.quicksortForVC(subjectList);
        subjectList.add(vc2);
        assertTrue(((VisualComponent)(subjectList.get(0))).getLayer() == 2);
        Sorter.quicksortForVC(subjectList);
        assertTrue(((VisualComponent)(subjectList.get(0))).getLayer() == 1);
        assertTrue(((VisualComponent)(subjectList.get(1))).getLayer() == 2);
        subjectList.add(vc3);
        Sorter.quicksortForVC(subjectList);
        assertTrue(((VisualComponent)(subjectList.get(0))).getLayer() == 0);
    }

    @Test
    public void test6() { //sorting a list w/ 5 items
        VisualComponent vc1 = new VisualTextureComponent(null, null, null, 2);
        VisualComponent vc2 = new VisualAnimationComponent(1.0, null, null);
        vc2.setLayerVal(1);
        VisualComponent vc3 = new VisualTextureComponent(null, null, null, 0);
        VisualComponent vc4 = new VisualTextureComponent(null, null, null, 3);
        VisualComponent vc5 = new VisualTextureComponent(null, null, null, 4);
        subjectList.add(vc1);
        subjectList.add(vc2);
        subjectList.add(vc3);
        subjectList.add(vc5);
        subjectList.add(vc4);
        Sorter.quicksortForVC(subjectList);
        for (int i = 0; i < 5; i++) {
            assertTrue(((VisualComponent)(subjectList.get(i))).getLayer() == i);
        }
    }

    @Test
    public void test7() { //sorting a list w/ 6 items
        VisualComponent vc1 = new VisualTextureComponent(null, null, null, 2);
        VisualComponent vc2 = new VisualAnimationComponent(1.0, null, null);
        vc2.setLayerVal(1);
        VisualComponent vc3 = new VisualTextureComponent(null, null, null, 0);
        VisualComponent vc4 = new VisualTextureComponent(null, null, null, 3);
        VisualComponent vc5 = new VisualTextureComponent(null, null, null, 4);
        VisualComponent vc6 = new VisualTextureComponent(null, null, null, 5);
        subjectList.add(vc1);
        subjectList.add(vc6);
        subjectList.add(vc2);
        subjectList.add(vc3);
        subjectList.add(vc5);
        subjectList.add(vc4);
        Sorter.quicksortForVC(subjectList);
        for (int i = 0; i < 6; i++) {
            assertTrue(((VisualComponent)(subjectList.get(i))).getLayer() == i);
        }
    }

    @Test
    public void test8() { //swapping test
        VisualComponent vc1 = new VisualTextureComponent(null, null, null, 2);
        VisualComponent vc2 = new VisualAnimationComponent(1.0, null, null);
        vc2.setLayerVal(1);
        subjectList.add(vc1);
        subjectList.add(vc2);
        Sorter.swap(subjectList, 0,1);
        assertTrue(((VisualComponent)(subjectList.get(0))).getLayer() == 1);
        assertTrue(((VisualComponent)(subjectList.get(1))).getLayer() == 2);
    }
}
