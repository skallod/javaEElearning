package ru.galuzin.rail_stations;

import com.google.common.collect.Maps;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.atan2;
import static ru.galuzin.rail_stations.Helper.encodeParams;

/**
 * Created by galuzin on 08.06.2016.
 */
public class Distancia {
    private static final double EARTH_RADIUS = 6371.; // Радиус Земли

    public static void main(final String[] args) throws IOException, JSONException {
        final Point subwayStationPoint =new Point(50.19297409d,30.1330471d) ;//getPoint("Россия, Москва, улица Поклонная, 12");
        final Point addressPoint = new Point(50.1333d,30.2333d);//getPoint("Россия, Москва, станция метро Парк Победы");



        // Рассчитываем расстояние между точками
        final double dlng = deg2rad(subwayStationPoint.lng - addressPoint.lng);
        final double dlat = deg2rad(subwayStationPoint.lat - addressPoint.lat);
        final double a = sin(dlat / 2) * sin(dlat / 2) + cos(deg2rad(addressPoint.lat))
                * cos(deg2rad(subwayStationPoint.lat)) * sin(dlng / 2) * sin(dlng / 2);
        final double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        System.out.println("distance: " + c * EARTH_RADIUS); // получаем расстояние в километрах
        Double temp = calculateDistance(subwayStationPoint.lat,subwayStationPoint.lng
                ,addressPoint.lat,addressPoint.lng);
        System.out.println("d2:"+temp);
        if(temp<10){
            System.out.println("fadsfas");
        }
    }

    /**
     * Класс точки, хранит значения в градусах
     *
     */
    private static class Point {
        public double lat;
        public double lng;

        public Point(final double lng, final double lat) {
            this.lng = lng;
            this.lat = lat;
        }

        @Override
        public String toString() {
            return lat + "," + lng;
        }
    }

    /**
     * Геокодирует адрес
     *
     * @param address
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private static Point getPoint(final String address) throws IOException, JSONException {
        final String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        final Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// указывает, исходит ли запрос на геокодирование от устройства с датчиком
        // местоположения
        params.put("address", address);// адрес, который нужно геокодировать
        final String url = baseUrl + '?' + encodeParams(params);// генерируем путь с параметрами
        System.out.println(url);// Можем проверить что вернет этот путь в браузере
        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о координатах можно получить по пути
        // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        JSONObject location = response.getJSONArray("results").getJSONObject(0);
        location = location.getJSONObject("geometry");
        location = location.getJSONObject("location");
        final double lng = location.getDouble("lng");// долгота
        final double lat = location.getDouble("lat");// широта
        final Point point = new Point(lng, lat);
        System.out.println(address + " " + point); // выводим адрес и точку для него
        return point;
    }

    /**
     * Преобразует значение из градусов в радианы
     *
     * @param degree
     * @return
     */
    private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }


    //boif
    public static double calculateDistance(final double lat1,
                                    final double lon1, final double lat2, final double lon2) {
        if ((lat1 != 0) && (lon1 != 0) && (lon2 != 0) && (lat2 != 0)) {
            double toRad = Math.PI / 180;
            double radius = 6371;
            double dLat = (lat2 - lat1) * toRad;
            double dLon = (lon2 - lon1) * toRad;
            double a =
                    Math.pow(Math.sin(dLat / 2.0), 2)
                            + (Math.cos(lat1 * toRad) * Math.cos(lat2 * toRad) * Math
                            .pow(Math.sin(dLon / 2.0), 2));
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return radius * c;
        }
        return -1;
    }

}
