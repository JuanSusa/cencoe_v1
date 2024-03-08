package security;

import com.cencoe.cencoe.models.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class AppConfig {


    private IUserRepository userRepository;

    public AppConfig(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService(){

        return username ->
                (UserDetails) userRepository.findByUserNumDoc(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    }
}
