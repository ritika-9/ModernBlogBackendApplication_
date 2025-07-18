package com.ritika.blog;

import com.ritika.blog.config.AppConstant;
import com.ritika.blog.entities.Role;
import com.ritika.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {

        SpringApplication.run(BlogAppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(passwordEncoder.encode("xyz"));

        // to create the role of admin and user if not present
        try{
            Role role=new Role();
            role.setId(AppConstant.ADMIN_USER);
            role.setName("ADMIN_USER");

            Role role1=new Role();
            role1.setId(AppConstant.NORMAL_USER);
            role1.setName("NORMAL_USER");

            List<Role> roles=List.of(role,role1);
            List<Role> result=this.roleRepo.saveAll(roles);

            result.forEach(r->{
                System.out.println(r.getName());
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
