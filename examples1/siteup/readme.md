Start server local with undertow and h2 memery mode :
    ./src/test/ServerTest.main //only backend
Static html content :
    ./html
Deploy server to VDS :
    run scripts from ../devops project
        prepare_ubuntu
        setup nginx
        setup tomcat
        setup postgres
    set ssh parameters in :
        build.gradle remotes task
    run task
        gradlew deploy
