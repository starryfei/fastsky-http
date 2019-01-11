package thread;

/**
 * ClassName: ShutDownHoonThreadTest
 * Description: Java通过Runtime静态方法：Runtime.getRuntime()通过Runtime的 void addShutdownHook
 *
 * (Thread hook) 法向Java虚拟机注册一个shutdown钩子事件，这样一旦程序结束事件到来时，就运行线程hook，也就是该线程永远是最后一个执行的
 *
 * @author: starryfei
 * @date: 2019-01-11 16:48
 **/
public class ShutDownHoonThreadTest {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutServer());
        TaskThread task = new TaskThread();
        task.start();
        TaskThread2 taskThread2 = new TaskThread2();
        taskThread2.start();
        System.out.println("================================");
    }

    static class ShutServer extends Thread{

        @Override
        public void run() {
            System.out.println("删除系统的缓存");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("删除成功。。");

        }
    }

    static class TaskThread extends Thread{

        @Override
        public void run() {
            System.out.println("读文件1");
        }
    }
    static class TaskThread2 extends Thread{

        @Override
        public void run() {
            System.out.println("读文件2");
        }
    }
}
