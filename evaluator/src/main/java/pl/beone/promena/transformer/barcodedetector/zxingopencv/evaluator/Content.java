package pl.beone.promena.transformer.barcodedetector.zxingopencv.evaluator;

import javax.swing.*;

public class Content {

    JPanel panel;

    JTextField imageOrFolderPathTextField;
    JButton loadButton;

    JSpinner rotationThresholdDegreesSpinner;
    JCheckBox additionalVerticalTransformationCheckBox;

    JButton applyButton;
    JButton nextImageButton;

    JRadioButton linearRadioButton;
    JRadioButton matrixRadioButton;

    JSpinner thresholdValueSpinner;
    JSpinner thresholdMaxValueSpinner;
    JLabel thresholdImageLabel;

    JSpinner closingKernelSizeWidthSpinner;
    JSpinner closingKernelSizeHeightSpinner;
    JLabel closingKernelImageLabel;

    JSpinner erosionsIterationsSpinner;
    JSpinner dilationsIterationsSpinner;
    JLabel erosionsAndDilationsLabel;

    JLabel contoursImageLabel;

    JLabel resultImageLabel;
    JList<String> barcodeList;
}
