//package ru.galuzin.drools_test.bof;
//
///**
// * Created by galuzin on 23.09.2016.
// */
//public class ProductPropertySet extends BaseAirPropertySet {
//private static final long serialVersionUID = -6212021180724788310L;
//
//private static <E extends Enum<?>> String getEnumName(final E value) {
//        if (value == null) {
//        return null;
//        }
//        return value.name();
//        }
//
//private static String getString(final String value) {
//        return TextUtil.isBlank(value) ? null : value.trim();
//        }
//
//private static double getDoubleValue(final BigDecimal value) {
//        if (value == null) {
//        return 0;
//        }
//        return value.doubleValue();
//        }
//
//private static boolean isSameCarriers(
//final DictionaryReference<Airline> ref1,
//final DictionaryReference<Airline> ref2) {
//        if (ref1 == null) {
//        return ref2 == null;
//        }
//        if (ref2 == null) {
//        return false;
//        }
//        String code1 = ref1.getCode();
//        String code2 = ref2.getCode();
//        if (code1.equals(code2)) {
//        return true;
//        }
//        for (Airline ac1 : DictHelper.getAirlinesByAnyCode(code1, null)) {
//        for (Airline ac2 : DictHelper.getAirlinesByAnyCode(code2, null)) {
//        if (ac1.equals(ac2)) {
//        return true;
//        }
//        }
//        }
//        return false;
//        }
//
//@SuppressWarnings("nls")
//private Airline addAirlines(final Set<String> collection,
//final DictionaryReference<Airline> airlineRef,
//final String airlineNum, final String ticketNo,
//final String propertyName) {
//        Airline blankOwner = Environment.getPublished(DictionaryCache.class)
//        .resolveReference(airlineRef);
//        if ((blankOwner == null) && !TextUtil.isBlank(airlineNum)) {
//        LogFactory.getLog(ProductPropertySet.class)
//        .warn(propertyName + " is empty in product " + ticketNo
//        + ". Airline number " + airlineNum + " will be used");
//        List<Airline> arls = DictHelper.getAirlinesByNumber(airlineNum,
//        product.getIssueDate());
//        if (!arls.isEmpty()) {
//        blankOwner = arls.get(0);
//        }
//        if (arls.size() > 1) {
//        LogFactory.getLog(ProductPropertySet.class)
//        .warn("more than one airlines for " + propertyName
//        + " found for number " + airlineNum);
//        }
//        }
//        if (blankOwner != null) {
//        if (collection != null) {
//        collection.add(blankOwner.getCode());
//        }
//        return blankOwner;
//        }
//        if ((airlineRef != null) && (collection != null)) {
//        collection.add(airlineRef.getCode());
//        }
//        return null;
//        }
//
//private final RuleProxy<ProductPropertySet, ?> proxy;
//
//private Product product;
//
//private String stFareBasis;
//
//private boolean throughTariff;
//
//public ProductPropertySet(
//final RuleProxy<ProductPropertySet, ?> ruleProxy) {
//        proxy = ruleProxy;
//        }
//
//public Product getProduct() {
//        return product;
//        }
//
//public void init(final Product prod, final boolean searchMode)
//        throws Exception {
//        setSearchMode(searchMode);
//        init(prod);
//        }
//
//public void init(final Product prod) throws Exception {
//        throughTariff = true;
//        RuleLogTracer tracer = proxy.getTracer();
//        tracer.traceEnteringMethod(ProductPropertySet.class, "init()"); //$NON-NLS-1$
//        try {
//        ProductCategory prCat = prod.getProductCategory();
//        MCOCategory mcoCat = prod.getMcoCategory();
//        product = prod;
//        if ((prCat == ProductCategory.EXCESS_BAGAGE)
//        || ((prCat == ProductCategory.MCO)
//        && ((mcoCat == MCOCategory.PENALTY)))) {
//
//        Product refund =
//        AirProductHelper.findRelatedAirProduct(prod, null, false);
//        if (refund != null) {
//        init(refund);
//        setBaseFare(AirProductHelper.getBaseFare(prod));
//        setTargetType(prod.getMcoCategory().name());
//        setIssueDate(prod.getIssueDate());
//        setFare(getDoubleValue(
//        AirProductHelper.getEquivalentFare(prod)));
//        product = prod;
//        getPaymentTypes().clear();
//        updatePaymentTypes(prod.getVendorFops());
//        setProductCategory(getEnumName(prCat));
//        setMcoCategory(getEnumName(mcoCat));
//        return;
//        }
//        }
//        Map<String, Object> params = initializeProductFields(prod);
//        AirlineReference carrierReference =
//        (AirlineReference) params.get("carrier"); //$NON-NLS-1$
//        String systemNumber = prod.getSystemNumber();
//        initializeSegmentFields(prod.getSegmentTariffs(), carrierReference,
//        systemNumber);
//        setTotalPrice(
//        BookingHelper.calculateProductPrice(product).getTotal());
//        setGuaranteedTariff(true);
//        for (ProductFare fare : product.getFares()) {
//        if (!fare.isIsGuaranteed()) {
//        setGuaranteedTariff(false);
//        }
//        }
//        if (prod.getReservation() != null) {
//        if (product.getReservation().getBookingFile() != null) {
//        EntityContainer<Organization> agencyCtr = Environment
//        .getPublished(EntityCache.class).resolve(product
//        .getReservation().getBookingFile().getAgency());
//        if (agencyCtr != null) {
//        setSimpleTaxed(agencyCtr.getEntity().isSimpleTaxed());
//        }
//        EntityContainer<Organization> partnerCtr =
//        Environment.getPublished(EntityCache.class)
//        .resolve(prod.getReservation().getBookingFile()
//        .getPartnerProfile());
//        if (partnerCtr != null) {
//        setPartnerCode(partnerCtr.getEntity().getCode());
//        getProfileGroups().addAll(ProfileHelper
//        .getGroupsCode(partnerCtr.getEntity()));
//        }
//        }
//        EntityContainer<Person> clientCtr =
//        Environment.getPublished(EntityCache.class)
//        .resolve(prod.getReservation().getClient());
//        if (clientCtr != null) {
//        setClientCode(clientCtr.getEntity().getCode());
//        }
//        if (prod.getReservation().getYouthTariff() != null) {
//        YouthTariff yt = Environment
//        .getPublished(DictionaryCache.class).resolveReference(
//        prod.getReservation().getYouthTariff());
//        if (yt != null) {
//        setSta(yt.isSTATariff());
//        }
//        }
//        }
//        setGift(prod.isGift());
//        setThroughTariff(throughTariff);
//        setCombined(prod.isCombined());
//        } catch (Exception e) {
//        tracer.traceError("unable to initialize property set", e); //$NON-NLS-1$
//        throw e;
//        } finally {
//        tracer.traceObject(getClass().getSimpleName(), this);
//        tracer.traceExitingMethod(ProductPropertySet.class, "init()"); //$NON-NLS-1$
//        }
//        }
//
//private void initializeSegmentFields(
//final List<SegmentTariff> segmentTariffs,
//final AirlineReference carrierReference,
//final String systemNumber) {
//        boolean firstSegment = true;
//        String arrivalCityCode = null;
//        String firstDepartureCityCode = null;
//        String firstArrivalCityCode = null;
//        LinkedList<String> cities = new LinkedList<String>();
//        String prevCode = null;
//        Set<String> arrivalCodes = new LinkedHashSet<String>();
//        int numOfInterlineSegments = 0;
//        int legsCount = 0;
//        for (int n = 0; n < segmentTariffs.size(); n++) {
//        SegmentTariff segmentTariff = segmentTariffs.get(n);
//        for (int m = 0; m < segmentTariff.getSegments().size(); m++) {
//        Segment segment = segmentTariff.getSegments().get(m);
//        if (segment.isStarting()) {
//        legsCount++;
//        if (prevCode != null) {
//        arrivalCodes.add(prevCode);
//        }
//        }
//        setNumOfSegments(getNumOfSegments() + 1);
//        String departureCityCode = null;
//        DictionaryReference<GeoLocation> departure =
//        segment.getDepartureLocation();
//        if (departure != null) {
//        departureCityCode = departure.getCode();
//
//        if (!TextUtil.isBlank(departureCityCode)) {
//        Set<String> geoCodes = new HashSet<String>();
//        geoCodes.add(departureCityCode);
//        geoCodes.addAll(
//        ProxyHelper.collectGeoCodes(geoCodes, true));
//        if (segment.isStarting()) {
//        getDepartureLocations().addAll(geoCodes);
//        } else {
//        if ((arrivalCityCode != null)
//        && !departureCityCode.equals(arrivalCityCode)) {
//        setDiffConnectAirports(true);
//        if (legsCount == 1) {
//        setDiffConnectOnFirstLeg(true);
//        }
//        }
//        getConnectLocations().addAll(geoCodes);
//        }
//        }
//        GeoLocation departureCity =
//        DictHelper.findCityByAirport(departureCityCode);
//
//        if (departureCity != null) {
//        departureCityCode = departureCity.getCode();
//        segment.setDepartCityCode(departureCityCode);
//        }
//        }
//        if (firstSegment) {
//        if (!TextUtil.isBlank(departureCityCode)) {
//        cities.add(departureCityCode);
//        firstDepartureCityCode = departureCityCode;
//        }
//        addAirlines(getFirstSegmentAirline(), segment.getAirline(),
//        null, systemNumber, "First segment airline"); //$NON-NLS-1$
//        setDaysBeforeTravel(
//        MiscUtil.deltaDays(segment.getStartDate(), new Date()));
//        }
//        getConnectedFlights().add(
//        Boolean.valueOf((segmentTariff.getSegments().size() > 1)
//        && (m < (segmentTariff.getSegments().size() - 1))));
//        getFlightNumbers().add(getString(segment.getFlightNo()));
//        try {
//        getFlightsNumbersInt()
//        .add(Integer.valueOf(getString(segment.getFlightNo())));
//        } catch (NumberFormatException e) {
//        //no-op
//        }
//        getServiceClasses()
//        .add(segmentTariff.getClassOfService() == null ? null
//        : segmentTariff.getClassOfService().name());
//        Date startDate = segment.getStartDate();
//        getDepartureDates().add(startDate);
//        String fareBasis = getString(segmentTariff.getFareBasis());
//        if (TextUtil.isBlank(stFareBasis)) {
//        stFareBasis = fareBasis;
//        } else if (!stFareBasis.equals(fareBasis)) {
//        throughTariff = false;
//        }
//        setFareBasis(fareBasis);
//        getFareBasises().add(fareBasis);
//        getBookingClasses().add(TextUtil.isBlank(fareBasis) ? null
//        : "" + fareBasis.charAt(0)); //$NON-NLS-1$
//        DictionaryReference<Airline> airlineReference =
//        segment.getAirline();
//        boolean sameCarriers =
//        isSameCarriers(carrierReference, airlineReference);
//        boolean interline = !sameCarriers;
//        setInterline(isInterline() | interline);
//        Set<String> segCarrierCodes = new HashSet<String>();
//        if (airlineReference != null) {
//        if (!getAirlines().contains(airlineReference.getCode())) {
//        setNumOfAirlines(getNumOfAirlines() + 1);
//        }
//        addAirlines(segCarrierCodes, airlineReference, null,
//        systemNumber, "Segment airline"); //$NON-NLS-1$
//        getAirlines().addAll(segCarrierCodes);
//        getSegmentAirlines().add(segCarrierCodes
//        .toArray(new String[segCarrierCodes.size()]));
//        if (interline) {
//        getInterlineCarriers().add(segCarrierCodes
//        .toArray(new String[segCarrierCodes.size()]));
//        } else {
//        getInterlineCarriers().add(null);
//        }
//        } else {
//        getInterlineCarriers().add(null);
//        getSegmentAirlines().add(new String[0]);
//        }
//        segCarrierCodes.clear();
//        if (segment.getOperatingAirline() != null) {
//        addAirlines(segCarrierCodes, segment.getOperatingAirline(),
//        null, systemNumber, "Operating airline"); //$NON-NLS-1$
//        getOperatingAirlines().addAll(segCarrierCodes);
//        }
//        if ((startDate != null) && ((getDepartureDate() == null)
//        || getDepartureDate().after(startDate))) {
//        setDepartureDate(startDate);
//        }
//        Date endDate = segment.getEndDate();
//        if ((endDate != null) && ((getArrivalDate() == null)
//        || getArrivalDate().before(endDate))) {
//        setArrivalDate(endDate);
//        }
//        DictionaryReference<GeoLocation> arrival =
//        segment.getArriveLocation();
//        if (arrival != null) {
//        arrivalCityCode = arrival.getCode();
//        if (firstArrivalCityCode == null) {
//        firstArrivalCityCode = arrivalCityCode;
//        }
//        GeoLocation arrivalCity =
//        DictHelper.findCityByAirport(arrivalCityCode);
//        if (arrivalCity != null) {
//        arrivalCityCode = arrivalCity.getCode();
//        segment.setArriveCityCode(arrivalCityCode);
//        }
//        }
//        cities.add(arrivalCityCode);
//        if (interline) {
//        numOfInterlineSegments++;
//        }
//        Set<String> dcs = new HashSet<String>();
//        if (departure != null) {
//        dcs.add(departure.getCode());
//        dcs.addAll(ProxyHelper.collectGeoCodes(dcs, true));
//        }
//        Set<String> acs = new HashSet<String>();
//        prevCode = null;
//        if (arrival != null) {
//        acs.add(arrival.getCode());
//        acs.addAll(ProxyHelper.collectGeoCodes(acs, true));
//        prevCode = arrival.getCode();
//        }
//        LinkedList<String[]> lst = new LinkedList<String[]>();
//        lst.add(dcs.toArray(new String[dcs.size()]));
//        lst.add(acs.toArray(new String[acs.size()]));
//        getFlightSegments().add(lst);
//        getDomesticFlightsAC().add(
//        isDomesticFlightAC(airlineReference, departure, arrival));
//        setCodeShare(isCodeShare()
//        || ((segment.getOperatingAirline() != null) && !segment
//        .getOperatingAirline().equals(segment.getAirline())));
//        firstSegment = false;
//        }
//        arrivalCodes.add(prevCode);
//        }
//        setOneWayTicket(!MiscUtil.equals(
//        DictHelper.findCityByAirport(firstDepartureCityCode),
//        DictHelper.findCityByAirport(arrivalCityCode)));
//        for (String city : cities) {
//        Set<String> geoCodes = new HashSet<String>();
//        if (!TextUtil.isBlank(city)) {
//        geoCodes.add(city);
//        geoCodes.addAll(ProxyHelper.collectGeoCodes(geoCodes, true));
//        }
//        getVisitedCities()
//        .add(geoCodes.toArray(new String[geoCodes.size()]));
//        }
//        getAllLocations().clear();
//        for (String[] items : getVisitedCities()) {
//        for (String item : items) {
//        getAllLocations().add(item.trim());
//        }
//        }
//        {
//        getArrivalLocations().clear();
//        Set<String> geoCodes = new HashSet<String>();
//        for (String loc : arrivalCodes) {
//        geoCodes.add(loc);
//        getArrivalLocations().add(loc);
//        }
//        getArrivalLocations()
//        .addAll(ProxyHelper.collectGeoCodes(geoCodes, true));
//        }
//        if ((legsCount == 2) && MiscUtil.equals(
//        DictHelper.findCityByAirport(firstDepartureCityCode),
//        DictHelper.findCityByAirport(arrivalCityCode))) {
//        getDepartureLocations().clear();
//        getDepartureLocations().addAll(ProxyHelper.collectGeoCodes(
//        Collections.singleton(firstDepartureCityCode), true));
//        getArrivalLocations().clear();
//        getArrivalLocations().addAll(ProxyHelper.collectGeoCodes(
//        Collections.singleton(firstArrivalCityCode), true));
//        }
//        setNumOfInterlineSegments(numOfInterlineSegments);
//        }
//
//private Boolean isDomesticFlightAC(
//final DictionaryReference<Airline> airlineReference,
//final DictionaryReference<GeoLocation> departureRef,
//final DictionaryReference<GeoLocation> arrivalRef) {
//        DictionaryCache dc = Environment.getPublished(DictionaryCache.class);
//        Airline airline = dc.resolveReference(airlineReference);
//        GeoLocation departure = dc.resolveReference(departureRef);
//        GeoLocation arrival = dc.resolveReference(arrivalRef);
//        if (airline == null) {
//        return null;
//        }
//        DictionaryReference<Country> countryRef = airline.getCountry();
//        if (countryRef == null) {
//        return null;
//        }
//        if (((departure == null) || (departure.getCountry() == null))
//        && ((arrival == null) || (arrival.getCountry() == null))) {
//        return null;
//        }
//        if ((departure != null) && (departure.getCountry() != null)
//        && !departure.getCountry().equals(countryRef)) {
//        return Boolean.FALSE;
//        }
//        if ((arrival != null) && (arrival.getCountry() != null)
//        && !arrival.getCountry().equals(countryRef)) {
//        return Boolean.FALSE;
//        }
//        return Boolean.TRUE;
//        }
//
//private Map<String, Object> initializeProductFields(final Product prod) {
//        ProductCategory prCat = prod.getProductCategory();
//        MCOCategory mcoCat = prod.getMcoCategory();
//        setProductCategory(getEnumName(prCat));
//        setMcoCategory(getEnumName(mcoCat));
//        setGroupFare(AirProductHelper.isGroupTicket(prod));
//        setBaseFare(AirProductHelper.getBaseFare(prod));
//        setTariffType(getEnumName(prod.getTariffType()));
//        setPassengerType(getEnumName(prod.getPassengerType()));
//        setTourCode(getString(prod.getTourCode()));
//        setGdsName(getEnumName(prod.getReservation() != null
//        ? prod.getReservation().getGdsName() : null));
//        setReservationType(getEnumName(prod.getReservation() != null
//        ? prod.getReservation().getType() : null));
//        setPcc(prod.getPcc());
//        if (prod.getStatus() == ProductStatus.REFUND) {
//        setForcedRefund(prod.isForcedRefund());
//        } else if ((prod.getStatus() == ProductStatus.SELL)
//        && (prod.getPreviousProduct() != null)
//        && (((Product) prod.getPreviousProduct())
//        .getStatus() == ProductStatus.EXCHANGE)) {
//        setForcedRefund(
//        ((Product) prod.getPreviousProduct()).isForcedRefund());
//        }
//        if (prCat != null) {
//        switch (prCat) {
//        case AIR:
//        setTargetType(ProductCategory.AIR.name());
//        break;
//        case MCO:
//        if (mcoCat == null) {
//        setTargetType(""); //$NON-NLS-1$
//        break;
//        }
//        setTargetType(mcoCat.name());
//        break;
//        case KRS:
//        setTargetType(ProductCategory.KRS.name());
//        break;
//        case EXCESS_BAGAGE:
//        setTargetType(ProductCategory.EXCESS_BAGAGE.name());
//        break;
//        }
//        } else {
//        setTargetType(""); //$NON-NLS-1$
//        }
//        //TODO: handle agent location
//        //agentLocation = prod.getReservation().getInvoicingAgentLocation();
//        String systemNumber = prod.getSystemNumber();
//        Airline blankOwner = addAirlines(getBlankOwners(), prod.getBlankOwner(),
//        prod.getBlankOwnerNumber(), systemNumber, "blank owner"); //$NON-NLS-1$
//        if ((blankOwner != null) && "Ш1".equals(blankOwner.getCode())) { //$NON-NLS-1$
//        setAgencyTchCode(prod.getAgencyCode());
//        }
//        setCarrierNumber(getString(prod.getCarrierNumber()));
//        DictionaryReference<Airline> carrierReference = prod.getCarrier();
//        Airline primaryCarrier = addAirlines(getCarriers(), carrierReference,
//        getCarrierNumber(), systemNumber, "responsible carrier"); //$NON-NLS-1$
//        if ((primaryCarrier != null) && (primaryCarrier.getCountry() != null)) {
//        DictionaryReference<Country> countryRef =
//        primaryCarrier.getCountry();
//        getPrimaryCarrierCountry().add(countryRef.getCode());
//        Country country = Environment.getPublished(DictionaryCache.class)
//        .resolveReference(countryRef);
//        if (country == null) {
//        LogFactory.getLog(getClass())
//        .warn(String.format(
//        "unable to resolve country reference with code %s", //$NON-NLS-1$
//        countryRef.getCode()));
//        } else {
//        getPrimaryCarrierCountry()
//        .addAll(country.getCodeVariants().values());
//        }
//        }
//        switch (AirProductHelper.calculateTransportationType(prod)) {
//        case DOMESTIC:
//        case NONE:
//        setTransportationType(Constants.TRANSPORTATION_DOMESTIC.name());
//        break;
//default:
//        setTransportationType(
//        Constants.TRANSPORTATION_INTRENATIONAL.name());
//        }
//        setPnr(getString(prod.getReservation().getRecordLocator()));
//        setValidator(getString(prod.getValidatorCode()));
//        setEticket(isEticket() | prod.isEticket());
//        updatePaymentTypes(prod.getVendorFops());
//        setIssueDate(prod.getIssueDate());
//        setCurrentDate(new Date());
//        ProductStatus status = prod.getStatus();
//        if ((status == ProductStatus.REFUND)
//        || (status == ProductStatus.EXCHANGE)) {
//        Product pr = (Product) prod.getPreviousProduct();
//        if (pr != null) {
//        setIssueDate(pr.getIssueDate());
//        }
//        }
//        setFare(getDoubleValue(AirProductHelper.getEquivalentFare(prod)));
//        BigDecimal nuc = AirProductHelper.getNucFare(prod);
//        BigDecimal roe = AirProductHelper.getNucRoe(prod);
//        if ((getFare() == 0) && (nuc != null) && (roe != null)) {
//        setFare(getBaseFare() != null ? (nuc.multiply(roe)
//        .multiply(AirProductHelper.calculateFareRate(prod)))
//        .doubleValue()
//        : 0);
//        }
//        for (Tax tax : prod.getTaxes()) {
//        if (!TextUtil.isBlank(tax.getCode())) {
//        getTaxCodes().add(tax.getCode().trim().toUpperCase());
//        }
//        }
//        for (ProductFop fop : prod.getVendorFops()) {
//        if ((fop.getType() == PaymentType.CREDIT)
//        || (fop.getType() == PaymentType.INTERLINE)
//        || (fop.getType() == PaymentType.MTD)) {
//        setPassengerStatus(getEnumName(fop.getPassengerStatus()));
//        break;
//        }
//        }
//        Map<String, Object> result = new HashMap<String, Object>(1);
//        result.put("carrier", carrierReference); //$NON-NLS-1$
//        return result;
//        }
//
//private void updatePaymentTypes(final Collection<ProductFop> fops) {
//        // TODO handle ticket payment type
//        for (ProductFop fop : fops) {
//        PaymentType paymentType = fop.getType();
//        if (paymentType != null) {
//        getPaymentTypes().add(paymentType.name());
//        }
//        }
//        }
//@Override
//public String toString(){
//        return super.toString()+","+product.getUid();
//        }
//        }
//
