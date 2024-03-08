package ch03;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator calculator = new ExpiryDateCalculator();
        LocalDate expiryDate = calculator.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate, expiryDate);
    }

    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 4, 8))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2024, 5, 8));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 5, 8))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2024, 6, 8));
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 1, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2024, 2, 29));

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 3, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2024, 4, 30));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_만원_납부_하면() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2024, 1, 31))
                .billingDate(LocalDate.of(2024, 2, 29))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData, LocalDate.of(2024, 3, 31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2024, 4, 25))
                .billingDate(LocalDate.of(2024, 5, 23))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData2, LocalDate.of(2024, 6, 25));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2024, 6, 30))
                .billingDate(LocalDate.of(2024, 7, 18))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData3, LocalDate.of(2024, 8, 30));

    }

    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산() {
        PayData payData = PayData.builder()
                .billingDate(LocalDate.of(2024, 4, 20))
                .payAmount(20_000)
                .build();
        assertExpiryDate(payData, LocalDate.of(2024,6, 20));

        PayData payData2 = PayData.builder()
                .billingDate(LocalDate.of(2024, 4, 20))
                .payAmount(60_000)
                .build();
        assertExpiryDate(payData2, LocalDate.of(2024,10, 20));

        PayData payData3 = PayData.builder()
                .billingDate(LocalDate.of(2024, 4, 20))
                .payAmount(90_000)
                .build();
        assertExpiryDate(payData3, LocalDate.of(2025,1, 20));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2024, 1, 31))
                .billingDate(LocalDate.of(2024, 2, 28))
                .payAmount(20_000)
                .build();
        assertExpiryDate(payData, LocalDate.of(2024,4, 30));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2024, 3, 31))
                .billingDate(LocalDate.of(2024, 4, 30))
                .payAmount(30_000)
                .build();
        assertExpiryDate(payData2, LocalDate.of(2024,7, 31));
    }

    @Test
    void 십만원을_납부하면_1년_제공() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 1, 28))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2025, 1, 28)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 2, 29))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2025, 2, 28)
        );
    }

    @Test
    void 십만원_초과로_납부한_경우() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 1, 31))
                        .payAmount(130_000)
                        .build(),
                LocalDate.of(2025, 4, 30)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 1, 31))
                        .payAmount(190_000)
                        .build(),
                LocalDate.of(2025, 10, 31)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2024, 1, 31))
                        .payAmount(230_000)
                        .build(),
                LocalDate.of(2026, 4, 30)
        );
    }

}
