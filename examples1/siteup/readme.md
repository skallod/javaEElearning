# Start server local with undertow and h2 memery mode :  
    ./src/test/ServerTest.main //only backend  
# Static html content :  
    ./html
# nginx
    for local develop
      edit hosts file add 127.0.0.1 siteup.local
      run nginx with src/test/resources/nginx.conf
# Deploy server to VDS :  
    run scripts from ../devops project  
        prepare_ubuntu  
        setup nginx  
        setup tomcat  
        setup postgres  
    run db init scripts :  
        ../db-service/init  
    set ssh parameters in :  
        build.gradle remotes task  
    run task  
        ./gradlew deploy  
  
# Tests  
    ./test/java/ru/rearitem/AuthTest
    ./../db-service/ru/galuzin/db_service/DbServiceTest  
  
