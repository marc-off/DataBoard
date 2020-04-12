import java.util.*;

public class Data implements Comparable<Data> {
    
    private int likes;
    private ArrayList<String> viewfriends;
    
    public Data() {
        likes = 0;
        viewfriends = new ArrayList<String>();
    }
    
    public void addlike (String friend) {
        likes++;
        viewfriends.add(friend);
    }
    
    public String viewlikes() {
        return new String(Integer.toString(likes));
    //  N.B. Grazie a questa implementazione, non c'è alcun rischio di esporre la rappresentazione!
    }
    
    public ArrayList<String> viewlikesfromfriends() {
        return new ArrayList<String>(viewfriends);
    }
    
    public String display() {
        return " INSERIRE CONTENUTO DEL DATO! ";
    }
    //  ...dati che possono essere di vario tipo ma che implementano obbligatoriamente il metodo display() - METODO OBSERVER (vedere il link sul tipo di dato Data)
     
    public int compareTo (Data d) {
        return d.likes-this.likes;
    }
    
}
