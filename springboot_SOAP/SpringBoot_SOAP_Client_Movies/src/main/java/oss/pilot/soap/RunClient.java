package oss.pilot.soap;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import movies.wsdl.GetMovieByIdResponse;

public class RunClient {

    public static void main(String[] args) {
    	
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
        
        MovieClient client = context.getBean(MovieClient.class);
        
        GetMovieByIdResponse response = client.getMovieById(new Long(103));
        
        System.out.println("response: Movie id="+ response.getMovieType().getMovieId()+", title=" + response.getMovieType().getTitle() + ", category="+ response.getMovieType().getCategory());
    }

}