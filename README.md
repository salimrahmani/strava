# strava

# Running challenge 

We are creating a new version of connected devices for runners. Hence we need to develop the API used to track the data of our users.

Your goal is to implement a web server that will expose a two REST API endpoints.

The first one saves a run. It must:
* accept four parameters :
    * the start and end dates
    * the number of ran kilometers
    * the number of burnt calories
* return the created run

The second one returns the users' statistics between two dates. It must :
* accept two dates as parameters
* return a JSON with:
    * the average ran kilometers
    * the average burnt calories

The server needs to be:
* ready for production
* easy to maintain by other developers
