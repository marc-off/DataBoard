import java.util.*;

public class TestBattery {
    public static void main (String[] args) {
        
        try {
            
            Board<CalcioNews> cn = new Board<CalcioNews> ("Marco","dexter66");
            // Proprietario : "Marco" ; Password : "dexter66"
            boolean runOK = true;
            // Per la gestione delle funzioni con valore di return "boolean"
            Vector<CalcioNews> bycategory;
            // Per generare un insieme di news di tipo "CalcioNews"
/*
            String names[] = {
                "Augusto",
                "Tito",
                "Domiziano",
                "Nerva",
                "Traiano",
                "Adriano","
                "Antonino Pio",
                "Marco Aurelio",
                "Lucio Vero",
                "Commodo",
                "Pertinace",
                "Didio Giuliano",
                "Settimio Severo",
                "Pescennio Nigro",
                "Clodio Albino",
                "Antonino (Caracalla)",
                "Geta",
                "Macrino"
            };
 */
            //  Lista casuale di nomi per fare ulteriori test a vostro piacimento!! (servitevi della seguente istruzione per generare a random alcuni nomi): System.out.println( new Random().nextInt(10); )

          
            CalcioNews d1 = new CalcioNews("Messi va alla JUVE!!!");
            CalcioNews d2 = new CalcioNews("I nerazzurri tornano in CHAMPIONS!!!");
            CalcioNews d3 = new CalcioNews("Lazio torna in serie B!!!");

            CalcioNews d4 = new CalcioNews("Pisa ha appena acquistato Messi (...come no!!!)");
//          Non lo useremo, lo testeremo solamente per l'eccezione 'DatoNotFoundException'
            
            System.out.println(d1.display());
            System.out.println(d2.display());
            System.out.println(d3.display());
          
            cn.createCategory("Milan", "dexter66");
            cn.createCategory("Inter", "dexter66");
            cn.createCategory("Juve", "dexter66");
            cn.createCategory("Lazio", "dexter66");
            cn.createCategory("Roma", "dexter66");
            cn.createCategory("Atalanta", "dexter66");
            cn.createCategory("Cagliari", "dexter66");
            cn.createCategory("Parma", "dexter66");
            cn.createCategory("Napoli", "dexter66");
            
// Triggers NullPointerException            cn.createCategory(null, "dexter66");
// Triggers WrongPasswordException           cn.createCategory("Parma", "daxxxtor64");
// Triggers AlreadyExistingCategoryException            cn.createCategory("Torino", "dexter66");

            cn.removeCategory("Milan", "dexter66");
            cn.removeCategory("Parma", "dexter66");
            cn.removeCategory("Atalanta", "dexter66");
// Triggers CategoryNotFoundException            cn.removeCategory("Pordenone", "dexter66");

            cn.addFriend("Roma", "dexter66", "Mario");
            cn.addFriend("Cagliari", "dexter66", "Adriano");
            cn.addFriend("Atalanta", "dexter66", "Enrico");
// Triggers FriendAlreadyAddedException            cn.addFriend("Roma", "dexter66", "Mario");

            cn.removeFriend("Roma", "dexter66", "Mario");
// Triggers FriendNotFoundException            cn.removeFriend("Atalanta", "dexter66", "Ciccio");
            
            runOK = cn.put("dexter66", d1, "Juve");
            runOK = cn.put("dexter66", d2, "Inter");
            runOK = cn.put("dexter66", d3, "Lazio");
// Triggers CategoryNotFoundException            runOK = cn.put("dexter66", d1, "Albinoleffe");
        
            CalcioNews copy_d1 = new CalcioNews ("copia di d1");
            copy_d1 = cn.get("dexter66", d1);
            System.out.println(copy_d1.display());
//  Stampa il contenuto di d1 : "CALCIONEWS : Messi va al MILAN!!!"
            
// Triggers DatoNotFoundException
//            CalcioNews copy_fake = new CalcioNews ("null perchè d4 non presente");
//            copy_fake = cn.get("dexter66", d4);
            
            copy_d1 = cn.remove("dexter66", d1);
// Triggers DatoNotFoundException (avendo appena rimosso d1)            copy_d1 = cn.get("dexter66", d1);
            
            cn.addFriend("Inter", "dexter66", "Mario");
            cn.insertLike("Mario", d2);
            System.out.println(cn.get("dexter66", d2).viewlikes());
//  Stampa il numero di like che l'oggetto d2 ha ottenuto finora , ovvero 1
//  Triggers FriendAlreadyLikedException         cn.insertLike ("Mario", d2);
            
            cn.addFriend("Inter", "dexter66", "Luigi");
            cn.addFriend("Inter", "dexter66", "Giovanni");
            cn.insertLike("Luigi", d2);
            cn.insertLike("Giovanni", d2);
            System.out.println(cn.get("dexter66", d2).viewlikes());
            System.out.println();
//  Stampa il numero di like che l'oggetto d2 ha ottenuto finora , ovvero 3
            
            CalcioNews inter1 = new CalcioNews("Inter sorpassa nuovamente la Juventus!");
            CalcioNews inter2 = new CalcioNews("Rottura alla caviglia per Sanchez : STOP di 3 MESI!");
            CalcioNews inter3 = new CalcioNews("Coppia LUKAKU-MARTINEZ imbattibile, sembra quasi amore!");
            runOK = cn.put("dexter66", inter1, "Inter");
            runOK = cn.put("dexter66", inter2, "Inter");
            runOK = cn.put("dexter66", inter3, "Inter");
            bycategory = new Vector<CalcioNews>(cn.getDataCategory("dexter66", "Inter"));
//  Stampa il contenuto delle news appartenenti alla categoria "Inter"
            
            System.out.println("Contenuto della categoria INTER : ");
            for ( CalcioNews internews : bycategory )     System.out.println(internews.display());
            System.out.println();
//  Stampa le notizie che fanno parte della categoria "Inter", ovvero le notizie 'inter1', 'inter2', 'inter3' (e prima tra le altre, la notizia 'd2' che abbiamo inserito prima come test della procedura "put")
            
            CalcioNews lazio1 = new CalcioNews("Immobile CAPOCANNONIERE della SERIE A!");
            runOK = cn.put ("dexter66", lazio1, "Lazio");
            cn.addFriend("Lazio", "dexter66", "Caligola");
            cn.insertLike("Caligola", lazio1);
//  Likes registrati finora della notizia 'lazio1' = 1, seconda dopo la notizia 'd2' in termini di likes
            
            CalcioNews cagliari1 = new CalcioNews("Il famosissimo 'casteddu' tra le prime 4 della classifica!");
            runOK = cn.put ("dexter66", cagliari1, "Cagliari");
            cn.addFriend("Cagliari", "dexter66", "Caligola");
            CalcioNews napoli1 = new CalcioNews("Rapporti incrinati tra la dirigenza e l'allenatore Ancelotti!");
            runOK = cn.put ("dexter66", napoli1, "Napoli");
            cn.addFriend("Napoli", "dexter66", "Caligola");
            Iterator<CalcioNews> bylikes = cn.getIterator("dexter66");
//  Ordinamento delle news in ordine DECRESCENTE in base ai like ottenuti da esse!!
            
            System.out.println("News ordinate in ordine DECRESCENTE rispetto ai likes : ");
            while (bylikes.hasNext()){
                System.out.println( ((bylikes).next()).display() +  " ");
            }
            System.out.println();
            Iterator<CalcioNews> byfriend = cn.getFriendIterator("Caligola");
//  Ordinamento delle news in base al sistema di "friendlist" (sei mio amico? ecco queste news)
            
            System.out.println("Caligola, hai l'accesso a queste ultimissime news!!!");
            while (byfriend.hasNext()){
                System.out.println( ((byfriend).next()).display() +  " ");
            }
            System.out.println();
            
//  Fine
            
        }
        catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }
        catch (AlreadyExistingCategoryException e) {
            System.out.println("AlreadyEsistingCategoryException");
        }
        catch (WrongPasswordException e) {
            System.out.println("WrongPasswordException");
        }
        catch (CategoryNotFoundException e) {
             System.out.println("CategoryNotFoundException");
         }
        catch (FriendAlreadyAddedException e) {
            System.out.println("FriendAlreadyAddedException");
        }
        catch (FriendNotFoundException e) {
            System.out.println("FriendNotFoundException");
        }
        catch (NotInFriendlistException e) {
            System.out.println("NotInFriendlistException");
        }
        catch (DatoAlreadyAddedException e) {
             System.out.println("DatoAlreadyAddedException");
         }
        catch (DatoNotFoundException e) {
            System.out.println("DatoNotFoundException");
        }
        catch (FriendAlreadyLikedException e) {
            System.out.println("FriendAlreadyLikedException");
        }
    }
}
