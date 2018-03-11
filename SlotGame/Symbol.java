
package coursework;

/**
 *
 * @author Asus
 */
public class Symbol implements ISymbol{

    private String path=null; // to store the path of the images
    private int value; // to store the coin credits of the images

    public Symbol(String image,int value){ // constructor
        this.path = image;
        this.value = value;
    }
    public void setImage(String path){ // This method will add the path of the iamges which stores in the folder.
        this.path = path;
    }

    public String getImage() { // This method is to retrive the image path.
        return path;
    }

    public void setValue(int v) { // this method will set the values to each images
        this.value = v;
    }

    public int getValue() { // this method will get the value of the images to calculate the winner.
        return value;
    }
}

