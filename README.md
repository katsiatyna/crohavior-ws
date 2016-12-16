# CROHAVIOR Web Service

## Overview
This is a Web Service for the Crohavior project.

##WS API DEMO

1. GET Heatmaps without api_key:

http://localhost:8080/api/heatmaps/1?interval=5&startTime=1224726940000&endTime=1224726960000

expected response: {"apiResponseMessage":{"message":"Parameter api_key has to be provided","type":"error"}}

2. GET Heatmaps correct:

http://localhost:8080/api/heatmaps/1?interval=5&startTime=1224726800000&endTime=1224726960000&api_key=crohavior

expected response: the first page of data

3. GET Heatmaps page 2:

http://localhost:8080/api/heatmaps/1?startTime=1224726800000&endTime=1224726960000&interval=5&pageNmb=2&api_key=crohavior

expected response: second page with links to next and prev

4. GET trajectories batches

http://localhost:8080/api/trajectories/batches/1?api_key=crohavior

expected response: list of trajectories with links

5. GET trajectories batch by link from 4.

http://localhost:8080/api/trajectories/1?batchId=ss2008-10-23T02%3A56%3A15e2008-10-23T04%3A56%3A15&api_key=crohavior

expected response: valid list of trajectories

6. GET heatmaps by the link from the trajectories

http://localhost:8080/api/heatmaps/1?startTime=1224726975000&endTime=1224734175000&interval=5&api_key=crohavior

expected response: heatmaps page 1 with/without data

7. GET User by username

http://localhost:8080/api/users/CROHAVIOR?api_key=crohavior

expected response: user with links and embedded projects

8. DELETE user by following the link from the _links

DELETE http://localhost:8080/api/users/CROHAVIOR?api_key=crohavior

expected response: User has to be ADMIN

9. DELETE same user with ADMIN api_key

DELETE http://localhost:8080/api/users/CROHAVIOR?api_key=akmahousing

expected: works!

10. GET User to check it's deleted

GET http://localhost:8080/api/users/CROHAVIOR?api_key=akmahousing

11. Create project

POST http://localhost:8080/api/projects?api_key=akmahousing
{
"id":5,
  "projectName": "Demeter",
"minLatitude": 40.333,
"minLongitude": 161.666, 
"maxLatitude": 40.666,
"maxLongitude": 161.999,
"userId":1

}

expected: OK

12. Update the same project: change id

PUT http://localhost:8080/api/projects/2?api_key=akmahousing
{
"id":6,
  "projectName": "Demeter",
"minLatitude": 40.333,
"minLongitude": 161.666, 
"maxLatitude": 40.666,
"maxLongitude": 161.999,
"userId":1

}

expected: cannot change ID

13. Update correct

PUT http://localhost:8080/api/projects/2?api_key=akmahousing

{
"id":5,
  "projectName": "Demeter",
"minLatitude": 40.333,
"minLongitude": 161.666, 
"maxLatitude": 40.666,
"maxLongitude": 161.999,
"userId":1

}

expected: OK

14. GET to check the change

http://localhost:8080/api/projects/2?api_key=akmahousing

expected: Demeter 2.0






