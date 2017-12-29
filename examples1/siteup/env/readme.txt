создать проект siteup
	настроить bat +
	добавить папок +
	настроить pom +
по видео сделать заготовку для bootstrap
сделать менюху и мин содержимое
затестить локально
попробовать удалить из tomcat все остальные приложения +
настроить tomcat на 80 порт +
использовать lets encrypt +
    смотри каталог cert_test
установил APR +
настроил connector на сертификаты из lets encrypt +

сделать redirect c 80 порта на 443 +
Connector port="80?
 enableLookups="false"
 redirectPort="443?
 web.xml /web-app
 <security-constraint>
 <web-resource-collection>
 <web-resource-name>Protected Context</web-resource-name>
 <url-pattern>/*</url-pattern>
 </web-resource-collection>
 <!-- auth-constraint goes here if you requre authentication -->
 <user-data-constraint>
 <transport-guarantee>CONFIDENTIAL</transport-guarantee>
 </user-data-constraint>
 </security-constraint>
 
 Запрос текста по ajax и установка в container
 Вставить в listener, filter, servlet id через session
 Настроить логирование
 
 
 
 GC_LOG=" -verbose:gc -Xloggc:./logs/gc_pid_%p.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:
+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=10M"
    
установка службы для tomcat
тестовый ajax с jquery +++
с помощью jquery заменить контент в container

tomcat Xms 300 Xmx 350
узнать что может tomcat manager, кроме развертки приложения
request listener add attribute -> do get get attr

сделать basket через session.setAttribute
    затестить очищается ли память после session timeout

отдавать с сервера тестовый json arr
    выбрать контролы bootstrap и в них отобразить + кнопка подробнее
    скрыть элементы , отобразить конкретный элемент + кнопка "в корзину"/"продолжить покупки"

выбрать технологию маппинга json в объекты - можно jackson для начала
при логоут надо делать в сервлете HttpSession.invalidate() - сброс сессии ,  затем делать форвард на index.html

возможно стоит хранить картинки на https://ru.imgbb.com
есть возможность в postgres хранить jsonb - наверное можно быстро отдавать на клиента

идея хранить в таблице основные поля и в нейже хранить в blob json json-lg

установить postgres на ubuntu

попробовать json положить в файл и отдать в браузер - там разложить по полям
    норм json превратился в object
        теперь надо научиться с ним работать с пом jquery

сделать аутентификацию на tomcat
    скачать драйвер для postgres 9.6 +
    попытаться натроить аутентификацию

столкнулся с проблемой : хочу чтобы везде были одинаковые относительные пути, но не получается index в основном каталоге остальное в подкаталогах
