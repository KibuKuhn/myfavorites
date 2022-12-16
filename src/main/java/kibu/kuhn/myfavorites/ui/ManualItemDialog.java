package kibu.kuhn.myfavorites.ui;

import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.domain.HyperlinkItem;
import kibu.kuhn.myfavorites.domain.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.awt.GridBagConstraints.*;

class ManualItemDialog extends JDialog {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManualItemDialog.class);

    private JLabel errorLabel;

    enum InputType {
        File, Folder, Link, Invalid
    }

    private JTextField locationField;
    private JTextField aliasField;

    private Item result;

    ManualItemDialog(Component owner) {
        super((JDialog)SwingUtilities.getRoot(owner), true);
        init();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            result = null;
            errorLabel.setText(null);
            aliasField.setText(null);
            locationField.setText(null);
        }
        super.setVisible(visible);
    }

    private void init() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(IGui.get().getI18n("manualitemdialog.title"));
        getContentPane().setLayout(new GridLayout(1,1,2,2));
        JPanel pane = new JPanel(new GridBagLayout());
        getContentPane().add(pane);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = WEST;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = NONE;
        constraints.insets.right = 5;
        constraints.insets.bottom = 5;
        pane.add(new JLabel(IGui.get().getI18n("manualitemdialog.message")), constraints);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.fill = HORIZONTAL;
        constraints.gridx = RELATIVE;
        constraints.insets.right = 0;
        locationField = new JTextField();
        locationField.addActionListener(new LocationListener());
        pane.add(locationField, constraints);

        constraints.gridy = RELATIVE;
        constraints.gridx = 0;
        constraints.gridwidth = RELATIVE;
        constraints.weightx = 0;
        constraints.fill = NONE;
        constraints.insets.right = 5;
        pane.add(new JLabel(IGui.get().getI18n("manualitemdialog.alias")), constraints);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.fill = HORIZONTAL;
        constraints.gridx = RELATIVE;
        constraints.insets.right = 0;
        aliasField = new JTextField();
        pane.add(aliasField, constraints);

        constraints.gridx = 0;
        constraints.insets.top = 20;
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        pane.add(errorLabel, constraints);

        constraints.insets.top = 0;
        constraints.weighty = 1;
        constraints.fill = BOTH;
        constraints.gridx = 0;
        pane.add(Box.createGlue(), constraints);

        constraints.fill = HORIZONTAL;
        constraints.weighty = 0;
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pane.add(buttonPane, constraints);
        buttonPane.add(new JButton(new AddAction()));
        buttonPane.add(new JButton(new CancelAction()));

        pack();
        setSize(400, 300);
    }

    private class AddAction extends AbstractAction {

        private AddAction() {
            putValue(NAME, IGui.get().getI18n("anualitemdialog.addaction"));
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String location = locationField.getText();
            InputType inputType = validateLocation(location);
            Item result = null;
            switch (inputType) {
                case Link:
                    try {
                        result = HyperlinkItem.of(new URL(location.trim()));
                    } catch (MalformedURLException ex) {
                        LOGGER.debug(ex.getMessage());
                        //never happens
                    }
                    break;
                case File:
                    result = FileSystemItem.of(Paths.get(location.trim()), true);
                    break;
                case Folder:
                    result = FileSystemItem.of(Paths.get(location.trim()), false);
                    break;
                case Invalid:
                    displayError();
                    break;
                default:
                    throw new IllegalStateException("Unknown inputType: " + inputType);
            }
            if (inputType == InputType.Invalid) {
                return;
            }


            String alias = aliasField.getText();
            if (alias != null && !alias.isBlank()) {
                result.setAlias(alias);
            }

            ManualItemDialog.this.result = result;
            errorLabel.setText(null);
            setVisible(false);
        }
    }

    private void displayError() {
        errorLabel.setText(IGui.get().getI18n("manualitemdialog.error"));
    }

    private InputType validateLocation(String location) {
        if (location == null || location.isBlank()) {
            return InputType.Invalid;
        }

        location = location.trim();

        try {
            Path path = Paths.get(location);
            boolean exists = Files.exists(path);
            if (exists) {
                return Files.isDirectory(path) ? InputType.Folder : InputType.File;
            }
            throw new FileNotFoundException("invalid path");
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
            try {
                new URL(location);
                return InputType.Link;
            } catch (MalformedURLException e) {
                LOGGER.debug(e.getMessage());
                return InputType.Invalid;
            }
        }
    }

    private class CancelAction extends AbstractAction {

        private CancelAction() {
            putValue(NAME, IGui.get().getI18n("manualitemdialog.cancelaction"));
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }

    Optional<Item> getItem() {
        return Optional.ofNullable(result);
    }

    private class LocationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = errorLabel.getText();
            if (text != null && !text.isBlank()) {
                errorLabel.setText(null);
            }
        }
    }
}
