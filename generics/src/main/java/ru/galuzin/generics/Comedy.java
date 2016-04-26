package ru.galuzin.generics;

import ru.galuzin.generics.radio_item.Resident;

import java.math.BigDecimal;

/**
 * Created by User on 17.04.2016.
 */
public class Comedy extends Radio<Comedy,Resident<BigDecimal>>{

    public Comedy(){
        super.setRadio(this);
        super.getItemsList().add(new Resident<BigDecimal>("kashtan",new BigDecimal(230000.456D)));
        super.getItemsList().add(new Resident<BigDecimal>("garic",new BigDecimal(765342345.67867D)));
    }

    private String curretnResident = "galigin";

    public String getCurretnResident() {
        return curretnResident;
    }

    public void setCurretnResident(String curretnResident) {
        this.curretnResident = curretnResident;
    }

    public void information(){
        System.out.println(""+super.getRadio().getCurretnResident());
        for(Resident<BigDecimal> resident : super.getItemsList()){
            System.out.println("resident="+resident);
            System.out.println("resident real salary= " + resident.getSalary());
        }
//       todo Radio<Comedy,Resident<BigDecimal>> return this;
    }
}
