import com.faith.mybatis.tx_demo.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.faith.mybatis.tx_demo.service.UserService;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = (UserService) annotationConfigApplicationContext.getBean(UserService.class);
        userService.saveUser();
    }
}
