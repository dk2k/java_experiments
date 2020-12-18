package delivery;

import javax.swing.*;

public class DeliveryRunnable implements Runnable {

    private static String prefix = "                ";

    private Order order; // delivering this order
    private Auto auto; // with this auto
    private JTextArea console;

    public DeliveryRunnable(Order order, Auto auto, JTextArea console) {
        this.order = order;
        this.auto = auto;
        this.console = console;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        console.append(prefix + "Order " + order + "\n");
        console.append(prefix + "has been delivered, " + "\n");
        console.append(prefix + "auto is vacant now: " + auto.getMake() + "\n");
        auto.setBusy(false);
    }
}
