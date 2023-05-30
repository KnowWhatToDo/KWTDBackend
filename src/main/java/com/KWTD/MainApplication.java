package com.KWTD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
@EnableWebMvc
public class MainApplication {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = MainApplication.class.getClassLoader();

        File file = new File(classLoader.getResource("serviceAccountKey.json").getFile());

        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

        System.out.println("Path: " + file.getAbsolutePath());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://.kwtd-16e46.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        SpringApplication.run(MainApplication.class, args);
    }

}
