import org.junit.runner.*;
import org.junit.runner.notification.Failure;

public class GameOfLifeTestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(GameOfLifeTest.class);
        for (Failure failure : result.getFailures())
            System.out.println(failure.toString());
    }
}
