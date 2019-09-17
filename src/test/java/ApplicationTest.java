import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ApplicationTest {



    @Test
    public void ConfigureGlobal() {
        System.out.println(new BCryptPasswordEncoder().encode("Zapp123"));
    }
}
