package com.mycompany.myapp.franquicia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class MyApplicationRunner implements ApplicationRunner {


    @Autowired
    private SaveMenus saveMenus;
 

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("POST from MyApplicationRunner");
        saveMenus.saveMenus();

        }
        }
    

