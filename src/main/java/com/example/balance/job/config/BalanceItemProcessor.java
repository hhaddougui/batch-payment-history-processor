package com.example.balance.job.config;

import java.util.Locale;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import com.example.balance.dao.InputDetailRecord;
import com.example.balance.dao.OutputDetailRecord;
import com.example.financial.utils.CurrencyFormatterService;

/**
 * ItemProcessor implementation for transforming InputDetailRecord to OutputDetailRecord.
 * Instantiates OutputDetailRecord inside process() for thread safety.
 */
@Component
public class BalanceItemProcessor implements ItemProcessor<InputDetailRecord, OutputDetailRecord> {

    private final CurrencyFormatterService currencyService;

    public BalanceItemProcessor(CurrencyFormatterService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public OutputDetailRecord process(InputDetailRecord item) throws Exception {
        OutputDetailRecord output = new OutputDetailRecord(); // Instantiate per record
        output.setCard_acquirer(item.getAcquirer());
        output.setCustomer_name(item.getClient());
        output.setTransaction_amount(item.getTransaction_amount());
        output.setCard_type(formatCardType(item.getType()));
        output.setBalance_amount(formatAmount(item.getBalance()));
        output.setFictious_balance(formatAmount(item.getFictious_balance()));
        output.setReceiver_name(item.getReceiver());
        return output;
    }

    String formatCardType(String type) {
        return type != null ? type.toUpperCase() : null;
    }

    String formatAmount(float amount) {
        Locale locale = Locale.forLanguageTag("en-US");
        return currencyService.formatCurrency(amount, "USD", locale);
    }
}