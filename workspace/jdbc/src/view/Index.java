package view;

import java.util.Scanner;

//ù ����ȭ��
public class Index {
	public static void main(String[] args) {
		System.out.println("UMS DB�� �����ϱ�");
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1. ȸ������\n2. �α���\n3. ������");
			int choice = sc.nextInt();
			
			if(choice == 1) {
				new JoinView();
			} else if(choice == 2) {
				//�α���
				new LoginView();
			} else if(choice == 3) {
				//������
				System.out.println("�ȳ���������~");
				break;
			}
		}
	}
}










