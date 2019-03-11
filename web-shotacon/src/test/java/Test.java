import com.shotacon.web.utils.movie.BoxOffice;
import com.shotacon.web.utils.movie.Mtime;

public class Test {

    
    public static void main(String[] args) {
        BoxOffice dailyBoxOffice = Mtime.dailyBoxOffice();
        
        
        System.out.println(dailyBoxOffice);
    }
}
