SELECT * FROM PLAYER;
SELECT * FROM TEAM;

SELECT 
	p.PLAYER_NAME,
	t.TEAM_NAME 	
FROM PLAYER p, TEAM t;

--�̳� ����
--ANSI
SELECT 
	p.PLAYER_NAME,
	t.TEAM_NAME 
FROM  PLAYER p
	INNER JOIN TEAM t ON p.TEAM_ID = t.TEAM_ID
WHERE t.TEAM_ID = 'K01';

--Oracle
SELECT 
	p.PLAYER_NAME,
	t.TEAM_NAME
FROM PLAYER p, TEAM t 
WHERE p.TEAM_ID = t.TEAM_ID;


--�ܺ� ����
SELECT NAME, PROFNO FROM T_PROFESSOR;
SELECT NAME, PROFNO FROM T_STUDENT;

SELECT 
	ts.NAME �л��̸�,
	NVL(tp.NAME, '��米�� ����') �����̸�
FROM T_STUDENT ts 
	LEFT OUTER JOIN T_PROFESSOR tp ON  ts.PROFNO = tp.PROFNO ;



-- ��������
SELECT 
	PLAYER_ID ,
	TEAM_ID ,
	HEIGHT ,
	(SELECT AVG(HEIGHT) 
	FROM PLAYER)
FROM PLAYER
WHERE HEIGHT > (SELECT AVG(HEIGHT) FROM PLAYER);

SELECT * 
FROM (SELECT PLAYER_ID, PLAYER_NAME, TEAM_ID, HEIGHT FROM PLAYER);

----------------------����-----------------------------
--EMP,DEPT / �� ������� �̸�, �μ���ȣ, �ٹ����� �˻�
SELECT  * FROM EMP;
SELECT * FROM DEPT;

SELECT
	e.ENAME,
	d.DEPTNO,
	d.LOC
FROM EMP e
	JOIN DEPT d ON e.DEPTNO=d.DEPTNO;

--EMPLOYEES / ��ձ޿����� ���� ������� �޿��� 10% �λ��ϱ�(51��)
SELECT * FROM EMPLOYEES e;

UPDATE EMPLOYEES
SET SALARY = 1.1*SALARY
WHERE SALARY < (SELECT AVG(SALARY) FROM EMPLOYEES);

--PLAYER, TEAM / ������ ������ ���� ���� ��ȭ��ȣ �˻�
SELECT * FROM PLAYER;
SELECT * FROM TEAM;

SELECT t.DDD || '-' || t.TEL ��ȭ��ȣ 
FROM PLAYER p
	JOIN TEAM t ON p.TEAM_ID = t.TEAM_ID
WHERE p.PLAYER_NAME = '������';

--EMPLOYEES, JOBS / �� ������� �̸�, �����ڵ�, ������, �̸��� �˻�
SELECT * FROM EMPLOYEES ;
SELECT * FROM JOBS;

SELECT
	e.FIRST_NAME || ' ' || e.LAST_NAME �̸�,
	e.JOB_ID,
	j.JOB_TITLE,
	e.EMAIL
FROM EMPLOYEES e
	JOIN JOBS j ON e.JOB_ID = j.JOB_ID;

--STADIUM, SCHEDULE / ����� �� ��� ������ 20120501 ~ 20120502 ���̿� �ִ� ����� �˻�(IN, BETWEEN���)
SELECT * FROM STADIUM s ;
SELECT * FROM SCHEDULE s ;

SELECT *
FROM STADIUM
WHERE STADIUM_ID IN
	(SELECT STADIUM_ID FROM SCHEDULE
	WHERE SCHE_DATE BETWEEN '20120501' AND '20120502');

--������ ������ �Ҽӵ� ���� ������ �˻�
SELECT * FROM PLAYER ;

SELECT * 
FROM PLAYER 
WHERE TEAM_ID = (SELECT TEAM_ID FROM PLAYER WHERE PLAYER_NAME = '������');

--�౸������ �� ��ü ���Ű���� ���� ������ �˻�(Ű �������� ����)
SELECT * 
FROM PLAYER 
WHERE HEIGHT < (SELECT AVG(HEIGHT) FROM PLAYER)
ORDER BY HEIGHT DESC;

--T_STUDENT, T_DEPARTMENT / �л��̸�, 1���� �а���ȣ, 1���� �а��̸� �˻�
SELECT
	s.NAME,
	s.DEPTNO1,
	d.DNAME
FROM T_STUDENT s
	JOIN T_DEPARTMENT d ON s.DEPTNO1 = d.DEPTNO;

--EMP, DEPT / �̸��� L�� �ִ� ������� �μ���� ���� �˻�
SELECT
	e.ENAME,
	d.DNAME,
	d.LOC
FROM EMP e
	JOIN DEPT d ON e.DEPTNO = d.DEPTNO
WHERE e.ENAME LIKE('%L%');

--JOBS, EMPLOYEES / ������ 'Manager'��� ���ڿ��� ���Ե� �������� ��� ������ �������� �˻�
SELECT
	j.JOB_TITLE,
	AVG(e.SALARY)
FROM JOBS j
	JOIN EMPLOYEES e ON e.JOB_ID = j.JOB_ID
WHERE j.JOB_TITLE LIKE('%Manager%')
GROUP BY j.JOB_TITLE;

--T_STUDENT, T_DEPARTMENT, T_PROFESSOR / �л��̸�, �а��̸�, �������� �̸� �˻�
SELECT
	s.NAME �л���,
	d.DNAME �а���,
	NVL(p.NAME,'��� ����') ��������
FROM T_STUDENT s
	JOIN T_DEPARTMENT d ON s.DEPTNO1 = d.DEPTNO
	LEFT OUTER JOIN T_PROFESSOR p ON s.PROFNO = p.PROFNO;

--�౸������ �߿��� �� ���� Ű�� ���� ū �������� ���ڵ�, �̸�, Ű �˻�(TEAM_ID�� ����)
SELECT TEAM_ID, PLAYER_NAME, HEIGHT 
FROM PLAYER
WHERE (TEAM_ID, HEIGHT) IN -- ���������� �÷��� ������ ������ߵ�
	(SELECT TEAM_ID, MAX(HEIGHT) FROM PLAYER
	GROUP BY TEAM_ID)
ORDER BY TEAM_ID;

--STADIUM, TEAM / ������ڵ�, ������̸�, �¼���, Ȩ���̸� �˻�
SELECT
	s.STADIUM_ID "����� �ڵ�",
	s.STADIUM_NAME "����� �̸�",
	s.SEAT_COUNT "�¼� ��",
	NVL(t.TEAM_NAME,'Ȩ�� ����') "Ȩ�� ��"
FROM STADIUM s
	LEFT OUTER JOIN TEAM t ON s.HOMETEAM_ID = t.TEAM_ID;

--EMPLOYEES / Den�� ��ȭ��ȣ ��3�ڸ��� ���� ������� �����ڵ�, ��ȭ��ȣ, �̸�
SELECT
	JOB_ID "�����ڵ�",
	PHONE_NUMBER "��ȭ��ȣ",
	FIRST_NAME || ' ' || LAST_NAME "�̸�"
FROM EMPLOYEES
WHERE PHONE_NUMBER LIKE(
	(SELECT SUBSTR(PHONE_NUMBER,1,3) FROM EMPLOYEES
	WHERE FIRST_NAME='Den') || '%'
);

--�迵�� ������, �迵�� �������� �Ի����� ������ �޿��� ���� �������� ������ȣ, �̸�, �޿� �˻�
SELECT
	PROFNO,
	NAME,
	PAY
FROM T_PROFESSOR
WHERE HIREDATE >=
	(SELECT HIREDATE FROM T_PROFESSOR WHERE NAME='�迵��')
AND PAY>=(SELECT PAY FROM T_PROFESSOR WHERE NAME='�迵��');

