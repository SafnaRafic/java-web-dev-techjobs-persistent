## Part 1: Test it with SQL

## Part 2: Test it with SQL
select name from Employer where location = "Saint Louis"

## Part 3: Test it with SQL
Drop table job;

## Part 4: Test it with SQL
select name,description from skill
where skill.id in (select skills_id from job_skills
where jobs_id in (select id from job));