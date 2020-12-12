Loan Payment Plan Generator
===========================
By Robert Nevitt
----------------

The Loan Payment Plan Generator creates a plan based on four inputs: 
* loan amount
* nominal rate (APR only at this point)
* duration in months
* first payment date

To run the application from command line:
> ./mvnw spring-boot:run

To run tests:
> ./mvnw test

Once the app is running you send a POST request with a JSON body to:
> /plan-generator

Sample JSON request body:

```
{
	"loanAmount": "5000.00",
	"nominalRate": "5.0",
	"duration": 24,
	"startDate": "2018-01-01T00:00:01Z"
}
```

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
