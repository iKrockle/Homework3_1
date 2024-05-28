select s."name" student_name,s.age,fa."name" faculty_name
from faculty fa
left join student s on s.faculty_id=fa.id;

//inner join

select s."name" student_name,s.age,av.file_path
from student s, avatar av
where s.id=av.student_id;