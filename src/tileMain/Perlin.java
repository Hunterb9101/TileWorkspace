package tileMain;

public class Perlin {
	private static int[] hash = {
			7,4,5,2,6,3,0,1
		};
	
	public static float valueNoise1D(double x, double y, float frequency){
		x*=frequency;
		y*=frequency;
		int i = (int) Math.floor(x);
		i %= hash.length - 1;
		return hash[i] /7f;
	}
	
	public static float valueNoise2D (double x, double y, float frequency) {
		x *= frequency;
		y *= frequency;
		int ix = (int) Math.floor(x);
		int iy = (int) Math.floor(y);
		ix %= hash.length - 1;
		iy %= hash.length - 1;
		return hash[(hash[ix] + iy) % (hash.length-1)] * (1/hash.length-1);
	}
	
	public static float perlinNoise1D(double x, double y, float frequency){
		x*=frequency;
		y*=frequency;
		int i = (int) Math.floor(x);
		i %= hash.length - 1;
		return hash[i] /7f;
	}
	
	public static float perlinNoise2D (double x, double y, float frequency) {
		x *= frequency;
		y *= frequency;
		int ix = (int) Math.floor(x);
		int iy = (int) Math.floor(y);
		ix %= hash.length - 1;
		iy %= hash.length - 1;
		return hash[(hash[ix] + iy) % (hash.length-1)] * (1/hash.length-1);
	}
}
