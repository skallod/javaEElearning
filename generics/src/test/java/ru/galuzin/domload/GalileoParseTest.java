package ru.galuzin.domload;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by galuzin on 24.01.2017.
 */
public class GalileoParseTest extends TestCase{

    public void test1() throws Exception{
        Map<String,String> passportNumbers = new HashMap<>();
        passportNumbers.put("KONDRATEVA/LARISAANATOLIEVNAdob", "09OCT66");
        passportNumbers.put("KONDRATEVA/LARISAANATOLIEVNA" ,"4611557546");
        String birthday = getPassportNumber(passportNumbers,
                /*indexedPersonalInfo,*/ "dob");
        System.out.println("birthday = " + birthday);
        SimpleDateFormat sdf =
                new SimpleDateFormat("ddMMMyy", Locale.US);
        java.util.Date birthDate = sdf.parse(birthday);
        System.out.println("birthDate = " + birthDate);
        String number = getPassportNumber(passportNumbers,
                /*indexedPersonalInfo,*/ "");
        System.out.println("number = " + number);

    }

    String indexedPersonalInfogetKey = "1";
    String indexedPersonalInfogetValueGetLastName = "KONDRATEVA";
    String indexedPersonalInfogetValueGetFirstName = "LARISAANATOLIEVNA";

    private String getPassportNumber(
            final Map<String, String> passportNumbers,
            /*final Map.Entry<String, GalileoPNRPersonalInfo> personalInfo,*/
            final String code) {
        String key = indexedPersonalInfogetKey + code;
        if (!passportNumbers.containsKey(key)) {
            key = indexedPersonalInfogetValueGetLastName + "/"
                    + indexedPersonalInfogetValueGetFirstName + code;
//            if (!passportNumbers.containsKey(key)) {
//                System.out.println("key = " + key);
//                for(Map.Entry<String,String> entry : passportNumbers.entrySet()){
//                    String mapKey = entry.getKey();
//                    mapKey = mapKey.replaceAll("\n", "");//убрать перенос строк
//                    if(mapKey.contains(key)){
//                        return
//                    }
//                }
//            }
        }
        return passportNumbers.get(key);
    }
}
