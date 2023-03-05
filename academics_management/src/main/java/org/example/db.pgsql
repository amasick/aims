CREATE TABLE department(
id VARCHAR(10),
name VARCHAR(50),
PRIMARY KEY (id)
);

INSERT INTO department(id,name) VALUES ('CS','Computer Science');
INSERT INTO department(id,name) VALUES ('EE','Electrical Engineering');
INSERT INTO department(id,name) VALUES ('MNC','Maths and Computing');
INSERT INTO department(id,name) VALUES ('ME','Mechanical Engineering');


CREATE TABLE batch(
id VARCHAR(10),
year VARCHAR(10),
dep_id VARCHAR(10),
PRIMARY KEY(id),
FOREIGN KEY (dep_id) references department (id)
);

CREATE TABLE course(
id VARCHAR(10),
name VARCHAR(100),
dep_id VARCHAR(10),
l integer,
t integer,
p integer,
c integer,
PRIMARY KEY(id),
FOREIGN KEY (dep_id) references department (id)

);

CREATE TABLE ug_curriculum(
course_id VARCHAR(10),
batch_id VARCHAR(10),
course_type VARCHAR(100),
FOREIGN KEY (course_id) references course (id),
PRIMARY KEY (course_id,batch_id)
);

CREATE TABLE course_catalog(
course_id VARCHAR(10),
PRIMARY KEY(course_id),
FOREIGN KEY (course_id) references course (id)
);

CREATE TABLE course_offering(
course_id VARCHAR(10),
cgpa_limit INTEGER,
instructor_id VARCHAR(10),
PRIMARY KEY(course_id),
FOREIGN KEY (course_id) references course_catalog (course_id),
FOREIGN KEY (instructor_id) references instructor (id)

);

CREATE TABLE prereq(
course_id VARCHAR(10),
prereq_id VARCHAR(10),
FOREIGN KEY (course_id) references course (id),
FOREIGN KEY (course_id) references course (id)
);


CREATE TABLE student(
id VARCHAR(10),
name VARCHAR(100),
batch_id VARCHAR(10),
email VARCHAR(100),
password VARCHAR(100),
phone_number VARCHAR(20),
credits INTEGER,
token TEXT,
PRIMARY KEY (id),
FOREIGN KEY (batch_id) references batch (id)
);

CREATE TABLE instructor(
id VARCHAR(10),
name VARCHAR(10),
email VARCHAR(100),
dep_id VARCHAR(10),
password VARCHAR(100),
phone_number VARCHAR(20),
token TEXT,
PRIMARY KEY (id),
FOREIGN KEY (dep_id) references department (id)
);


CREATE TABLE registration_status(
course_id VARCHAR(10),
student_id VARCHAR(10),
instructor_id VARCHAR(10),
status VARCHAR(100),
FOREIGN KEY (course_id) references course_offering (course_id),
FOREIGN KEY (student_id) references student (id),
FOREIGN KEY (instructor_id) references instructor (id)
);

CREATE TABLE GRADES(
student_id VARCHAR(10),
instructor_id VARCHAR(10),
course_id VARCHAR(10),
grade VARCHAR(5),
semester VARCHAR(100),
academic_year VARCHAR(100),
FOREIGN KEY (student_id) references student (id),
FOREIGN KEY (instructor_id) references instructor (id),
FOREIGN KEY (course_id) references course (id)
);

CREATE TABLE semester(
academic_year VARCHAR(10),
semester VARCHAR(10)
);

CREATE TABLE transcript(
student_id VARCHAR(10),
transcript bytea,
FOREIGN KEY (student_id) references student (id)
);



select course_offering.course_id,ug_curriculum.course_type,course_offering.instructor_id
from student,ug_curriculum,course_offering
where student.batch_id=ug_curriculum.batch_id and ug_curriculum.course_id=course_offering.course_id;


select ug_curriculum.course_id
from ug_curriculum
where course_type='core'
except
select grades.course_id
from grades
where grades.grade!='F' and grades.student_id=user_id;


cs456 68
ee345 30
hs104 3
cp302 3
cp303 3