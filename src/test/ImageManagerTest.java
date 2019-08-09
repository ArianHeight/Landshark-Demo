package test;

import model.data.game0exceptions.ImageDidNotLoadException;
import model.io.ImageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageManagerTest {
    ImageManager subject;

    @BeforeEach
    public void before() {
        subject = new ImageManager();
    }

    @Test
    public void test1() { //tests successful load
        try {
            Image result = subject.loadImage("data/assets/textures/shark1.png");
            assertTrue(true);
        } catch (ImageDidNotLoadException error) {
            assertTrue(false);
        }
    }

    @Test
    public void test2() { //tests failed load
        try {
            Image result = subject.loadImage("data/system/test/some.png");
            assertTrue(false);
        } catch (ImageDidNotLoadException error) {
            assertTrue(true);
        }
    }

    @Test
    public void test3() { //tests loaded already
        Image result = null;
        Image resultTwo = null;
        try {
            result = subject.loadImage("data/assets/textures/shark1.png");
            assertTrue(true);
        } catch (ImageDidNotLoadException error) {
            assertTrue(false);
        }

        try {
            resultTwo = subject.loadImage("data/assets/textures/shark1.png");
            assertTrue(true);
        } catch (ImageDidNotLoadException error) {
            assertTrue(false);
        }

        assertTrue(result == resultTwo);
    }
}
