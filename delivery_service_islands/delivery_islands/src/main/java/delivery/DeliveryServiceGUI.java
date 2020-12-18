package delivery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DeliveryServiceGUI extends JFrame {

    private static String prefix = "                ";

    private int count = 0;

    public DeliveryServiceGUI(City[] startCity,
                              BST[] bstInCity,
                              java.util.List<City> possibleDestCity,
                              CityGraph cityGraph,
                              Queue<Order> queue) throws HeadlessException {
        //this.startCity = startCity;
        //this.bstInCity = bstInCity;
        //this.possibleDestCity = possibleDestCity;
        //this.cityGraph = cityGraph;

        for (int i = 0; i < startCity.length; i++)
            comboBoxStart.addItem(startCity[count++].getName());

        for (City dest : possibleDestCity) {
            comboBoxDest.addItem(dest.getName());
        }

        console.setEditable(false);

        textfield1.setEditable(false);
        textfield2.setEditable(false);
        textfieldWeight.setEditable(false);
        textfieldDest.setEditable(false);
        textfieldDestCheckResult.setEditable(false);

        addOrderButton.setEnabled(false);

        WrapperForSelectedData wrapperForSelectedData = new WrapperForSelectedData();

        AtomicInteger maxWeightInService = new AtomicInteger(0);
        comboBoxDest.setEnabled(false);

        comboBoxStart.setSelectedItem(null);

        comboBoxStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCityString = (String) ((JComboBox) e.getSource()).getSelectedItem();

                comboBoxDest.setEnabled(true);

                // find it's position in array
                int position = -1;
                for (int i = 0; i < startCity.length; i++) {
                    if (startCity[i].getName().equals(selectedCityString)) {
                        position = i;
                        wrapperForSelectedData.setPositionInComboStart(position);
                        break;
                    }
                }

                wrapperForSelectedData.setReachable(cityGraph.dfs(startCity[position]));

                // find max weight which we can deliver in the service of this city
                maxWeightInService.set(bstInCity[position]
                        .maxValue(bstInCity[position].getRoot())
                        .getMaxWeight());

                textfield2.setText("You Selected start city: " + selectedCityString + ". Max weight of package is " + maxWeightInService);

            }
        });

        comboBoxDest.setSelectedItem(null);

        comboBoxDest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDestCityString = (String) ((JComboBox) e.getSource()).getSelectedItem();

                // find it in the List
                City selectedCity = null;
                for (City dest : possibleDestCity) {
                    if (dest.getName().equals(selectedDestCityString)) {
                        selectedCity = dest;
                        break;
                    }
                }

                // check if the dest city is reachable from our start city
                boolean isReachable = wrapperForSelectedData.getReachable().contains(selectedCity);

                textfieldDestCheckResult.setText("Destination city: " + selectedCity.getName() + (isReachable ? " is reachable" : " isn't reachable"));

                if (isReachable) {
                    textfieldWeight.setEditable(true);
                    addOrderButton.setEnabled(true);
                } else {
                    textfieldWeight.setEditable(false);
                    addOrderButton.setEnabled(false);
                }

            }
        });

        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int specifiedWeight = Integer.valueOf(textfieldWeight.getText());

                    // check if this weight is less than maximal for the chosen service
                    if (specifiedWeight > maxWeightInService.get()) {
                        JOptionPane.showMessageDialog(new JFrame(), "This weight is greater than max weight " + maxWeightInService.get(), "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // adding order to queue
                    Order order = new Order();
                    order.setFromCity(new City((String) comboBoxStart.getSelectedItem()));
                    order.setToCity(new City((String) comboBoxDest.getSelectedItem()));
                    order.setWeight(specifiedWeight);

                    console.append(prefix + "Added order " + order + "\n");
                    queue.add(order);
                    console.append(prefix + "Size of queue " + queue.size() + "\n");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Please specify weight as a number", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) { // infinite loop

                    if (queue.size() > 0) {
                        Order order = queue.poll();
                        console.append(prefix + "trying to process order " + order + "\n");
                        // check if there is a vehicle with needed load
                        BST currentBST = bstInCity[wrapperForSelectedData.getPositionInComboStart()];
                        java.util.List<Auto> capableAutos = currentBST.
                                nodesGreaterThanXOrEqual(currentBST.getRoot(), order.getWeight())
                                .stream()
                                .map(node -> node.getAuto())
                                .collect(Collectors.toList());

                        console.append(prefix + "Capable vehicles: \n");
                        capableAutos.stream().forEach(auto -> {
                            console.append(prefix + auto.getMake() + " load " + auto.getMaxWeight() + "\n");
                        });

                        // find vacant car with less possible max load

                        Auto vacantCapableAuto = null;
                        int minVacantLoadOfAuto = 10000000;

                        for (Auto auto : capableAutos) {
                            if (!auto.getBusy() && minVacantLoadOfAuto > auto.getMaxWeight()) {
                                vacantCapableAuto = auto;
                                minVacantLoadOfAuto = auto.getMaxWeight();
                            }
                        }

                        if (vacantCapableAuto == null) {
                            console.append(prefix + "Can't find a vacant capable vehicle\n");
                            // need to place the order back into the queue
                            queue.add(order);
                        } else {
                            console.append(prefix + "Found best fit vehicle " + vacantCapableAuto.getMake() + "\n");
                            console.append(prefix + "Starting delivery\n");
                            vacantCapableAuto.setBusy(true);
                            console.append(prefix + "Vehicle " + vacantCapableAuto.getMake() + "\n");
                            console.append(prefix + " is now occupied till the end of this delivery\n");

                            Runnable deliveryRunnable = new DeliveryRunnable(order, vacantCapableAuto, console);
                            new Thread(deliveryRunnable).start();

                        }

                    } else {
                        console.append(prefix + "empty queue\n");
                    }

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        new Thread(runnable).start();

        setLayout(new FlowLayout());

        add(textfield1);
        add(comboBoxStart);
        add(textfield2);

        add(textfieldDest);
        add(comboBoxDest);
        add(textfieldDestCheckResult);

        add(labelForWeight);
        add(textfieldWeight);

        add(addOrderButton);

        //add(console);
        JScrollPane scroll = new JScrollPane (console,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scroll);
    }

    private JTextField textfield1 = new JTextField
            ("Select start city : "); // to deliver from this city
    private JTextField textfield2 = new JTextField(45); // to show selected City and max weight of package

    private JLabel labelForWeight = new JLabel("Specify weight of your package:");
    private JTextField textfieldWeight = new JTextField(25);

    private JComboBox comboBoxStart = new JComboBox();


    private JTextField textfieldDest = new JTextField
            ("Select destination city : "); // to deliver to this city
    private JComboBox comboBoxDest = new JComboBox();
    private JTextField textfieldDestCheckResult = new JTextField(45); // to show selected City and max weight of package

    JButton addOrderButton = new JButton("Add delivery order");

    JTextArea console = new JTextArea(25, 55);
}
