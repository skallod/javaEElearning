������� ������ siteup
	��������� bat +
	�������� ����� +
	��������� pom +
�� ����� ������� ��������� ��� bootstrap
������� ������ � ��� ����������
��������� ��������
����������� ������� �� tomcat ��� ��������� ���������� +
��������� tomcat �� 80 ���� +
������������ lets encrypt +
    ������ ������� cert_test
��������� APR +
�������� connector �� ����������� �� lets encrypt +

������� redirect c 80 ����� �� 443 +
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
 
 ������ ������ �� ajax � ��������� � container
 �������� � listener, filter, servlet id ����� session
 ��������� �����������
 
 
 
 
 
 
 
 
 
 GC_LOG=" -verbose:gc -Xloggc:./logs/gc_pid_%p.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:
+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=10M"
    
��������� ������ ��� tomcat
�������� ajax � jquery
� ������� jquery �������� ������� � container

tomcat Xms 300 Xmx 350
������ ��� ����� tomcat manager, ����� ��������� ����������
request listener add attribute -> do get get attr