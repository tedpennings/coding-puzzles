/**
 * Performance (time)
 * real	0m0.193s
 * user	0m0.158s
 * sys	0m0.044s
 */
public class IsPowerOfThree {
	
	public static boolean isPowerOfThree(int x) {
		if (x < 0) throw new IllegalArgumentException();
		// 0 and 1 fall through to true
		while (x > 1) {
			if (x % 3 == 0) {
				x = x/3;
			} else {
				return false;
			}
		}
		return true;
	}

	public static void main(String... args) {
		for (int i = 0; i < 1000000; i++) {
			if (isPowerOfThree(i)) {
				System.out.println(i + " is a power of three");
			} 
		}	

	}

}