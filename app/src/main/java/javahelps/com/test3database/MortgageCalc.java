package javahelps.com.test3database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;

public class MortgageCalc extends AppCompatActivity {

    private int EstimatedPrice, RentValue,DownPayment, MortgageTaxFeeValue, InsurancePaymentValue,  Investment;
    private double CashFlowValue, Interest, PeriodOfPayback, TotalPayment, Payment;
    Button Cal;
    ScrollView scmc;

    SeekBar rentSeekbar,priceSeekbar,downPaymentSeekbar,interestSeekbar,paybackPeriodSeekbar,mortgagetaxfeeSeekbar,insurancePaymentSeekbar;

    EditText estimatedPrice, Rent , downPayment, interest, periodOfPayback, MortgageTaxFee, InsurancePayment, payment, totalPayment, CashFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_calc);

        //code for back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        estimatedPrice = (EditText) findViewById(R.id.estimatedPrice);
        Rent = (EditText) findViewById(R.id.rent);
        downPayment = (EditText) findViewById(R.id.downPayment);
        interest = (EditText) findViewById(R.id.interest);
        periodOfPayback = (EditText) findViewById(R.id.periodOfPayback);
        MortgageTaxFee = (EditText) findViewById(R.id.mortgageTaxFee);
        InsurancePayment = (EditText) findViewById(R.id.insurancePayment);
        payment = (EditText) findViewById(R.id.payment);
        totalPayment = (EditText) findViewById(R.id.totalPayment);
        CashFlow = (EditText) findViewById(R.id.cashFlow);

        scmc = (ScrollView) findViewById(R.id.scrollViewMC);

        Cal = (Button) findViewById(R.id.Calculate);

        priceSeekbar = (SeekBar) findViewById(R.id.seekbarEstimatedPrice);
        priceSeekbar.setMax(1000000);
        rentSeekbar = (SeekBar) findViewById(R.id.seekbarrent);
        rentSeekbar.setMax(10000);
        downPaymentSeekbar = (SeekBar) findViewById(R.id.seekbarDownPayment);
        downPaymentSeekbar.setMax(500000);
        interestSeekbar = (SeekBar) findViewById(R.id.seekbarInterest);
        interestSeekbar.setMax(20);
        paybackPeriodSeekbar = (SeekBar)findViewById(R.id.seekbarPaybackPeriod);
        paybackPeriodSeekbar.setMax(20);
        mortgagetaxfeeSeekbar = (SeekBar) findViewById(R.id.seekbarMortgageTax);
        mortgagetaxfeeSeekbar.setMax(10000);
        insurancePaymentSeekbar = (SeekBar) findViewById(R.id.seekbarInsurancePayment);
        insurancePaymentSeekbar.setMax(5000);

        priceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                estimatedPrice.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rentSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Rent.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        downPaymentSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                downPayment.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        interestSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                interest.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        paybackPeriodSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                periodOfPayback.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mortgagetaxfeeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MortgageTaxFee.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        insurancePaymentSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                InsurancePayment.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        Cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EstimatedPrice = Integer.parseInt(estimatedPrice.getText().toString());
                priceSeekbar.setProgress(EstimatedPrice);
                RentValue = Integer.parseInt(Rent.getText().toString());
                rentSeekbar.setProgress(RentValue);
                DownPayment = Integer.parseInt(downPayment.getText().toString());
                downPaymentSeekbar.setProgress(DownPayment);
                Interest =Double.valueOf(interest.getText().toString());
                interestSeekbar.setProgress((int)Interest);
                PeriodOfPayback = Double.valueOf(periodOfPayback.getText().toString());
                paybackPeriodSeekbar.setProgress((int)PeriodOfPayback);
                MortgageTaxFeeValue = Integer.parseInt(MortgageTaxFee.getText().toString());
                mortgagetaxfeeSeekbar.setProgress((int)MortgageTaxFeeValue);
                InsurancePaymentValue = Integer.parseInt(InsurancePayment.getText().toString());
                insurancePaymentSeekbar.setProgress(InsurancePaymentValue);

                Investment = EstimatedPrice - DownPayment;
                TotalPayment = Investment * ( 1 + Interest*PeriodOfPayback/100);
                Payment = TotalPayment/PeriodOfPayback;
                CashFlowValue = ((11*RentValue) + (12*MortgageTaxFeeValue) - (0.01*EstimatedPrice) - (RentValue/2));
                CashFlow.setText(String.valueOf(CashFlowValue));
                totalPayment.setText(String.valueOf(TotalPayment));
                payment.setText(String.valueOf(Payment));

                scmc.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
