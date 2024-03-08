package ch03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    public LocalDate calculateExpiryDate(PayData payData) {
        int addedMonths = payData.getPayAmount() >= 100_000
                ? payData.getPayAmount() / 10_000 + 2 * (payData.getPayAmount() / 100_000)
                : payData.getPayAmount() / 10_000;

        if (payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, addedMonths);
        } else {


            return payData.getBillingDate().plusMonths(addedMonths);
        }
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonths) {
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);

        if (isSameDayOfMonth(payData.getFirstBillingDate(), candidateExp)) {
            final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
            final int dayLengthOfcandiMon = lastDayOfMonth(candidateExp);

            if (dayLengthOfcandiMon < dayOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLengthOfcandiMon);
            }

            return candidateExp.withDayOfMonth(dayOfFirstBilling);
        } else {
            return candidateExp;
        }
    }

    private boolean isSameDayOfMonth(LocalDate firstBillingDate, LocalDate billingDate) {
        return firstBillingDate.getDayOfMonth() != billingDate.getDayOfMonth();
    }

    private int lastDayOfMonth(LocalDate date) {
        return YearMonth.from(date).lengthOfMonth();
    }
}
