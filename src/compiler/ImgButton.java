package compiler;


import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImgButton extends JButton{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String imgPath;
	
	public ImgButton(String path, Dimension dimension) {
		super(new ImageIcon(path));
		
        setSize(dimension);
	}
}
