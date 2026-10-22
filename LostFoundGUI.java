import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class LostFoundGUI extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField locationField;
    private JTextField descriptionField;
    private JTextField searchField;

    private JComboBox<String> typeBox;

    private JTextArea resultArea;

    private LostFoundManager manager;

    public LostFoundGUI() {

        manager = new LostFoundManager();

        setTitle("Lost & Found Management System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
    }

    private void createGUI() {

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(8, 2, 10, 10));

        // Text fields
        idField = new JTextField();
        nameField = new JTextField();
        locationField = new JTextField();
        descriptionField = new JTextField();
        searchField = new JTextField();

        // Dropdown
        typeBox = new JComboBox<>(
                new String[]{"LOST", "FOUND"}
        );

        // Buttons
        JButton addButton = new JButton("Add Item");
        JButton searchButton = new JButton("Search");
        JButton viewButton = new JButton("View All");
        JButton claimButton = new JButton("Mark Claimed");

        // Add components
        panel.add(new JLabel("Item ID:"));
        panel.add(idField);

        panel.add(new JLabel("Item Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Location:"));
        panel.add(locationField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        panel.add(new JLabel("Type:"));
        panel.add(typeBox);

        panel.add(new JLabel("Search:"));
        panel.add(searchField);

        panel.add(addButton);
        panel.add(searchButton);

        panel.add(viewButton);
        panel.add(claimButton);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        // Add to window
        add(panel, BorderLayout.NORTH);

        add(
                new JScrollPane(resultArea),
                BorderLayout.CENTER
        );

        // Button actions
        addButton.addActionListener(e -> addItem());

        searchButton.addActionListener(e -> searchItem());

        viewButton.addActionListener(e -> viewAll());

        claimButton.addActionListener(e -> claimItem());
    }

    // ADD ITEM
    private void addItem() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String location = locationField.getText().trim();
        String description = descriptionField.getText().trim();

        if (id.isEmpty() ||
                name.isEmpty() ||
                location.isEmpty() ||
                description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        String type =
                (String) typeBox.getSelectedItem();

        Item item;

        if (type.equals("LOST")) {

            item = new LostItem(
                    id,
                    name,
                    location,
                    description
            );

        } else {

            item = new FoundItem(
                    id,
                    name,
                    location,
                    description
            );
        }

        manager.addItem(item);

        JOptionPane.showMessageDialog(
                this,
                "Item added successfully!"
        );

        clearFields();
    }

    // SEARCH ITEM

    private void searchItem() {

        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            keyword = descriptionField.getText().trim();
        }
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter a keyword or description to search."
            );
            return;
        }

        resultArea.setText("");

        ArrayList<Item> results = manager.searchItem(keyword);

        if (results.isEmpty()) {
            resultArea.setText("No matching items found.");
            return;
        }

        for (Item item : results) {
            resultArea.append(item + "\n\n");
        }
    }

    // VIEW ALL ITEMS
    private void viewAll() {

        resultArea.setText("");

        for (Item item :
                manager.getItems()) {

            resultArea.append(
                    item + "\n\n"
            );
        }
    }

    // CLAIM ITEM
    private void claimItem() {

        String id = JOptionPane.showInputDialog(
                this,
                "Enter Item ID:"
        );

        if (id == null ||
                id.trim().isEmpty()) {

            return;
        }

        boolean success =
                manager.claimItem(id);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Item marked as Claimed."
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Item not found."
            );
        }
    }

    // CLEAR FORM
    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        descriptionField.setText("");
    }
}