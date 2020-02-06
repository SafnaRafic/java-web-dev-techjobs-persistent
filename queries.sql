## Part 1: Test it with SQL
CREATE TABLE job(
id int PRIMARY KEY,
employer VARCHAR(255),
name VARCHAR(255),
skills VARCHAR(255));

show column from job;

## Part 2: Test it with SQL
SELECT name FROM Employer WHERE location = "Saint Louis"

## Part 3: Test it with SQL
DROP TABLE job;

## Part 4: Test it with SQL
SELECT name,description FROM skill
WHERE skill.id In (SELECT skills_id FROM job_skills
WHERE jobs_id In (SELECT id FROM job));

select skill.name,skill.description,job_skills.skills_id from skill inner join job_skills
on skill.id=job_skills.skills_id;