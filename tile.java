import java.awt.*;
public class tile {
    private int x;
    private int y;
    private int height;
    private int width;
    private Color tileColour;
    private String type;
    public tile(int random){
        if (random == 1){
        	//brown
        	type = "Barricade";
            tileColour = new Color(153,102,0);
        }
        else if (random == 0){
        	//grey
        	type = "Floor";
            tileColour = new Color(214,217,223);
        }
        else if(random == 2){
        	tileColour = new Color(0, 0, 0);
        }
    }
    public String getType(){
    	return type;
    }
    public Color getColour(){
        return tileColour;
    }
    public void setColour(Color Colour){
    	this.tileColour = Colour;
    }
    public void setDetails(int x, int y, int height, int width){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public void paint(Graphics2D g) {
    	g.setColor(tileColour);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }
}
