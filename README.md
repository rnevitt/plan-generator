Loan Payment Plan Generator
===========================
By Robert Nevitt
----------------

The Loan Payment Plan Generator creates a plan based on four inputs: 
* loan amount
* nominal rate (APR only at this point)
* duration in months
* first payment date

Starting The Service
----------------

To run the application from command line:
> ./mvnw spring-boot:run

To run tests:
> ./mvnw test

Once the app is running you send a POST request with a JSON body to:
> /plan-generator

Request Format
--------------

Sample JSON request body:

```
{
	"loanAmount": "5000.00",
	"nominalRate": "5.0",
	"duration": 24,
	"startDate": "2018-01-01T00:00:01Z"
}
```

* loanAmount is a required field, should not contain denomination symbols.
* nominalRate is a required field and should not contain a percentage symbol.
* duration is a required field and should not contain a decimal point.
* startDate is a required field and must be in the force yyyy-mm-ddThh:mm:ssZ or yyyy-mm-ddThh-mm:ss+00:00.

Response Format
---------------

Sample JSON response body:

```
{
    "borrowerPayments": [
        {
            "borrowerPaymentAmount": "219.36",
            "date": "2018-01-01T00:00:01Z",
            "initialOutstandingPrincipal": "5000.00",
            "interest": "20.83",
            "principal": "198.53",
            "remainingOutstandingPrincipal": "4801.47"
        },
        {
            "borrowerPaymentAmount": "219.36",
            "date": "2018-02-01T00:00:01Z",
            "initialOutstandingPrincipal": "4801.47",
            "interest": "20.01",
            "principal": "199.35",
            "remainingOutstandingPrincipal": "4602.12"
        },
        ...
        {
            "borrowerPaymentAmount": "219.28",
            "date": "2019-12-01T00:00:01Z",
            "initialOutstandingPrincipal": "218.37",
            "interest": "0.91",
            "principal": "218.37",
            "remainingOutstandingPrincipal": "0.00"
        }
    ]
}
```

The response is an array named borrowerPayments each entry in the array contains the following fields:
* borrowerPaymentAmount: the amount the borrower will pay.
* date: date payment will be made.
* initialOutstandingPrincipal: principal before payment.
* remainingOustandingPrincipal: what the principal will be after payment.
* interest: the portion of the payment that will go to interest.
* principal: the portion of the payment that will be applied to the initialOustandPrincipal.
* remainingOutstandingPrincipal: the principal remaining after the payment has been made.