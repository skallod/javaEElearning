while true
do
curl -X POST \
  'http://127.0.0.1:8889/greeting' \
  -H 'Authorization: Basic Y29ydGVvc3Bzazo0YjgzIWI3NWQpYjhlZGU4NDUwZmNk' \
  -H 'cache-control: no-cache' \
  -H 'content-type: text/xml' \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:trav="urn://severstal.com/pi/HCM/TRAVEL">   <soapenv:Header/>   <soapenv:Body>      <trav:mt_order_data>         <PERNR>@DIC:Код:</PERNR>         <REINR>@DIC:Номер заявки:</REINR>         <ComplexID>709339</ComplexID>         <ReserveID>1661654</ReserveID>         <OperationType>orderticketed</OperationType>         <Description>Заказ выполнен: Омск, Маяк (Омь) 30.09.18-12.10.18</Description>      </trav:mt_order_data>   </soapenv:Body></soapenv:Envelope>'
sleep 1
done