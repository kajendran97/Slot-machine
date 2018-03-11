package coursework;


import java.util.ArrayList;
import java.util.Collections;



/**
 *
 * @author Asus
 */
public class Reel{
    
    ArrayList<Symbol> images = new ArrayList<Symbol>();

    public Reel(){
        addreelImage();
    }
   
    
    public void addreelImage() { // intializing the array and adding the images
        Symbol bell = new Symbol("Images/bell.png", 6);
        Symbol cherry = new Symbol("Images/cherry.png", 2);
        Symbol lemon = new Symbol("Images/lemon.png", 3);
        Symbol plum = new Symbol("Images/plum.png", 4);
        Symbol redSeven = new Symbol("Images/redseven.png", 7);
        Symbol waterMelon = new Symbol("Images/watermelon.png", 5);
        images.add(bell);
        images.add(cherry);
        images.add(lemon);
        images.add(plum);
        images.add(redSeven);
        images.add(waterMelon);

        
    }

    public ArrayList Spin(ArrayList images){//This method will suffle the images which stores in the arraylist.
        Collections.shuffle(images);
        return images;
    }
    
   public void stopSpin(boolean spin) {
        spin = false;
        Thread.currentThread().stop();
    }
   
   public Symbol getReel(int value) { // this mehod is to get the value of the revelent images when the spin is stoped.
       for(int i = 0; i < images.size(); i++) {
           if(images.get(i).getValue() == value) {
               return images.get(i);
           }
       }
       return null;
   }
   
}
