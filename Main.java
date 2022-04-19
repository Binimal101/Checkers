class Main {
	public static void main(String[] args) {
		Board b1 = new Board();
		System.out.println(b1);
		
		b1.moveUsingCartesian(8, 8, 1, 1);
		b1.moveUsingCartesian(1, 1, 4, 4);

		System.out.println(b1);
		
	}
}