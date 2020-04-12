public class TechNews extends Data {
    
    private String news;
    
    public TechNews(String msg) {
        super();
        news = "TECHNEWS : "+msg;
    }

    public String display(){
        return new String(news);
    }
    
}
