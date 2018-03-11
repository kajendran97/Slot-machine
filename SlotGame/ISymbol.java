/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

/**
 *
 * @author Asus
 */
public interface ISymbol {

    void setImage(String image); // this method to store the path of symbols(url of the image will be stored in).

    String getImage(); // get and display the symbols

    void setValue(int v); // set the credit value of the each symbols

    int getValue(); // return the values of the symbols
}
