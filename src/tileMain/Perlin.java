package tileMain;

import java.util.Random;

public class Perlin {
	float[][] generateWhiteNoise(int width, int height) {
	    Random random = new Random(0);
	    float[][] noise = new float[width][height];
	 
	    for (int i = 0; i < noise.length; i++) {
	        for (int j = 0; j < noise[i].length; j++){
	            noise[i][j] = (float)random.nextDouble();
	        }
	    }
	 
	    return noise;
	}
	
	float[][] generateSmoothNoise(float[][] baseNoise, int octave){
		int width = baseNoise.length;
		int height = baseNoise[0].length;
	 
		float[][] smoothNoise = baseNoise;
	 
		int samplePeriod = (int) Math.pow(2,octave); // calculates 2 ^ k
		float sampleFrequency = 1.0f / samplePeriod;
	   
		for (int i = 0; i < width; i++) {
	      //calculate the horizontal sampling indices
	      int sample_i0 = (i / samplePeriod) * samplePeriod;
	      int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
	      float horizontal_blend = (i - sample_i0) * sampleFrequency;
	      for (int j = 0; j < height; j++){
	         //calculate the vertical sampling indices
	         int sample_j0 = (j / samplePeriod) * samplePeriod;
	         int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
	         float vertical_blend = (j - sample_j0) * sampleFrequency;
	         //blend the top two corners
	         float top = interpolate(baseNoise[sample_i0][sample_j0],
	            baseNoise[sample_i1][sample_j0], horizontal_blend);
	 
	         //blend the bottom two corners
	         float bottom = interpolate(baseNoise[sample_i0][sample_j1],
	            baseNoise[sample_i1][sample_j1], horizontal_blend);
	 
	         //final blend
	         smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
	      }
	   }
	 
	   return smoothNoise;
	}
	
	float interpolate(float x0, float x1, float alpha){
		return (float) ((float)(x0) * (float)(1 - alpha) + (float)(alpha * x1));
	}
	
	float[][] generatePerlinNoise(float[][] baseNoise, int octaveCount) {
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing
	 
	   float persistance = .5f;
	 
	   //generate smooth noise
	   for (int i = 0; i<octaveCount; i++) {
		   System.out.println("Generating Smooth Noise: " + i);
	       smoothNoise[i] = generateSmoothNoise(baseNoise, i);
	   }
	 
	    float[][] perlinNoise = new float[width][height];
	    float amplitude = 1.0f;
	    float totalAmplitude = 0.0f;
	 
	    //blend noise together
	    for (int octave = octaveCount - 1; octave >= 0; octave--) {
	       amplitude *= persistance;
	       totalAmplitude += amplitude;
	       System.out.println("Adding smooth noise for octave: " + octave + " at amplitude: " + amplitude);
	       for (int i = 0; i < width; i++) {
	          for (int j = 0; j < height; j++) {
	             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
	          }
	       }
	    }
	 
	   //normalization
	   for (int i = 0; i < width; i++) {
	      for (int j = 0; j < height; j++) {
	         perlinNoise[i][j] /= totalAmplitude;
	      }
	   }
	   return perlinNoise;
	}
	
	public float[][] printVals(float[][] baseNoise){
		baseNoise = generatePerlinNoise(generateWhiteNoise(800,800),6);
		for(int i = 0; i<baseNoise.length; i++){
			String row = "";
			for(int j = 0; j<baseNoise[i].length;j++){
				row+= (int)(baseNoise[i][j]*255) + " ";
			}
			//System.out.println(row);
		}
		return baseNoise;
	}
}
