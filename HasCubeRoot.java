/**
 * Performance (time)
 * real	0m0.337s
 * user	0m0.299s
 * sys	0m0.045s
 */
public class HasCubeRoot {

	public static boolean hasCubeRoot(int x) {
		for (int i = 0, sum = 0; sum <= x; i++, sum = i*i*i) {
			if (sum == x) return true;
		}
		return false;
	}


	public static void main(String... args) {
		for (int i = 0; i < 1000000; i++) {
			if (hasCubeRoot(i)) {
				System.out.println(i + " has a cube root");
			} 
		}	
	}
}