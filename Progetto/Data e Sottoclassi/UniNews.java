public class UniNews extends Data {
    
    private String news;
    
    public UniNews(String msg) {
        super();
        news = "UNINEWS : "+msg;
    }

    public String display(){
        return new String(news);
    }
    
}
