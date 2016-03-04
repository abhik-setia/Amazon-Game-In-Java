package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BotsBlue {
ImageView i;
BotsBlue()
{
	Image imgr=new Image(getClass().getResourceAsStream("bot1.png"));
	i=new ImageView(imgr);
}

}
