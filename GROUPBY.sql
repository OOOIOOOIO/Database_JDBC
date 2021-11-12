--PLAYER / TEAM_ID �� K01�� ����(*) �˻�
SELECT * FROM PLAYER WHERE TEAM_ID='K01'; 
--PLAYER / TEAM_ID�� K07�� �ƴ� ���� �˻�
SELECT * FROM PLAYER WHERE TEAM_ID != 'K07';
--PLAYER / WEIGHT�� 80 ������ ���� �˻�
SELECT * FROM PLAYER WHERE WEIGHT<=80;
--PLAYER / HEIGHT�� 170 �̻��� ���� �˻�
SELECT * FROM PLAYER WHERE HEIGHT >= 170;
--PLAYER / HEIGHT�� 170���� �̸鼭 WEIGHT�� 70�̻��� ���� �˻�
SELECT * FROM PLAYER WHERE HEIGHT <= 170 AND WEIGHT >= 70;
--PLAYER / TEAM_ID�� K03 �̰� JOIN_YYYY �� 2011�� ���� �˻�
SELECT * FROM PLAYER WHERE TEAM_ID = 'K03' AND JOIN_YYYY = '2011';
--PLAYER / TEAM_ID�� K04 �̰� JOIN_YYYY �� 2011~2012�� ���� �˻�
SELECT * FROM PLAYER WHERE TEAM_ID='K04' AND JOIN_YYYY IN ('2011','2012');
--PLAYER / BIRTH_DATE�� 1987�� ������ ���� �˻�
SELECT * FROM PLAYER
WHERE BIRTH_DATE>=TO_DATE('1987/01/01 00:00:00','YYYY/MM/DD HH24:MI:SS');
SELECT * FROM PLAYER WHERE BIRTH_DATE>='1987-01-01';

SELECT * FROM PLAYER WHERE TO_CHAR(BIRTH_DATE,'YYYY')>='1987';

--����ȯ
SELECT TO_NUMBER('12515') FROM DUAL;
SELECT TO_CHAR(125) FROM DUAL;

--SYSDATE : ���� �ð���
SELECT SYSDATE FROM DUAL; 
SELECT TO_CHAR(SYSDATE) FROM DUAL; -- '09-NOV-21'
SELECT TO_CHAR(SYSDATE,'YY/MM/DD') FROM DUAL; -- '21/11/09'
SELECT TO_CHAR(SYSDATE,'YY-MM-DD') FROM DUAL; -- '21-11-09'
SELECT TO_CHAR(SYSDATE,'YYYY') FROM DUAL; -- '2021'
SELECT TO_CHAR(SYSDATE,'HH24:MI:SS') FROM DUAL; -- '19:38:26'

SELECT TO_DATE('2021-12-25 14:00:00','YYYY-MM-DD HH24:MI:SS') FROM DUAL;

--����
SELECT 'FLO' || 'WER' "��" FROM DUAL;
--������ȣ-�����̸�
SELECT PLAYER_ID||'-'||PLAYER_NAME "��������" FROM PLAYER;

--LIKE ���ǽ�
SELECT PLAYER_NAME FROM PLAYER WHERE PLAYER_NAME LIKE('��_%');

--�����̸�, ���� �˻�
SELECT PLAYER_NAME "�����̸�", NVL(NICKNAME,'��������') "����" FROM PLAYER;
SELECT PLAYER_NAME "�����̸�", NVL2(NICKNAME,'��������','��������') "����"
FROM PLAYER;
SELECT PLAYER_NAME "�����̸�", NVL2(NICKNAME,NICKNAME,'��������') "����"
FROM PLAYER;

SELECT * FROM PLAYER;
SELECT SUM(HEIGHT),MAX(HEIGHT),MIN(HEIGHT),AVG(HEIGHT),COUNT(NVL(HEIGHT,0))
FROM PLAYER;

--GROUP BY
SELECT TEAM_ID, MAX(HEIGHT) FROM PLAYER GROUP BY TEAM_ID;

--HAVING
SELECT TEAM_ID, MAX(HEIGHT) FROM PLAYER GROUP BY TEAM_ID
HAVING MAX(HEIGHT)>=185;

--ORDER BY
SELECT TEAM_ID, MAX(HEIGHT) FROM PLAYER GROUP BY TEAM_ID
HAVING MAX(HEIGHT)>=185 ORDER BY TEAM_ID;

SELECT TEAM_ID, MAX(HEIGHT) M FROM PLAYER GROUP BY TEAM_ID
HAVING MAX(HEIGHT)>=185 ORDER BY M DESC;

--EMPLOYEES / JOB_ID ���� ��� SALARY�� 10000�̸��� �׷����
--SALARY �հ�, ���, �ִ밪, �ּҰ�, JOB_ID�� ���� �˻�
SELECT
	JOB_ID ����,
	SUM(SALARY) �հ�,
	AVG(SALARY) ���,
	MAX(SALARY) �ִ밪,
	MIN(SALARY) �ּҰ�,
	COUNT(*) "����"
FROM EMPLOYEES GROUP BY JOB_ID HAVING AVG(SALARY)<10000;
--T_PROFESSOR / �а��� ��� �޿� �˻�, ��� �޿��� 450���� ���� �а��� �˻�
SELECT DEPTNO �а�, AVG(PAY) "��� �޿�"
FROM T_PROFESSOR GROUP BY DEPTNO HAVING AVG(PAY) > 450;
--PLAYER / �����԰� 80 �̻��� �������� ���Ű�� 180�̻��� ������ �˻�
SELECT "POSITION" ������ FROM PLAYER WHERE WEIGHT>=80
GROUP BY "POSITION" HAVING AVG(HEIGHT)>=180;
--T_PROFESSOR / �а���, ���޺��� �������� ��� �޿� �˻�
SELECT
	DEPTNO �а�,
	"POSITION" ����,
	AVG(PAY) "��� �޿�"
FROM T_PROFESSOR GROUP BY DEPTNO,"POSITION";
--T_EMP / �Ŵ���(MGR)���� �����ϴ� ��������
--�Ŵ���, ������, �޿��Ѿ�, �޿����, �����(COMM) ��� ���޾� �˻�
--�� �����(JOB = 'PRESIDENT')�� ����
SELECT * FROM T_EMP;
SELECT
	MGR "�Ŵ��� ��ȣ",
	COUNT(*) "���� ��",
	SUM(SAL) "�޿� �Ѿ�",
	AVG(SAL) "�޿� ���",
	AVG(NVL(COMM,0)) "����� ���"
FROM T_EMP WHERE JOB!='PRESIDENT' GROUP BY MGR;
--NULL ��
--�÷� IS NULL : NULL �̶�� ��
--�÷� IS NOT NULL : NULL�� �ƴ϶�� ��
SELECT
	MGR "�Ŵ��� ��ȣ",
	COUNT(*) "���� ��",
	SUM(SAL) "�޿� �Ѿ�",
	AVG(SAL) "�޿� ���",
	AVG(NVL(COMM,0)) "����� ���"
FROM T_EMP GROUP BY MGR HAVING MGR IS NOT NULL;
--T_PROFESSOR / ������ ������ Ȥ�� �������� ����� �߿� ������
--����ȣ, �Ҽӱ��� �� ��, �ټ��� ���, �޿� ���, ���ʽ� ��� �˻�
--DATEŸ�Գ��� ����ÿ��� �� ���� ���� ����� ����
SELECT SYSDATE - HIREDATE FROM T_PROFESSOR;
--CEIL(����) : �ø��Լ� / FLOOR(����) : �����Լ� / ROUND(����) : �ݿø��Լ�
SELECT
	DEPTNO "�� ��ȣ",
	COUNT(*) "�Ҽӱ��� �Ѽ�",
	CEIL(AVG(SYSDATE-HIREDATE)) "�ټ��� ���",
	AVG(PAY) "�޿� ���",
	AVG(NVL(BONUS,0)) "���ʽ� ���"
FROM T_PROFESSOR WHERE "POSITION" IN ('������','������')
GROUP BY DEPTNO;






