//package ru.galuzin.drools_test.bof;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by galuzin on 23.09.2016.
// */
//
//public class BaseAirPropertySet implements Serializable {
//    private static final long serialVersionUID = 7268012328461765404L;
//
//    private String agentLocation;
//
//    private String agencyTchCode;
//
//    private final Set<String> airlines = new HashSet<String>();
//
//    private final Set<String> operatingAirlines = new HashSet<String>();
//
//    private final Set<String> allLocations = new HashSet<String>();
//
//    private Date arrivalDate;
//
//    private final Set<String> arrivalLocations = new HashSet<String>();
//
//    private Money baseFare;
//
//    private final Set<String> blankOwners = new HashSet<String>();
//
//    private final List<String> bookingClasses = new LinkedList<String>();
//
//    private String carrierNumber;
//
//    private final Set<String> carriers = new HashSet<String>();
//
//    private final Set<String> connectLocations = new HashSet<String>();
//
//    private Date currentDate;
//
//    private Date departureDate;
//
//    private final List<Date> departureDates = new LinkedList<Date>();
//
//    private final Set<String> departureLocations = new HashSet<String>();
//
//    private boolean directContract;
//
//    private boolean eticket;
//
//    private double fare;
//
//    private String fareBasis;
//
//    private final List<String> fareBasises = new LinkedList<String>();
//
//    private final List<String> flightNumbers = new LinkedList<String>();
//
//    private final List<Integer> flightNumbersInt = new LinkedList<Integer>();
//
//    private boolean groupFare;
//
//    private boolean interline;
//
//    private final List<String[]> interlineCarriers = new LinkedList<String[]>();
//
//    private final Set<String> paymentTypes = new HashSet<String>();
//
//    private String pnr;
//
//    private String salesPointCity;
//
//    private String salesPointRegion;
//
//    private final List<String> serviceClasses = new LinkedList<String>();
//
//    private final Set<String> taxCodes = new HashSet<String>();
//
//    // This field indicates the type of Product (!not proxy); for example ProductCategory.AIR.name()
//    private String targetType;
//
//    private String tariffType;
//
//    private String transportationType;
//
//    private String validator;
//
//    private String productCategory;
//
//    private String mcoCategory;
//
//    private final List<String[]> visitedCities = new LinkedList<String[]>();
//
//    private final List<List<String[]>> flightSegments =
//            new LinkedList<List<String[]>>();
//
//    private final List<String[]> segmentAirlines = new LinkedList<String[]>();
//
//    private final List<Boolean> domesticFlightsAC = new LinkedList<Boolean>();
//
//    private final List<Boolean> connectedFlights = new LinkedList<Boolean>();
//
//    private int numOfSegments;
//
//    private boolean oneWayTicket = true;
//
//    private Date issueDate;
//
//    private BigDecimal totalPrice;
//
//    private String passengerType;
//
//    private String tourCode;
//
//    private final Set<String> firstSegmentAirline = new HashSet<String>();
//
//    private int numOfInterlineSegments = 0;
//
//    private boolean simpleTaxed = false;
//
//    private final Set<String> primaryCarrierCountry = new HashSet<String>();
//
//    private boolean forcedRefund;
//
//    private String gdsName;
//
//    private String reservationType;
//
//    private String passengerStatus;
//
//    private String pcc;
//
//    private int numOfAirlines;
//
//    private boolean codeShare;
//
//    private boolean guaranteedTariff;
//
//    private String partnerCode;
//
//    private String clientCode;
//
//    private boolean diffConnectAirports;
//
//    private boolean diffConnectOnFirstLeg;
//
//    private boolean gift;
//
//    private boolean throughTariff;
//
//    private boolean searchMode;
//
//    private boolean combined;
//
//    private int daysBeforeTravel;
//
//    private final Set<String> profileGroups = new HashSet<String>();
//
//    private boolean sta;
//
//    public String getAgentLocation() {
//        return agentLocation;
//    }
//
//    public void setAgentLocation(final String value) {
//        agentLocation = value;
//    }
//
//    public String getAgencyTchCode() {
//        return agencyTchCode;
//    }
//
//    public void setAgencyTchCode(final String value) {
//        agencyTchCode = value;
//    }
//
//    public Date getArrivalDate() {
//        return arrivalDate;
//    }
//
//    public void setArrivalDate(final Date value) {
//        arrivalDate = value;
//    }
//
//    public Money getBaseFare() {
//        return baseFare;
//    }
//
//    public void setBaseFare(final Money value) {
//        baseFare = value;
//    }
//
//    public String getCarrierNumber() {
//        return carrierNumber;
//    }
//
//    public void setCarrierNumber(final String value) {
//        carrierNumber = value;
//    }
//
//    public Date getCurrentDate() {
//        return currentDate;
//    }
//
//    public void setCurrentDate(final Date value) {
//        currentDate = value;
//    }
//
//    public Date getDepartureDate() {
//        return departureDate;
//    }
//
//    public void setDepartureDate(final Date value) {
//        departureDate = value;
//    }
//
//    public boolean isDirectContract() {
//        return directContract;
//    }
//
//    public void setDirectContract(final boolean value) {
//        directContract = value;
//    }
//
//    public boolean isEticket() {
//        return eticket;
//    }
//
//    public void setEticket(final boolean value) {
//        eticket = value;
//    }
//
//    public double getFare() {
//        return fare;
//    }
//
//    public void setFare(final double value) {
//        fare = value;
//    }
//
//    public String getFareBasis() {
//        return fareBasis;
//    }
//
//    public void setFareBasis(final String value) {
//        fareBasis = value;
//    }
//
//    public boolean isGroupFare() {
//        return groupFare;
//    }
//
//    public void setGroupFare(final boolean value) {
//        groupFare = value;
//    }
//
//    public boolean isInterline() {
//        return interline;
//    }
//
//    public void setInterline(final boolean value) {
//        interline = value;
//    }
//
//    public String getPnr() {
//        return pnr;
//    }
//
//    public void setPnr(final String value) {
//        pnr = value;
//    }
//
//    public String getSalesPointCity() {
//        return salesPointCity;
//    }
//
//    public void setSalesPointCity(final String value) {
//        salesPointCity = value;
//    }
//
//    public String getSalesPointRegion() {
//        return salesPointRegion;
//    }
//
//    public void setSalesPointRegion(final String value) {
//        salesPointRegion = value;
//    }
//
//    public String getTargetType() {
//        return targetType;
//    }
//
//    public void setTargetType(final String value) {
//        targetType = value;
//    }
//
//    public String getTariffType() {
//        return tariffType;
//    }
//
//    public void setTariffType(final String value) {
//        tariffType = value;
//    }
//
//    public String getTransportationType() {
//        return transportationType;
//    }
//
//    public void setTransportationType(final String value) {
//        transportationType = value;
//    }
//
//    public String getValidator() {
//        return validator;
//    }
//
//    public void setValidator(final String value) {
//        validator = value;
//    }
//
//    public String getProductCategory() {
//        return productCategory;
//    }
//
//    public void setProductCategory(final String value) {
//        productCategory = value;
//    }
//
//    public String getMcoCategory() {
//        return mcoCategory;
//    }
//
//    public void setMcoCategory(final String value) {
//        mcoCategory = value;
//    }
//
//    public int getNumOfSegments() {
//        return numOfSegments;
//    }
//
//    public void setNumOfSegments(final int value) {
//        numOfSegments = value;
//    }
//
//    public boolean isOneWayTicket() {
//        return oneWayTicket;
//    }
//
//    public void setOneWayTicket(final boolean value) {
//        oneWayTicket = value;
//    }
//
//    public Date getIssueDate() {
//        return issueDate;
//    }
//
//    public void setIssueDate(final Date value) {
//        issueDate = value;
//    }
//
//    public Set<String> getAirlines() {
//        return airlines;
//    }
//
//    public Set<String> getOperatingAirlines() {
//        return operatingAirlines;
//    }
//
//    public int getNumOfAirlines() {
//        return numOfAirlines;
//    }
//
//    public void setNumOfAirlines(final int value) {
//        numOfAirlines = value;
//    }
//
//    public Set<String> getAllLocations() {
//        return allLocations;
//    }
//
//    public Set<String> getArrivalLocations() {
//        return arrivalLocations;
//    }
//
//    public Set<String> getBlankOwners() {
//        return blankOwners;
//    }
//
//    public List<String> getBookingClasses() {
//        return bookingClasses;
//    }
//
//    public Set<String> getCarriers() {
//        return carriers;
//    }
//
//    public Set<String> getConnectLocations() {
//        return connectLocations;
//    }
//
//    public List<Date> getDepartureDates() {
//        return departureDates;
//    }
//
//    public Set<String> getDepartureLocations() {
//        return departureLocations;
//    }
//
//    public List<String> getFareBasises() {
//        return fareBasises;
//    }
//
//    public List<String> getFlightNumbers() {
//        return flightNumbers;
//    }
//
//    public List<Integer> getFlightsNumbersInt() {
//        return flightNumbersInt;
//    }
//
//    public List<String[]> getInterlineCarriers() {
//        return interlineCarriers;
//    }
//
//    public Set<String> getPaymentTypes() {
//        return paymentTypes;
//    }
//
//    public List<String> getServiceClasses() {
//        return serviceClasses;
//    }
//
//    public List<String[]> getVisitedCities() {
//        return visitedCities;
//    }
//
//    public BigDecimal getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(final BigDecimal value) {
//        totalPrice = value;
//    }
//
//    public void setPassengerType(final String val) {
//        passengerType = val;
//    }
//
//    public String getPassengerType() {
//        return passengerType;
//    }
//
//    public void setTourCode(final String val) {
//        tourCode = val;
//    }
//
//    public String getTourCode() {
//        return tourCode;
//    }
//
//    public Set<String> getFirstSegmentAirline() {
//        return firstSegmentAirline;
//    }
//
//    public List<List<String[]>> getFlightSegments() {
//        return flightSegments;
//    }
//
//    public void setNumOfInterlineSegments(final int val) {
//        numOfInterlineSegments = val;
//    }
//
//    public int getNumOfInterlineSegments() {
//        return numOfInterlineSegments;
//    }
//
//    public List<String[]> getSegmentAirlines() {
//        return segmentAirlines;
//    }
//
//    public void setSimpleTaxed(final boolean val) {
//        simpleTaxed = val;
//    }
//
//    public boolean isSimpleTaxed() {
//        return simpleTaxed;
//    }
//
//    public Set<String> getPrimaryCarrierCountry() {
//        return primaryCarrierCountry;
//    }
//
//    public void setForcedRefund(final boolean value) {
//        forcedRefund = value;
//    }
//
//    public boolean isForcedRefund() {
//        return forcedRefund;
//    }
//
//    public List<Boolean> getDomesticFlightsAC() {
//        return domesticFlightsAC;
//    }
//
//    public List<Boolean> getConnectedFlights() {
//        return connectedFlights;
//    }
//
//    public String getGdsName() {
//        return gdsName;
//    }
//
//    public void setGdsName(final String value) {
//        gdsName = value;
//    }
//
//    public String getReservationType() {
//        return reservationType;
//    }
//
//    public void setReservationType(final String value) {
//        reservationType = value;
//    }
//
//    public Set<String> getTaxCodes() {
//        return taxCodes;
//    }
//
//    public String getPassengerStatus() {
//        return passengerStatus;
//    }
//
//    public void setPassengerStatus(final String value) {
//        passengerStatus = value;
//    }
//
//    public String getPcc() {
//        return pcc;
//    }
//
//    public void setPcc(final String value) {
//        pcc = value;
//    }
//
//    public boolean isCodeShare() {
//        return codeShare;
//    }
//
//    public void setCodeShare(final boolean value) {
//        codeShare = value;
//    }
//
//    public boolean isGuaranteedTariff() {
//        return guaranteedTariff;
//    }
//
//    public void setGuaranteedTariff(final boolean value) {
//        guaranteedTariff = value;
//    }
//
//    public String getPartnerCode() {
//        return partnerCode;
//    }
//
//    public void setPartnerCode(final String value) {
//        partnerCode = value;
//    }
//
//    public String getClientCode() {
//        return clientCode;
//    }
//
//    public void setClientCode(final String value) {
//        clientCode = value;
//    }
//
//    public boolean isDiffConnectAirports() {
//        return diffConnectAirports;
//    }
//
//    public void setDiffConnectAirports(final boolean value) {
//        this.diffConnectAirports = value;
//    }
//
//    public boolean isDiffConnectOnFirstLeg() {
//        return diffConnectOnFirstLeg;
//    }
//
//    public void setDiffConnectOnFirstLeg(final boolean value) {
//        this.diffConnectOnFirstLeg = value;
//    }
//
//    public boolean isGift() {
//        return gift;
//    }
//
//    public void setGift(final boolean value) {
//        this.gift = value;
//    }
//
//    public boolean isThroughTariff() {
//        return throughTariff;
//    }
//
//    public void setThroughTariff(final boolean value) {
//        this.throughTariff = value;
//    }
//
//    public boolean isSearchMode() {
//        return searchMode;
//    }
//
//    public void setSearchMode(final boolean value) {
//        searchMode = value;
//    }
//
//    public boolean isCombined() {
//        return combined;
//    }
//
//    public void setCombined(final boolean value) {
//        combined = value;
//    }
//
//    public Pair<Set<String>> getCombinedAirlines() {
//        return new Pair<Set<String>>(carriers, airlines);
//    }
//
//    public int getDaysBeforeTravel() {
//        return daysBeforeTravel;
//    }
//
//    public void setDaysBeforeTravel(final int value) {
//        this.daysBeforeTravel = value;
//    }
//
//    public boolean isSta() {
//        return sta;
//    }
//
//    public void setSta(final boolean value) {
//        sta = value;
//    }
//
//    public Set<String> getProfileGroups() {
//        return profileGroups;
//    }
//}
