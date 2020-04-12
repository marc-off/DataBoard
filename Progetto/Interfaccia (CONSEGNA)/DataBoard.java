public interface DataBoard<E extends Data>{

    public void createCategory (String category, String passw);

    public void removeCategory (String category, String passw);

    public void addFriend(String category, String passw, String friend);

    public void removeFriend(String category, String passw, String friend);

    public boolean put(String passw, <E extends Data> dato, String category);

    public E get(String passw, <E extends Data> dato);

    public E remove(String passw, <E extends Data> dato);

    void insertLike(String friend, <E extends Data> dato);
    
    public List<E extends Data> getDataCategory(String passw, String category);

    public Iterator<E> getIterator(String passw);

    public Iterator<E> getFriendIterator(String friend);
    
}

