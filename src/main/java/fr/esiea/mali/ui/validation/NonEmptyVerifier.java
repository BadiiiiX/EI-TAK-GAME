package fr.esiea.mali.ui.validation;

import javax.swing.*;

public class NonEmptyVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextField tf) {
            String text = tf.getText().trim();
            boolean ok = !text.isEmpty();
            if (!ok) {
                JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(input),
                        "Field cannot be empty",
                        "Required field",
                        JOptionPane.WARNING_MESSAGE
                );
            }
            return ok;
        }
        return true;
    }
}
