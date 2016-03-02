package org.mfusco.fromgoftolambda.examples.decorator;

import java.util.function.Function;
import java.util.stream.Stream;

public class DecoratorLambda {

    public static class DefaultSalaryCalculator implements Function<Double, Double> {
        @Override
        public Double apply( Double grossAnnual ) {
            return grossAnnual / 12;
        }
    }

    public static void main( String[] args ) {
        new DefaultSalaryCalculator()
                .andThen( Taxes::generalTax )
                .andThen( Taxes::regionalTax )
                .andThen( Taxes::healthInsurance )
                .apply( 80000.00 );

        calculateSalary( 80000.00, new DefaultSalaryCalculator(), Taxes::generalTax, Taxes::regionalTax, Taxes::healthInsurance );
    }

    public static double calculateSalary(double annualGross, Function<Double, Double>... taxes) {
        return Stream.of(taxes).reduce( Function.identity(), Function::andThen ).apply( annualGross );
    }
}
