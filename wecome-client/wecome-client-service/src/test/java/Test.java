/**
 * @Author kangec 10/19/2020 7:54 PM
 * @Email ardien@126.com
 **/
public class Test {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            long currentTimeMillis = System.currentTimeMillis();
            Thread.sleep(200);
            System.out.println(currentTimeMillis % 100000000);
        }
    }
}
