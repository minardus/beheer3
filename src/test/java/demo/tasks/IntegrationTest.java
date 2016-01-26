package demo.tasks;

import junit.framework.TestCase;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

/**
 * Created by minar_000 on 26-1-2016.
 */
public class IntegrationTest extends TestCase {

    private FrameFixture demo;

    private Main main;

    @Before
    public void setUp() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Tasks");
        main = new Main();
        frame.setContentPane(main.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(746, 340);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        demo = new FrameFixture(frame);
    }

    @After
    public void tearDown() {
        demo.cleanUp();
    }

    @Test
    public void test() {
        demo.button("addTaskButton").click();
        DialogFixture df = WindowFinder.findDialog(AddEditTask.class).using(demo.robot);
        df.textBox("textFieldName").enterText("Vaatwasser leeghalen");
        df.textBox("textAreaDescription").enterText("Anders wordt moeke boos!!");
        df.button("buttonOK").click();
        demo.robot.waitForIdle();
        demo.button("showGraphButton").click();
        demo.robot.waitForIdle();
    }

}
