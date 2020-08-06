package StudentManager;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

public class MyRenderer extends DefaultTableCellRenderer { // ���̺� ��� �� ������ Ŭ����
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (!isSelected) {
			if (row % 2 == 0) {
				cell.setBackground(Color.WHITE);
			} else {
				cell.setBackground(Color.getHSBColor(0, 0, 0.85f));
			}
		}
		return cell;
	}
}
