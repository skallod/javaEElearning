package ru.galuzin.camel_servlet.howtio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class HawtioExecutor {

    public void exec() throws Exception{
        System.setProperty("hawtio.authenticationEnabled", "false");
//        io.hawt.embedded.Main hawt = new io.hawt.embedded.Main();
//        hawt.setWar(new File(HawtioExecutor.class.getResource("/hawtio-default-2.3.0.war").toURI()).toString());
//        //hawt.setOpenUrl();
//        hawt.run();
    }

}
