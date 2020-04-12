public class CalcioNews extends Data {
    
    private String news;
    
    public CalcioNews(String msg) {
        super();
        news = "CALCIONEWS : "+msg;
    }

    public String display(){
        return new String(news);
    }
    
}
