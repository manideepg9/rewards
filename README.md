# rewards

The below endpoint calculates and returns the reward points for all the transactions provided in orders.txt. 
If we provide only 3 months of data, API will calculate rewards for only those 3 months. 

API:

GET http://localhost:8080/rewards

Expected Response:

{
    "Customer1": {
        "MARCH-2021": 165,
        "MARCH-2020": 350,
        "MAY-2020": 960,
        "JUNE-2020": 301,
        "Total": 1926,
        "OCTOBER-2020": 0,
        "SEPTEMBER-2020": 150
    },
    "Customer2": {
        "MARCH-2020": 250,
        "APRIL-2020": 465,
        "JUNE-2020": 0,
        "Total": 721,
        "SEPTEMBER-2020": 1,
        "JUNE-2021": 5
    }
}
