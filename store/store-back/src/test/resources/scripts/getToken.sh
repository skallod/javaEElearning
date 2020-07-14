#while true
#do
curl -X GET \
  'http://localhost:8181/api/v1/book/test' \
  -H 'authorization: Basic ajpw' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/x-www-form-urlencoded' \
  -H 'testHeader: test-header-1'
#sleep 1
#done
#-H 'authorization: Basic ajpw' \
#-H 'authorization: Basic YWRtaW46cA==' \
#admin
