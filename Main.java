class Main {
	public static void main(String[] args) {
		Board b1 = new Board();
		System.out.println(b1);
		
		int[] xy = b1.fromCartesian(3,3);
		int[] dxdy = b1.fromCartesian(4,4);
		b1.move(xy[0], xy[1], dxdy[0], dxdy[1]);

		System.out.println(b1);
		
	}
}