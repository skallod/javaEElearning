//package ru.galuzin.drools_test.bof;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Currency;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.gridnine.bof.common.Environment;
//import com.gridnine.bof.common.model.EntityCache;
//import com.gridnine.bof.common.model.EntityContainer;
//import com.gridnine.bof.common.model.EntityReference;
//import com.gridnine.bof.common.model.booking.air.Commission;
//import com.gridnine.bof.common.model.booking.commission.BaseCommissionProperties;
//import com.gridnine.bof.common.model.booking.commission.CalculationBasis;
//import com.gridnine.bof.common.model.booking.commission.CalculationType;
//import com.gridnine.bof.common.model.booking.commission.CommissionProperties;
//import com.gridnine.bof.common.model.booking.commission.DiscountProperties;
//import com.gridnine.bof.common.model.booking.commission.FeeProperties;
//import com.gridnine.bof.common.model.booking.commission.RouteType;
//import com.gridnine.bof.common.model.dict.CommissionCategory;
//import com.gridnine.bof.common.model.dict.ContractType;
//import com.gridnine.bof.common.model.dict.DictionaryReference;
//import com.gridnine.bof.common.model.dict.MCOCategory;
//import com.gridnine.bof.common.model.dict.PreferenceKey;
//import com.gridnine.bof.common.model.dict.ProductCategory;
//import com.gridnine.bof.common.model.helpers.DictHelper;
//import com.gridnine.bof.common.model.helpers.SystemHelper;
//import com.gridnine.bof.common.model.profile.Contract;
//import com.gridnine.bof.common.model.rules.standard.ProxyHelper;
//import com.gridnine.bof.common.model.rules.standard.RuleResultValue;
//import com.gridnine.bof.common.model.system.Message;
//import com.gridnine.bof.common.model.system.MessageType;
//import com.gridnine.bof.common.model.system.Money;
//import com.gridnine.bof.common.model.system.RoundingMode;
//import com.gridnine.bof.common.model.system.RoundingValue;
//import com.gridnine.bof.common.util.CollectionUtil;
//import com.gridnine.bof.common.util.TextUtil;
//import com.gridnine.bof.common.util.TimeSpan;
//import com.gridnine.bof.common.xml.XHelper;
//import com.gridnine.bof.server.rules.RuleLogTracer;
//import com.gridnine.bof.server.rules.RuleProxy;
//
//public abstract class BaseCommissionActionSet implements Serializable {
//    private static final long serialVersionUID = 8541024801015259243L;
//
//    private final RuleProxy<?, ?> proxy;
//
//    private final BaseAirPropertySet propertySet;
//
//    private String commissionCurrencyCode;
//
//    private Double commissionRate;
//
//    private BigDecimal commissionValue;
//
//    private final List<ProfitItem> profitValues = new ArrayList<ProfitItem>();
//
//    private final List<ProfitItem> profitFareRates =
//            new ArrayList<ProfitItem>();
//
//    private final List<ProfitItem> profitFareAndTaxesRates =
//            new ArrayList<ProfitItem>();
//
//    private final Map<String, RuleResultValue> results =
//            new LinkedHashMap<String, RuleResultValue>();
//
//    public BaseCommissionActionSet(final RuleProxy<?, ?> px,
//                                   final BaseAirPropertySet propSet) {
//        proxy = px;
//        propertySet = propSet;
//    }
//
//    protected RuleProxy<?, ?> getProxy() {
//        return proxy;
//    }
//
//    protected BaseAirPropertySet getPropertySet() {
//        return propertySet;
//    }
//
//    public BigDecimal getCommissionValue() {
//        return commissionValue;
//    }
//
//    public boolean setCommission(final double value, final String currency,
//                                 final String target, final String[] fops) {
//        if (!isApplicable(target, fops)) {
//            return false;
//        }
//        if (TextUtil.isBlank(currency)
//                || "%".equals(currency) || "null".equals(currency)) { //$NON-NLS-1$
//            commissionRate = Double.valueOf(value);
//            return true;
//        }
//        commissionValue = BigDecimal.valueOf(value);
//        commissionCurrencyCode = currency;
//        commissionValue = BigDecimal.valueOf(value);
//        return true;
//    }
//
//    private boolean isApplicable(final String target, final String[] fops) {
//        outer: for (String fop : fops) {
//            for (String pt : propertySet.getPaymentTypes()) {
//                if (pt.equals(fop)) {
//                    continue outer;
//                }
//            }
//            return false;
//        }
//        if (!TextUtil.isSame(propertySet.getTargetType(), target)) {
//            return false;
//        }
//        return true;
//    }
//
//    public void setCommissionCurrencyCode(final String value) {
//        commissionCurrencyCode = value;
//    }
//
//    public Double getCommissionRate() {
//        return commissionRate;
//    }
//
//    public void setCommissionRate(final double value) {
//        commissionRate = Double.valueOf(value);
//    }
//
//    public void setCommissionValue(final double value) {
//        commissionValue = BigDecimal.valueOf(value);
//    }
//
//    public void addProfitValue(final double value, final String timeSpan) {
//        profitValues.add(new ProfitItem(value, TimeSpan.decode(timeSpan)));
//    }
//
//    public void addProfitFareRate(final double value, final String timeSpan) {
//        profitFareRates.add(new ProfitItem(value, TimeSpan.decode(timeSpan)));
//    }
//
//    public void addProfitFareAndTaxesRate(final double value,
//                                          final String timeSpan) {
//        profitFareAndTaxesRates.add(new ProfitItem(value, TimeSpan
//                .decode(timeSpan)));
//    }
//
//    public BigDecimal getProfit() {
//        Date now = new Date();
//        ProfitItem item = ProfitItem.find(profitValues, now);
//        if (item != null) {
//            return BigDecimal.valueOf(item.value);
//        }
//        BigDecimal base;
//        item = ProfitItem.find(profitFareRates, now);
//        if (item != null) {
//            base = getEquivalentFare(false);
//        } else {
//            item = ProfitItem.find(profitFareAndTaxesRates, now);
//            if (item != null) {
//                base = getEquivalentFare(true);
//            } else {
//                return null;
//            }
//        }
//        if (Math.abs(base.doubleValue()) <= 0.001) {
//            return null;
//        }
//        return BigDecimal.valueOf(item.value).multiply(base)
//                .divide(BigDecimal.valueOf(100), 3, java.math.RoundingMode.HALF_UP);
//    }
//
//    public String getCommissionCurrencyCode() {
//        return commissionCurrencyCode;
//    }
//
//    public void calculateCommissionByRate(final boolean totalRate,
//                                          final double value) {
//        commissionValue = calculateFeeByRateInternal(totalRate, value);
//        commissionCurrencyCode = getEquivalentCurrencyCode();
//        commissionRate = Double.valueOf(value);
//    }
//
//    private BigDecimal calculateFeeByRateInternal(final boolean totalRate,
//                                                  final double value) {
//        return totalRate ? propertySet.getTotalPrice()
//                .multiply(BigDecimal.valueOf(value))
//                .divide(BigDecimal.valueOf(100)) : BigDecimal
//                .valueOf(propertySet.getFare()).multiply(BigDecimal.valueOf(value))
//                .divide(BigDecimal.valueOf(100));
//    }
//
//    public void calculateCommissionByValue(final boolean perSegment,
//                                           final double oneWayValue, final double roundTripValue)
//            throws Exception {
//        commissionValue =
//                calculateFeeByValueInternal(perSegment, oneWayValue, roundTripValue);
//        commissionCurrencyCode = getEquivalentCurrencyCode();
//        commissionRate = null;
//    }
//
//    private BigDecimal calculateFeeByValueInternal(final boolean perSegment,
//                                                   final double oneWayValue, final double roundTripValue) {
//        if (perSegment) {
//            return (BigDecimal.valueOf(propertySet.getNumOfSegments())
//                    .multiply(BigDecimal.valueOf(Math.max(oneWayValue,
//                            roundTripValue))));
//        } else if (propertySet.isOneWayTicket()) {
//            return BigDecimal.valueOf(oneWayValue);
//        }
//        return BigDecimal.valueOf(roundTripValue);
//    }
//
//    public void setCommission(final String category, final String className,
//                              final String uid, final double value, final String currencyCode) {
//        RuleResultValue res = new RuleResultValue();
//        res.setCurrencyCode(currencyCode);
//        res.setValue(value);
//        res.setTarget(createReference(className, uid));
//        results.put(category, res);
//    }
//
//    public boolean setCommission(final String category, final String className,
//                                 final String uid, final double value, final String currencyCode,
//                                 final String target, final String[] fops) {
//        if (!isApplicable(target, fops)) {
//            return false;
//        }
//        RuleResultValue res = new RuleResultValue();
//        res.setCurrencyCode(currencyCode);
//        res.setValue(value);
//        res.setTarget(createReference(className, uid));
//        results.put(category, res);
//        return true;
//    }
//
//    public Map<String, RuleResultValue> getResults() {
//        return results;
//    }
//
//    public boolean accept(final String category, final String className,
//                          final String uid) throws Exception {
//        RuleLogTracer tracer = getProxy().getTracer();
//        tracer.traceEnteringMethod(BaseCommissionActionSet.class,
//                String.format("accept(%s, %s, %s)", category, className, uid)); //$NON-NLS-1$
//        try {
//            if (results.containsKey(category)) {
//                tracer.traceLogic("results already contain the " + //$NON-NLS-1$
//                        "category, returning false"); //$NON-NLS-1$
//                return false;
//            }
//            EntityContainer<? extends BaseCommissionProperties> ctr =
//                    Environment.getPublished(EntityCache.class).resolve(
//                            createReference(className, uid));
//            if (ctr == null) {
//                tracer.traceLogic("unable to load commission properties," + //$NON-NLS-1$
//                        "returning false"); //$NON-NLS-1$
//                return false;
//            }
//            BaseCommissionProperties prop = ctr.getEntity();
//            if (prop instanceof CommissionProperties) {
//                tracer
//                        .traceLogic("properties is instance of CommissionProperties," + //$NON-NLS-1$
//                                "returning true"); //$NON-NLS-1$
//                return true;
//            }
//            if (prop instanceof DiscountProperties) {
//                tracer
//                        .traceLogic("properties is instance of DiscountProperties," + //$NON-NLS-1$
//                                "returning true"); //$NON-NLS-1$
//                return true;
//            }
//            FeeProperties feeProp = (FeeProperties) prop;
//            if (!accept(feeProp)) {
//                return false;
//            }
//            if (!feeProp.getRouteTypes().isEmpty()) {
//                if (propertySet.isOneWayTicket()
//                        && !feeProp.getRouteTypes().contains(RouteType.ONE_WAY)) {
//                    tracer
//                            .traceLogic("ticket is a one way ticket, but route types does not" + //$NON-NLS-1$
//                                    " contain ONE_WAY, returning false"); //$NON-NLS-1$
//                    return false;
//                }
//                if (!propertySet.isOneWayTicket()
//                        && !feeProp.getRouteTypes().contains(RouteType.ROUND_TRIP)) {
//                    tracer
//                            .traceLogic("ticket is a round trip ticket, but route types does not" + //$NON-NLS-1$
//                                    " contain ROUND_TRIP, returning false"); //$NON-NLS-1$
//                    return false;
//                }
//            }
//            tracer.traceLogic("returning true"); //$NON-NLS-1$
//            return true;
//        } finally {
//            tracer
//                    .traceExitingMethod(BaseCommissionActionSet.class, "accept()"); //$NON-NLS-1$
//        }
//    }
//
//    public Collection<Commission> createCommissions(final Contract contract,
//                                                    final Collection<Message> messages) throws Exception {
//        Map<String, Commission> commissions =
//                new LinkedHashMap<String, Commission>();
//        if ((commissionValue != null) || (commissionRate != null)) {
//            Commission commission = new Commission();
//            commission.setContractType(contract.getContractType());
//            if ((commissionValue != null) && (commissionCurrencyCode != null)) {
//                Money money = new Money();
//                money.setCurrency(Currency.getInstance(commissionCurrencyCode));
//                money.setValue(commissionValue);
//                commission.setAmount(money);
//            }
//            if (commissionRate != null) {
//                commission.setRate(commissionRate);
//            }
//            commission.setRoundingMode(CollectionUtil.findEnumConstant(
//                    RoundingMode.class, proxy.getRoundingMode()));
//            commission.setRoundingValue(CollectionUtil.findEnumConstant(
//                    RoundingValue.class, proxy.getRoundingValue()));
//            updateEquivalentAmount(commission, false, messages);
//            commissions.put("", commission); //$NON-NLS-1$
//        }
//        for (Entry<String, RuleResultValue> entry : results.entrySet()) {
//            RuleResultValue value = entry.getValue();
//            EntityContainer<? extends BaseCommissionProperties> ctr =
//                    Environment.getPublished(EntityCache.class).resolve(
//                            value.getTarget());
//            if (ctr == null) {
//                messages
//                        .add(SystemHelper.createMessage(MessageType.WARNING,
//                                "не удается найти свойства комиссии {0}",
//                                value.getTarget()));
//                continue;
//            }
//            if (!ctr.getEntity().isMinimum()) {
//                Commission commission =
//                        calculateCommissionAmount(value,
//                                contract.getContractType(), messages);
//                if (commission == null) {
//                    continue;
//                }
//                commissions.put(getCategory(value.getTarget()), commission);
//            }
//        }
//        for (Entry<String, RuleResultValue> entry : results.entrySet()) {
//            RuleResultValue value = entry.getValue();
//            EntityContainer<? extends BaseCommissionProperties> ctr =
//                    Environment.getPublished(EntityCache.class).resolve(
//                            value.getTarget());
//            if (ctr == null) {
//                continue;
//            }
//            if (ctr.getEntity().isMinimum()) {
//                Commission minCom =
//                        calculateCommissionAmount(value,
//                                contract.getContractType(), messages);
//                if (minCom == null) {
//                    continue;
//                }
//                String category = getCategory(value.getTarget());
//                Commission com = commissions.get(category);
//                if (com == null) {
//                    commissions.put(category, minCom);
//                    continue;
//                }
//                if ((Math.abs(minCom.getEquivalentAmount().doubleValue()) - Math
//                        .abs(com.getEquivalentAmount().doubleValue())) > 0.001) {
//                    commissions.put(category, minCom);
//                }
//            }
//        }
//        return commissions.values();
//    }
//
//    private String getCategory(
//            final EntityReference<? extends BaseCommissionProperties> target)
//            throws Exception {
//        if (target == null) {
//            return ""; //$NON-NLS-1$
//        }
//        EntityContainer<? extends BaseCommissionProperties> ctr =
//                Environment.getPublished(EntityCache.class).resolve(target);
//        if (ctr == null) {
//            return ""; //$NON-NLS-1$
//        }
//        DictionaryReference<CommissionCategory> category =
//                ctr.getEntity().getCategory();
//        return category != null ? ctr.getEntityType().getName() + "_" //$NON-NLS-1$
//                + category : ctr.getEntityType().getName();
//    }
//
//    protected Commission calculateCommissionAmount(final RuleResultValue value,
//                                                   final ContractType contractType, final Collection<Message> messages)
//            throws Exception {
//        Commission result = new Commission();
//        result.setContractType(contractType);
//        EntityContainer<? extends BaseCommissionProperties> ctr =
//                Environment.getPublished(EntityCache.class).resolve(
//                        value.getTarget());
//        BaseCommissionProperties prop = ctr.getEntity();
//        result.setCommissionProperties(ctr.toReference());
//        String currencyCode = value.getCurrencyCode();
//        boolean includeTaxes = false;
//        if ((prop instanceof CommissionProperties)
//                || (prop instanceof DiscountProperties)) {
//            if ("%".equals(currencyCode) || TextUtil.isBlank(currencyCode) || "null".equals(currencyCode)) { //$NON-NLS-1$ //$NON-NLS-2$
//                result.setRate(Double.valueOf(value.getValue()));
//                if (prop instanceof DiscountProperties) {
//                    includeTaxes =
//                            ((DiscountProperties) prop).getCalculationBasis() == CalculationBasis.TOTAL;
//                }
//            } else {
//                Currency currency = getCurrency(currencyCode, messages);
//                Money money = new Money();
//                money.setCurrency(currency);
//                BigDecimal amount = BigDecimal.valueOf(value.getValue());
//                String productCategory = getPropertySet().getProductCategory();
//                if ((ProductCategory.AIR.name().equals(productCategory) || (ProductCategory.MCO
//                        .name().equals(productCategory) && MCOCategory.PTA.name()
//                        .equals(getPropertySet().getMcoCategory())))
//                        && (prop instanceof CommissionProperties)
//                        && ((CommissionProperties) prop).isSegmentCalculation()) {
//                    amount =
//                            amount.multiply(BigDecimal.valueOf(getPropertySet()
//                                    .getNumOfSegments()));
//                }
//                money.setValue(amount);
//                result.setAmount(money);
//            }
//        } else if (prop instanceof FeeProperties) {
//            FeeProperties feeProp = (FeeProperties) prop;
//            if (feeProp.getCalculationType() == CalculationType.SEGMENT) {
//                Currency currency = getCurrency(currencyCode, messages);
//                Money money = new Money();
//                money.setCurrency(currency);
//                money.setValue(BigDecimal.valueOf(value.getValue()).multiply(
//                        BigDecimal.valueOf(propertySet.getNumOfSegments())));
//                result.setAmount(money);
//            } else {
//                if ("%".equals(currencyCode) || TextUtil.isBlank(currencyCode) || "null".equals(currencyCode)) { //$NON-NLS-1$ //$NON-NLS-2$
//                    result.setRate(Double.valueOf(value.getValue()));
//                    includeTaxes =
//                            feeProp.getCalculationBasis() == CalculationBasis.TOTAL;
//                } else {
//                    Currency currency = getCurrency(currencyCode, messages);
//                    Money money = new Money();
//                    money.setCurrency(currency);
//                    money.setValue(BigDecimal.valueOf(value.getValue()));
//                    result.setAmount(money);
//                }
//            }
//            fixCommissionRate(result, feeProp);
//        } else {
//            messages.add(SystemHelper.createMessage(MessageType.WARNING,
//                    "неизвестный тип комиссии {0}", value.getTarget()));
//            return null;
//        }
//        if (prop.getRoundingMode() != null) {
//            result.setRoundingMode(RoundingMode.valueOf(prop.getRoundingMode()
//                    .name()));
//        }
//        if (prop.getRoundingValue() != null) {
//            result.setRoundingValue(RoundingValue.valueOf(prop
//                    .getRoundingValue().name()));
//        }
//        updateEquivalentAmount(result, includeTaxes, messages);
//        return result;
//    }
//
//    protected abstract void fixCommissionRate(Commission commission,
//                                              FeeProperties feeProp);
//
//    protected void updateEquivalentAmount(final Commission commission,
//                                          final boolean includeTaxes, final Collection<Message> messages) {
//        String eqvCode = getEquivalentCurrencyCode();
//        BigDecimal result = BigDecimal.ZERO;
//        if (commission.getRate() != null) {
//            BigDecimal fare = getEquivalentFare(includeTaxes);
//            if (Math.abs(fare.doubleValue()) > 0.0001) {
//                result =
//                        result.add(BigDecimal
//                                .valueOf(commission.getRate().doubleValue())
//                                .multiply(fare)
//                                .divide(BigDecimal.valueOf(100), 3,
//                                        java.math.RoundingMode.HALF_UP));
//            } else {
//                messages
//                        .add(SystemHelper
//                                .createMessage(MessageType.WARNING,
//                                        "не удается рассчитать комиссию из-за отсутствия эквивалентного тарифа"));
//            }
//        }
//        Money money = commission.getAmount();
//        if ((money != null) && (money.getCurrency() != null)
//                && (money.getValue() != null)) {
//            BigDecimal value = money.getValue();
//            String currencyCode = money.getCurrency().getCurrencyCode();
//            if (!currencyCode.equals(eqvCode)) {
//                value = value.multiply(getCurrencyRate(currencyCode, messages));
//            }
//            result = result.add(value);
//        }
//        commission.setEquivalentAmount(ProxyHelper.roundDouble(result,
//                commission.getRoundingMode(), commission.getRoundingValue()));
//    }
//
//    private Currency getCurrency(final String currencyCode,
//                                 final Collection<Message> messages) {
//        if (TextUtil.isBlank(currencyCode)) {
//            String curCode =
//                    DictHelper.getPreferenceValue(PreferenceKey.EQUIVE_CURRENCY,
//                            "RUB"); //$NON-NLS-1$
//            messages
//                    .add(SystemHelper
//                            .createMessage(
//                                    MessageType.WARNING,
//                                    "не задана валюта комиссии, будем использовать валюту по умолчанию {0}",
//                                    curCode));
//            return Currency.getInstance(curCode);
//        }
//        return Currency.getInstance(currencyCode);
//    }
//
//    protected abstract boolean accept(FeeProperties feeProp);
//
//    protected abstract BigDecimal getEquivalentFare(boolean includeTaxes);
//
//    protected abstract BigDecimal getCurrencyRate(String currencyCode,
//                                                  Collection<Message> messages);
//
//    private String getEquivalentCurrencyCode() {
//        return DictHelper.getPreferenceValue(PreferenceKey.EQUIVE_CURRENCY,
//                "RUB"); //$NON-NLS-1$
//    }
//
//    @SuppressWarnings("unchecked")
//    private EntityReference<? extends BaseCommissionProperties> createReference(
//            final String className, final String uid) {
//        try {
//            return new EntityReference<BaseCommissionProperties>(uid,
//                    (Class<BaseCommissionProperties>) XHelper.getClass(className),
//                    null);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private static class ProfitItem {
//        static ProfitItem find(final List<ProfitItem> items, final Date date) {
//            for (ProfitItem item : items) {
//                if ((item.timeSpan == null) || item.timeSpan.contains(date)) {
//                    return item;
//                }
//            }
//            return null;
//        }
//
//        final double value;
//
//        final TimeSpan timeSpan;
//
//        ProfitItem(final double v, final TimeSpan ts) {
//            value = v;
//            timeSpan = ts;
//        }
//    }
//}
