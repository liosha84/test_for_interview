package my.container.config;

import my.component.services.PaymentSystem;
import my.component.services.Recommendator;
import my.component.services.impl.CashPaymentSystem;
import my.component.services.impl.SmartRecommendator;

import java.util.Map;

public class JavaConfiguration implements Configuration{
    @Override
    public String getPackageToScan() {
        return "my";
    }

    @Override
    public Map<Class, Class> getInterfaceToImplementations() {
        return Map.of(PaymentSystem.class, CashPaymentSystem.class, Recommendator.class, SmartRecommendator.class);
    }
}
