/**
 * Created by lasaldan on 3/19/14.
 */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseControl extends MouseAdapter {

	// What to do when a mouse button is clicked
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse Button Clicked");
	}
}
