# Modsen-test-api
That is introductory task into Modsen company.
Service allows users to manage meetings, i.e
create new ones, get certain and all meetings
with filtering and sorting, update meetings information and
delete meetings.
##What you need to start up application?
-JRE 17+

-PostgreSQL server

or

-Docker

There is docker-compose file, that simplifies
application startup.

##How to test application?
I strongly recommend use Postman, it's the easiest
way to check restful API. I suggest to use link belows
that check service performance.

[Get meeting by id](http://localhost:8080/meetings/1)

[Get all meetings](http://localhost:8080/meetings)

[Get all with sorting](http://localhost:8080/meetings?sort_param=topic&sort_type=asc&sort_param=startDate&sort_type=asc)

[Get all with filtering](http://localhost:8080/meetings?search_param=topic&search_info=2&search_param=organizer&search_info=2)

[Get all with sorting and filtering](http://localhost:8080/meetings?sort_param=topic&sort_type=desc&search_param=organizer&search_info=3)

[Create meeting](http://localhost:8080/meetings) (Need to use HTTP methos POST with JSON body of creating meeting)

[Update meeting](http://localhost:8080/meetings/1) (Need to use HTTP methos PATCH with JSON body of updating meeting)

[Delete meeting](http://localhost:8080/meetings/1) (Need to use HTTP methos DELETE)

