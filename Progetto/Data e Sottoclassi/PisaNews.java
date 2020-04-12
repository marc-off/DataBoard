public class PisaNews extends Data {
    
    private String news;
    
    public PisaNews(String msg) {
        super();
        news = "PISANEWS : "+msg;
    }

    public String display(){
        return new String(news);
    }
    
}
