public class FakeNews extends Data {
    
    private String news;
    
    public FakeNews(String msg) {
        super();
        news = "FAKENEWS : "+msg;
    }

    public String display(){
        return new String(news);
    }
    
}
