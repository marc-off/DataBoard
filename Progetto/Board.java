/**
 Si richiede di progettare, realizzare e documentare la collezione DataBoard<E extends Data>.
 
 La collezione DataBoard<E extends Data> è un contenitore di oggetti generici che estendono il tipo di dato Data.
 
 Intuitivamente la collezione si comporta come uno spazio per la memorizzazione e visualizzazioni di dati che possono essere di vario tipo ma che implementano obbligatoriamente il metodo display().
 
 La bacheca deve garantire la privacy dei dati fornendo un proprio meccanismo di gestione della condivisione dei dati. Ogni dato presente nella bacheca ha associato la categoria del dato. Il proprietario della bacheca può definire le proprie categorie e stilare una lista di contatti (amici) a cui saranno visibili i dati per ogni tipologia di categoria.
 
 I dati condivisibili sono solamente in lettura: in particolare i dati possono essere visualizzati dagli amici ma modificati solamente dal proprietario della bacheca. Gli amici possono associare un “like” al contenuto condiviso.
 */

import java.util.*;

public class Board<E extends Data> {
    /**
     * OVERVIEW: DataBoard è una tipo MUTABILE che rappresenta un contenitore di oggetti generici. Rappresenta uno spazio per la memorizzazione , visualizzazione e condivisione (SOLO IN LETTURA) di dati di elementi di E.
     *
     * ELEMENTO TIPICO:
     *  this = <owner, password, (category_1, friendlist_1), (category_2, friendlist_2), ... , (category_n, friendlist_n)>
     *
     *      dove category_i = { <elem_k, likes_k> | 0≤k≤category_i.size()-1 && elem_k is elem of <E extends data> && likes_k rappresenta il numero dei like assegnati all'elemento elem_k} || {}
     *
     *      e friendlist_i rappresenta la lista di amici (insieme di oggetti di tipo String) che può visualizzare i contenuti di category_i
     *
     *      e owner and password (di tipo String) rappresentano le credenziali del proprietario della bacheca , autorizzato alle operazioni di modifica.
     */
    
    private String proprietario = new String();
    private String password = new String();
    private ArrayList<String> Categories = new ArrayList<String>();
    //  JAVADOC: Constructs an empty list of lists with an initial capacity of ten
    private ArrayList<ArrayList<E>> Datastream = new ArrayList<ArrayList<E>>();
    //  JAVADOC: Constructs an empty list of lists with an initial capacity of ten*ten
    private ArrayList<ArrayList<String>> Friendlist = new ArrayList<ArrayList<String>>();
    //  JAVADOC: Constructs an empty list of lists with an initial capacity of ten*ten
    private ArrayList<E> datasorted = new ArrayList<E>();
    //  JAVADOC: Constructs an empty list of lists with an initial capacity of ten
    private ArrayList<E> datafriend = new ArrayList<E>();
    //  JAVADOC: Constructs an empty list of lists with an initial capacity of ten
    public Board(String owner, String passw) {
        
        if ( owner==null || passw==null ) throw new NullPointerException();
        
        proprietario = owner;
        password = passw;
        
    }
    
    /**
     *
     * INVARIANTE DI RAPPRESENTAZIONE:
     * 1. (proprietario, password, Categories, Datastream, Friendist) != null
     * 2. && Datastream.get(i) != null ∀i | 0 ≤ i ≤ Datastream.size() - 1
     *      ⇒ Datastream.get(i).get(j) != null ∀j | 0 ≤ j ≤ Datastream.get(i).get(j).size() - 1
     *      ⇒ Datastream.get(i).get(j) == null ∀j | Datastream.get(i).get(j).size ≤ j ≤ Datastream.get(i).get(j).capacity()
     * 3. && Friendlist.get(k) != null ∀k | 0 ≤ k ≤ Friendlist.size() - 1
     *      ⇒ Friendlist.get(k).get(l) != null ∀l | 0 ≤ l ≤ Friendlist.get(k).get(l).size() - 1
     *      ⇒ Friendlist.get(k).get(l) == null ∀l | Friendlist.get(k).get(l).size() ≤ l ≤ Friendlist.get(k).get(l).capacity()
     * 4. && ∀i | 0 ≤ i ≤ Datastream.size() - 1
     *      in Datastream.get(i)
     *          occurrence of 'dato', elem of <E>, is at most UNIQUE.
     * 5. && ∀j | 0 ≤ j ≤ Friendlist.size() - 1
     *      in Friendlist.get(j)
     *          occurrence of 'friend', elem of String, is at most UNIQUE.
     * 6. && in Categories
     *      occurrence of 'category', elem of String, is at most UNIQUE.
     * 7. && ∀ 'dato', elem of <E extends Data>, 'dato'.likes.valueOf() ≥ 0
     *
     * ABSTRACTION FUNCTION (Object → Abstract Value):
     *
     * AF (this) =  { <owner, password> | owner & password are String objects } ∪ { <(category_i, friendlist_i)> | 0 ≤ i ≤ Categories.size() - 1 == Friendlist.size() - 1 } , dove categories_i = { <(dato_j, likes_j)> | 0 ≤ j ≤ Datastream.get(i).size() - 1 }
     *
     *
     */
    
    // Crea una categoria di dati
    // se vengono rispettati i controlli di identità

    /**
    * DESCRIZIONE DEL METODO: Crea una categoria di dati se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ("category" != null) && (passw != null) && ( (category, friendlist) ∉ this) && (passw == this.password )
    */
    public void createCategory (String category, String passw) throws NullPointerException, AlreadyExistingCategoryException, WrongPasswordException {

        if ( category==null || passw==null ) throw new NullPointerException();
        if ( Categories.contains(category) ) throw new AlreadyExistingCategoryException();
        if ( this.password != passw ) throw new WrongPasswordException();
            
        Categories.add(category);
        Datastream.add(new ArrayList<E>());
        Friendlist.add(new ArrayList<String>());
        
    }
    /**
    * @modifies: this (se i controlli di identità sono RISPETTATI!)
    * @throws: se ("category" == null || passw == null) lancia NullPointerException (eccezione disponibile in Java, unchecked);
            se ( (category, friendlist) ∈ this) lancia AlreadyExistingExceptionCategoryException (eccezione non disponibile in Java, checked);
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked)
    * @effects: this(post) = this(pre) ∪ <category, friendlist>
    * @return: ---
    */

    
    // Rimuove una categoria di dati
    // se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Rimuove una categoria di dati se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ("category" != null) && (passw != null) && (passw == this.password)
    */
    public void removeCategory (String category, String passw) throws NullPointerException, WrongPasswordException, CategoryNotFoundException {

    if ( category==null || passw==null ) throw new NullPointerException();
    if ( this.password != passw ) throw new WrongPasswordException();
        if ( !Categories.contains(category) ) throw new CategoryNotFoundException();
    else {
    assert Categories.remove(category);

    Datastream.get(Categories.indexOf(category)).clear();
    //  JAVADOC: Returns the element (an ArrayList of elements of <E>) at the specified position (the index of the first occurrence of the specified element (category) in this list (Categories), or -1 if this list does not contain the element) in this list (Datastream), and removes all the elements from this list.
    Friendlist.get(Categories.indexOf(category)).clear();
    //  Returns the element (an ArrayList of elements of String) at the specified position (the index of the first occurrence of the specified element (category) in this list (Categories), or -1 if this list does not contain the element) in this list (Friendlist), and removes all the elements from this list.
        }
    }
    /**
    * @modifies: this (se i controlli di identità sono RISPETTATI!)
    * @throws: se ("category" == null || passw == null) lancia NullPointerException (eccezione disponibile in Java, unchecked);
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked)
            se ( (category, friendlist) ∉ this) lancia CategoryNotFoundException (eccezione non disponibile in Java, checked)
    * @effects: this(post) = this(pre) \ <category, friendlist>
    * @return: ---
    */
    
    
    // Aggiunge un amico ad una categoria di dati
    // se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Aggiunge un amico ad una categoria di dati se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ( ("category", passw, friend) != null ) && (friend ∉ friendlist in (category, friendlist)) && ( (category, friendlist) ∈ this) && (passw == this.password)
    */
    public void addFriend(String category, String passw, String friend) throws NullPointerException, WrongPasswordException, FriendAlreadyAddedException, CategoryNotFoundException {

    if ( category==null || passw==null || friend==null ) throw new NullPointerException();
    if ( this.password!=passw ) throw new WrongPasswordException();
    
    if ( Categories.indexOf(category) < 0 ) throw new CategoryNotFoundException();
    else {
        if ( Friendlist.get(Categories.indexOf(category)).indexOf(friend) >= 0 ) throw new FriendAlreadyAddedException();
    //  SIGNIFICATO DELL'ESPRESSIONE LOGICA (da JAVADOC): Returns the element (an ArrayList of elements of String) at the specified position (the index of the first occurrence of the specified element (category) in this list (Categories), or -1 if this list does not contain the element) in this list (Friendlist), and returns the index of the first occurrence of the specified element ('friend') in this list ('Friendlist'), or -1 if this list does not contain the element. More formally, returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index..
        
    else Friendlist.get(Categories.indexOf(category)).add(friend);
    //  JAVADOC: Returns the element (an ArrayList of elements of String) at the specified position (the index of the first occurrence of the specified element (category) in this list (Categories), or -1 if this list does not contain the element) in this list (Friendlist), and appends the specified element (friend) to the end of this list.
        }
    }
    /**
    * @modifies: this (se i controlli di identità vengono RISPETTATI!)
    * @throws: se ("category" == null || passw == null || friend == null) lancia NullPointerException (eccezione disponibile in Java, unchecked);
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked);
            se (friend ∈ friendlist in (category, friendlist)) lancia FriendAlreadyAddedException (eccezione non disponibile in Java, checked);
            se ( (category, friendlist) ∉ this) lancia CategoryNotFoundException (eccezione non disponibile in Java, checked)
    * @effects: this(post) = this(pre) ∪ <category, (friendlist ∪ friend)>
    * @return: ---
    */
    
    // rimuove un amico da una categoria di dati
    // se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Aggiunge un amico ad una categoria di dati se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ( ("category", passw, friend) != null ) && (friend ∈ friendlist in (category, friendlist)) && ( (category, friendlist) ∈ this) && (passw == this.password)
    */
    public void removeFriend (String category, String passw, String friend) throws NullPointerException, WrongPasswordException, FriendNotFoundException, CategoryNotFoundException {

    if ( category==null || passw==null || friend==null ) throw new NullPointerException();
    if ( this.password!=passw ) throw new WrongPasswordException();
    if ( !Friendlist.get(Categories.indexOf(category)).contains(friend) )  throw new FriendNotFoundException();
    //  SIGNIFICATO DELL'ESPRESSIONE LOGICA (da JAVADOC): Returns the element (an ArrayList of elements of String) at the specified position (the index of the first occurrence of the specified element (category) in this list (Categories), or -1 if this list does not contain the element) in this list (Friendlist), and returns true if this list contains the specified element (friend).
    if ( Categories.indexOf(category) < 0 ) throw new CategoryNotFoundException();
                
    Friendlist.get(Categories.indexOf(category)).remove(friend);
    //  JAVADOC: Returns the element (an ArrayList of elements of String) at the specified position (the index of the first occurrence of the specified element (category) in this list (Categories), or -1 if this list does not contain the element) in this list (Friendlist), and removes the first occurrence of the specified element (friend) from this list, if it is present.
            
    }
    /**
    * @modifies: this (se i controlli di identità vengono RISPETTATI!)
    * @throws: se ("category" == null || passw == null || friend == null) lancia NullPointerException (eccezione disponibile in Java, unchecked);
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked);
            se (friend ∉ friendlist in (category, friendlist)) lancia FriendNotFoundException (eccezione non disponibile in Java, checked);
            se ( (category, friendlist) ∉ this) lancia CategoryNotFoundException (eccezione non disponibile in Java, checked)
    * @effects: this(post) = this(pre) ∪ <category, (friendlist \ friend)>
    * @return: ---
    */

    
    //Inserisce un dato in bacheca
    // se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Inserisce un dato in bacheca se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ( (passw, dato, "category") != null) && (passw == this.password) && ( (category, friendlist) ∈ this)
    */
    public boolean put(String passw, E dato, String category) throws NullPointerException, WrongPasswordException, CategoryNotFoundException, DatoAlreadyAddedException {
        
    if ( passw==null || dato==null || category==null ) throw new NullPointerException();
    if ( this.password!=passw ) throw new WrongPasswordException();
    if ( !Categories.contains(category) ) throw new CategoryNotFoundException();
    if ( Datastream.get(Categories.indexOf(category)).contains(dato) ) throw new DatoAlreadyAddedException();
        
    Datastream.get(Categories.indexOf(category)).add(dato);
    //  JAVADOC: Returns the element (an ArrayList of elements of <E>) at the specified position (the index of the first occurrence of the specified element (category) in this list (Categories), or -1 if this list does not contain the element) in this list (Datastream), and appends the specified element ('dato') to the end of this list.
    return true;
    //  TRUE perchè, avendo superato i controlli inziali (tramite l'uso sobrio e controllato delle eccezioni) e soprattutto la fase di controllo di identità, sappiamo per certo che abbiamo trovato la categoria opportuna nella quale inserire la prima (e UNICA) occorrenza del dato 'dato' (NON NULL).
                   
    }
    /**
    * @modifies: this (se i controlli di identità sono RISPETTATI!)
    * @throws: se (passw == null || dato == null || "category" == null) lancia NullPointerException (eccezione disponibile in Java, unchecked);
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked);
            se ( (category, friendlist) ∉ this) lancia CategoryNotFoundException (eccezione non disponibile in Java, checked)
    * @effects: this(post) = this(pre) ∪ <(category ∪ (dato, likes)), friendlist>
    * @return: true if (passw == this.password), else false
    */
    
    //Restituisce una copia del dato in bacheca
    // se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Restituisce una copia del dato in bacheca se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ( (passw, dato) != null ) && (passw == this.password) && ( (dato, likes) ∈ this )
    */
    public E get(String passw, E dato) throws NullPointerException, WrongPasswordException, DatoNotFoundException {
        
    if ( passw==null || dato==null ) throw new NullPointerException();
    if ( this.password!=passw ) throw new WrongPasswordException();
    
    int i = 0;
    boolean search = false;
    while ( (i < Datastream.size()) && (!search) ) {
        if (Datastream.get(i).contains(dato))   search = true;
        //  SIGNIFICATO DELL'ESPRESSIONE LOGICA: Returns the element (an ArrayList of elements of <E>) at the specified position ('i') in this list (Datastream), or -1 if this list does not contain the element) in this list (Datastream), and returns true if this list contains the specified element ('dato').
        else i++;
    }
    if (!search) throw new DatoNotFoundException();
    
    return Datastream.get(i).get(Datastream.get(i).indexOf(dato));
    //  JAVADOC: Returns the element at the specified position ('i') in this list --> ArrayList<E>. Then, again, returns the element at the specified position (the index of the first (and only) occurrence of the element 'dato') in this list.
    
    //  N.B. Grazie a questa implementazione, non c'è alcun rischio di esporre la rappresentazione!

    }
    /**
    * @modifies: --- (METODO OBSERVER)
    * @throws: se (passw == null || dato == null) lancia NullPointerException (eccezione disponibile in Java, unchecked);
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked);
            se ( (dato, likes) ∉ this) lancia DatoNotFoundException (eccezione non disponibile in Java, checked)
    * @effects: --- (METODO OBSERVER)
    * @return: E dato of (dato, likes)
    */
    
    // Rimuove il dato dalla bacheca se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Rimuove il dato dalla bacheca se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ( (passw, dato) != null ) && (passw == this.password) && ( (dato, likes) ∈ this )
    */
    public E remove(String passw, E dato) throws NullPointerException, WrongPasswordException, DatoNotFoundException {

    if ( passw==null || dato==null ) throw new NullPointerException();
    if ( this.password!=passw ) throw new WrongPasswordException();
    
    int i = 0;
    boolean search = false;
    while ( i < Datastream.size() ) {
        if (Datastream.get(i).contains(dato)) {
            search = true;
            //  SIGNIFICATO DELL'ESPRESSIONE LOGICA (da JAVADOC): Returns the element (an ArrayList of elements of <E>) at the specified position ('i') in this list ('Datastream'), or -1 if this list does not contain the element) in this list ('Datastream'), and returns true if this list contains the specified element ('dato').
            Datastream.get(i).remove(dato);
            //  JAVADOC: Returns the element (an ArrayList<E>) at the specified position ('i') in this list. Then, removes the first (and, according to OUR implementation, the only one) occurrence of the specified element ('dato') from this list, if it is present.
        }
        else i++;
    }
    if (!search) throw new DatoNotFoundException();
        
    return dato;
    //  N.B. Grazie a questa implementazione, non c'è alcun rischio di esporre la rappresentazione!
                   
    }
    /**
    * @modifies: this
    * @throws: se (passw == null || dato == null) lancia NullPointerException (eccezione disponibile in Java, unchecked);
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked);
            se ( (dato, likes) ∉ this) lancia DatoNotFoundException (eccezione non disponibile in Java, checked)
    * @effects: this(post) = this(pre) ∪ <(category \ (dato, likes)), friendlist>
    * @return: ---
    */
    
    // Aggiunge un like a un dato
    // se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Aggiunge un like a un dato, se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ( (friend, dato) != null) && (friend ∈ friendlist in <category ∋ (dato, likes), friendlist>) && ( (dato, likes) ∈ this )
    */
    public void insertLike(String friend, E dato) throws NullPointerException, DatoNotFoundException, NotInFriendlistException, FriendAlreadyLikedException {

    if ( friend==null || dato==null ) throw new NullPointerException();
    
    int i = 0;
    boolean search = false;
    boolean isfriend = false;
    while ( (i < Datastream.size()) && (!isfriend) ) {
        if (Datastream.get(i).contains(dato)) {
        //  SIGNIFICATO DELL'ESPRESSIONE LOGICA (da JAVADOC): Returns the element (an ArrayList of elements of <E>) at the specified position ('i') in this list (Datastream), or -1 if this list does not contain the element) in this list (Datastream), and returns true if this list contains the specified element (dato).
            search = true;
            if ( Friendlist.get(i).contains(friend) ) {
                isfriend = true;
                if( Datastream.get(i).get(Datastream.get(i).indexOf(dato)).viewlikesfromfriends().contains(friend) )    throw new FriendAlreadyLikedException();
                //  JAVADOC: Returns the element (of ArrayList<E>) at the specified position in this list ('i', which is the first occurrence of an ArrayList<E> which contains the element 'dato' of <E>). Then, again, returns the element at the specified position (the first and only occurrence of element 'dato' of <E>, in this ArrayList<E> in Datastream, which contains this exact element 'dato') in this list. Finally, checks if the ArrayList<String> of friends who liked this element 'dato' contains the element of String 'friend'.
                else Datastream.get(i).get(Datastream.get(i).indexOf(dato)).addlike(friend);
                // JAVADOC (basically the same... until the .addlike() method) : "" "" "" .... Finally, adds a like to this element of E 'dato'.
                    
            }
        }

        else i++;
    }
    if (!search) throw new DatoNotFoundException();
    if (!isfriend) throw new NotInFriendlistException();
        

    }
    /**
    * @modifies: this
    * @throws: se (friend == null || dato == null) lancia NullPointerException (eccezione disponibile in Java, unchecked)
            se (friend ∉ friendlist in <(dato, likes), friendlist>) lancia NotInFriendlistException (eccezione non disponibile in Java, unchecked)
            se ( (dato, likes) ∉ this) lancia DatoNotFoundException (eccezione non disponibile in Java, checked)
    * @effects: this(post) = <owner, password, ... , (category ∋ (dato, likes+1), friendlist), ... >
    * @return: ---
    */
    
    // Crea la lista dei dati in bacheca su una determinata categoria
    // se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Crea la lista dei dati in bacheca su una determinata categoria se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: ( (passw, "category") |= null )&& (this.password == passw) && ( (category, friendlist) ∈ this)
    */
    public List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException, CategoryNotFoundException {

    if ( passw==null || category==null ) throw new NullPointerException();
    if ( this.password!=passw ) throw new WrongPasswordException();
    if ( !Categories.contains(category) ) throw new CategoryNotFoundException();
        
    int categoryindex = Categories.indexOf(category);
    return Datastream.get(categoryindex).subList(0, Datastream.get(categoryindex).size());
    //  JAVADOC: Returns the element (of ArrayList<E>) at the specified position in this list ('categoryindex'). Then, again, returns a view of the portion of this list between the specified fromIndex (0), inclusive, and toIndex (size() of ArrayList<E> of Datastream element at 'categoryindex' position) , exclusive.
    
    //  N.B. Grazie a questa implementazione, non c'è alcun rischio di esporre la rappresentazione!

    }
    /**
    * @modifies: --- (METODO OBSERVER)
    * @throws: se (passw == null || "category" == null) lancia NullPointerException (eccezione disponibile in Java, unchecked)
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked);
            se ( (category, friendlist) ∉ this) lancia CategoryNotFoundException (eccezione non disponibile in Java, checked)
    * @effects: --- (METODO OBSERVER)
    * @return: elements of <E extends Data> in category of (category, friendlist)
    */
    
    // restituisce un iteratore (senza remove) che genera tutti i dati in
    // bacheca ordinati rispetto al numero di like
    // se vengono rispettati i controlli di identità
    
    /**
    * DESCRIZIONE DEL METODO: Restituisce un iteratore (senza remove) che genera tutti i dati in bacheca ordinati rispetto al numero di like , se vengono rispettati i controlli di identità
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: (passw != null) && (this.password == passw)
    */
    public Iterator<E> getIterator(String passw) throws NullPointerException, WrongPasswordException {

    if ( passw==null ) throw new NullPointerException();
    if ( this.password!=passw ) throw new WrongPasswordException();
        
    datasorted.clear();
    for (int i = 0; i < Datastream.size(); i++) {
        for (int j = 0; j < Datastream.get(i).size(); j++) {
            datasorted.add(Datastream.get(i).get(j));
        }
    }
        
    Collections.sort(datasorted);
/*
    UNICA NOTA REGISTRATA DAL COMPILATORE :
    warning: [unchecked] unchecked method invocation: method sort in class Collections is applied to given types
          Collections.sort(datasorted);
                          ^
        required: List<T>
        found:    ArrayList<E>
        where T,E are type-variables:
          T extends Comparable<? super T> declared in method <T>sort(List<T>)
          E extends Data declared in class DataBoard
*/
    return datasorted.iterator();
    //  N.B. Grazie a questa implementazione, non c'è alcun rischio di esporre la rappresentazione!

    }
    /**
    * @modifies: --- (METODO OBSERVER)
    * @throws: se (passw == null) lancia NullPointerException (eccezione disponibile in Java, unchecked)
            se (this.password != passw) lancia WrongPasswordException (eccezione non disponibile in Java, checked)
    * @effects: ---
    * @return: <(dato_1, likes_1), (dato_2, likes_2), ... , (dato_m, likes_m)> dove ( i > j ) ==> likes_i > likes_j con 0 ≤ j < i ≤ m
    */
    
    // Restituisce un iteratore (senza remove) che genera tutti i dati in bacheca condivisi
    
    /**
    * DESCRIZIONE DEL METODO: Restituisce un iteratore (senza remove) che genera tutti i dati in bacheca condivisi
    * (RISPETTARE RIGOROSITÁ MATEMATICA)
    * @requires: (friend != null)
    */
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException {

    if ( friend==null ) throw new NullPointerException();
        
    datafriend.clear();
    for (int i = 0; i < Friendlist.size(); i++) {
        if ( Friendlist.get(i).contains(friend) ) {
            for ( int j = 0; j < Datastream.get(i).size(); j++)
                datafriend.add(Datastream.get(i).get(j));
            }
    }
                   
    return datafriend.iterator();
    //  N.B. Grazie a questa implementazione, non c'è alcun rischio di esporre la rappresentazione!

    }
    /**
    * @modifies: ---
    * @throws: se (passw == null) lancia NullPointerException (eccezione disponibile in Java, unchecked)
    * @effects: ---
    * @return: < (category_1, friendlist_1), ... , (category_m, friendlist_m) > dove friend ∈ friendlist_l ∀l | 1 ≤ l ≤ m
     */
    
}
