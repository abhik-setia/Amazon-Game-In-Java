package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BotsRed {
	ImageView i;
BotsRed()
{
	Image imgr=new Image(getClass().getResourceAsStream("bot.png"));
	i=new ImageView(imgr);
}
}
