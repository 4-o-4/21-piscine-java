public class Program {
    private static int count;
    private static boolean flag = true;

    public static void main(String[] args) {
        Program lock = new Program();
        if (args.length != 1 || !args[0].startsWith("--count"))
            System.exit(-1);
        count = Integer.parseInt(args[0].split("=")[1]);
        new Thread(lock::produce).start();
        new Thread(lock::consume).start();
    }

    private synchronized void produce() {
        for (int i = 0; i < count; i++) {
            while (!flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            flag = false;
            System.out.println("Egg");
            notify();
        }
    }

    private synchronized void consume() {
        for (int i = 0; i < count; i++) {
            while (flag) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            flag = true;
            System.out.println("   Hen");
            notify();
        }
    }
}
